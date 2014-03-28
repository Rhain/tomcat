package com.rhain.tomcat.ex02;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer2 {

	private static final String SHUTDOWM_COMMAND = "/shutdown";
	
	private boolean shutdown = false;
	
	public static void main(String[] args) {
		HttpServer2 server = new HttpServer2();
		server.await();
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		int port = 9090;
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!shutdown){
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				
				Request request = new Request(input);
				request.parse();
				
				Response response = new Response(output);
				response.setRequest(request);
				if(request.getUri() != null){
					if(request.getUri().startsWith("/servlet/")){
						ServletProcessor2 p = new ServletProcessor2();
						p.process(request, response);
					}else{
						StaticResourceProcessor processor = new StaticResourceProcessor();
						processor.process(request, response);
					}
				}
				
				
				socket.close();
				
				shutdown = SHUTDOWM_COMMAND.equals(request.getUri());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
