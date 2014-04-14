package com.rhain.tomcat.ex03.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.StringManager;

import com.rhain.tomcat.ex03.ServletProcessor;
import com.rhain.tomcat.ex03.StaticResourceProcessor;

public class HttpProcessor {

	private HttpConnector connector = null;
	private HttpRequest request;
	private HttpRequestLine requestLine = new HttpRequestLine();
	private HttpResponse response;
	
	protected String method = null;
	protected String queryString = null;
	
	public HttpProcessor(HttpConnector connnector){
		this.connector = connnector;
	}
	
	protected StringManager sm = StringManager.getManager("com.rhain.tomcat.ex03.connector.http");
	
	public void parse(Socket socket) {
		SocketInputStream input = null;
		OutputStream output = null;
		try {
			input = new SocketInputStream(socket.getInputStream(), 2048);
			output = socket.getOutputStream();
			
			//创建HttpResquest对象并且解析
			request = new HttpRequest(input);
			
			//创建HttpResponse对象
			response = new HttpResponse(output);
			response.setRequest(request);
			
			response.setHeader("Server", "Servlet Container");
			
			parseRequest(input,output);
			parseHeaders(input);
			
			//如果是servlet则调用ServletProcessor处理不然调用StaticResourceProcessor处理
			if(request.getRequestURI().startsWith("/servlet/")){
				ServletProcessor processor = new ServletProcessor();
				processor.process(request, response);
			}else{
				StaticResourceProcessor processor = new StaticResourceProcessor();
				processor.process(request, response);
			}
			//关闭链接
			socket.close();
			//不用关闭这个应用
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws ServletException 
	 * @throws IOException 
	 * 
	 * @Method: parseHeaders 
	 * @Description: org.apache.catalina.connector.http.HttpProcessor.parseHeaders 的简化版本 只处理了一些简单的http headers像 cookie,content-length,content-type,并且忽略了其他的headers
	 * @param @param input
	 * @return void
	 * @throws
	 */
	private void parseHeaders(SocketInputStream input) throws IOException, ServletException{
		while(true){
			HttpHeader header = new HttpHeader();
			
			//读取下一个header
			input.readHeader(header);
			if(header.nameEnd == 0){
				if(header.valueEnd == 0){
					return ;
				}else{
					throw new ServletException(sm.getString("httpProcessor.parseHeaders.colon"));
				}
			}
			
			String name = new String(header.name,0,header.nameEnd);
			String value = new String(header.value,0,header.valueEnd);
			request.addHeader(name, value);
			
			if(name.equals("cookie")){
				Cookie cookies[] = RequestUtil.parseCookieHeader(value);
				for(int i=0;i<cookies.length;i++){
					if(cookies[i].getName().equals("jsessionid")){
						if(!request.isRequestedSessionIdFromCookie()){
							request.setRequestedSessionId(cookies[i].getValue());
							request.setRequestedSessionCookie(true);
							request.setRequestedSessionURL(false);
							
						}
					}
					request.addCookie(cookies[i]);
				}
			}else if("content-length".equals(name)){
				int n = -1;
				try{
					n = Integer.parseInt(value);
				}catch(Exception e){
					throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
				}
				request.setContentLength(n);
			}else if("content-type".equals(name)){
				request.setContentType(value);
			}
			
		}//end while
	}
	
	private void parseRequest(SocketInputStream input,OutputStream output) throws IOException, ServletException{
		//解析 request line  第一行
		input.readRequestLine(requestLine);
		String method = new String(requestLine.method,0,requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol,0,requestLine.protocolEnd);
		
		//鉴别是不是第一行
		if(method.length() < 1){
			throw new ServletException("Missing HTTP request method");
		}else if(requestLine.uriEnd < 1){
			throw new ServletException("Missing HTTP request URI");
		}
		
		//转换查询参数
		int question  = requestLine.indexOf("?");
		if(question >= 0){
			request.setQueryString(new String(requestLine.uri,question + 1,requestLine.uriEnd - question -1));
			uri = new String(requestLine.uri,0,question);
		}else{
			request.setQueryString(null);
			uri = new String(requestLine.uri,0,requestLine.uriEnd);
		}
		
		//检查绝对路径(http 协议)
		if(!uri.startsWith("/")){
			int pos = uri.indexOf("://");
			//解析出协议和host
			if(pos != -1){
				pos = uri.indexOf("/",pos + 3);
				if(pos == -1){
					uri = "";
				}else{
					uri = uri.substring(pos);
				}
			}
		}
		
		//解析请求的session ID
		String match = ";jsessionid=";
		int semicolon = uri.indexOf(match);
		if(semicolon >= 0){
			String rest = uri.substring(semicolon + match.length());
			int semicolon2 = rest.indexOf(';');
			if(semicolon2 >= 0){
				request.setRequestedSessionId(rest.substring(0,semicolon2));
				rest = rest.substring(semicolon2);
			}else{
				request.setRequestedSessionId(rest);
				rest = "";
			}
			request.setRequestedSessionURL(true);
			uri = uri.substring(0,semicolon) + rest;
		}else{
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}
		
		//格式化urI
		String normalizedUri = normalize(uri);
		
		((HttpRequest)request).setMethod(method);
		request.setProtocol(protocol);
		if(normalizedUri != null){
			((HttpRequest)request).setRequestURI(normalizedUri);
		}else{
			((HttpRequest)request).setRequestURI(uri);
		}
		
		if(normalizedUri == null){
			throw new ServletException("Invalid URI:"+ uri +"'");
		}
			
		
	}
	
	
	protected String normalize(String path){
		if(path == null)
			return null;
		
		String normalized = path;
		
		//格式化"/%7E" 和 "/%7e" 为 "/~"
		if(normalized.startsWith("/%7E") || normalized.startsWith("/%7e")){
			normalized = "/~" + normalized.substring(4);
		}
		//阻止编码保存的字符 '%','/','\'
		if ((normalized.indexOf("%25") >= 0)
			      || (normalized.indexOf("%2F") >= 0)
			      || (normalized.indexOf("%2E") >= 0)
			      || (normalized.indexOf("%5C") >= 0)
			      || (normalized.indexOf("%2f") >= 0)
			      || (normalized.indexOf("%2e") >= 0)
			      || (normalized.indexOf("%5c") >= 0)) {
			      return null;
			    }
		if(normalized.equals("/.")){
			return "/";
		}
		
		//格式化斜杠，如果path不是以/开头，添加/
		if(normalized.indexOf('\\') >= 0){
			normalized = normalized.replace('\\', '/');
		}
		if(!normalized.startsWith("/"))
			normalized = "/" + normalized;
		
		//把 path中的'//' 变成'/'
		while(true){
			int index  = normalized.indexOf("//");
			if(index < 0 )
				break;
			normalized = normalized.substring(0,index) + normalized.substring(index + 1);
		}
		
		//把path中的'/./' 变成'/'
		while(true){
			int index = normalized.indexOf("/./");
			if(index < 0)
				break;
			normalized = normalized.substring(0,index) + normalized.substring(index + 2);
		} 
		
		//把path中的'/../' 变成'/'
		while(true){
			int index  = normalized.indexOf("/../");
			if(index < 0){
				break;
			}
			if(index == 0)
				return null;
			int index2 = normalized.lastIndexOf('/',index -1);
			normalized = normalized.substring(0,index2) + normalized.substring(index + 3);
		}
		
		// '/...'含有这样的path 是不合法的
		if(normalized.indexOf("/...") >=0)
			return null;
		
		return normalized;
	}
	
	
}
