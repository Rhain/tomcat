package com.rhain.tomcat.ex03.connector;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

import org.apache.catalina.util.StringManager;

import com.rhain.tomcat.ex03.connector.http.Constants;
import com.rhain.tomcat.ex03.connector.http.HttpRequest;

public class RequestStream extends ServletInputStream {
	
	//------------------------------------------------实例变量
	
	/**
	 * 改输入流是否被关闭了
	 */
	protected boolean closed = false;
	
	/**
	 * 已经被返回的字符数
	 */
	protected int count = 0;
	
	
	/**
	 * 传递过来的 content length，默认是-1
	 */
	protected int length = -1;
	
	/**
	 * 本地化字符管理
	 */
	protected static StringManager sm = StringManager.getManager(Constants.Package);
	
	/**
	 * 用户读取数据的底层输入流
	 */
	protected InputStream stream = null;

	
	//--------------------------------------------------------- 构造方法
	public RequestStream(HttpRequest request){
		super();
		closed = false;
		count = 0;
		length = request.getContentLength();
		stream = request.getStream();
	}
	
	//--------------------------------------------------------- 公共方法
	
	public void close() throws IOException{
		if(closed)
			throw new IOException(sm.getString("requestStream.close.closed"));
		if(length > 0 ){
			while(count < length){
				int b = read();
				if(b < 0)
					break;
			}
		}
		closed = true;
	}
	
	/**
	 * 读取单个byte
	 */
	@Override
	public int read() throws IOException {
		if(closed)
			throw new IOException(sm.getString("requestSteam.read.closed"));
		if((length > 0) && (count >= length))
			return -1;
		
		int b = stream.read();
		if(b >= 0){
			count++;
		}
		
		return b;
	}
	
	public int read(byte[] b) throws IOException{
		return read(b,0,b.length);
	}
	
	public int read(byte b[],int off,int len) throws IOException{
		int toRead = len;
		if(length > 0){
			if(count >= length)
				return -1;
			if((count + len) > length)
				toRead = length -count;
		}
		int actuallyRead = super.read(b,off,toRead);
		return actuallyRead;
	}

}
