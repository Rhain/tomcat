package com.rhain.tomcat.ex01;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpServer {

	public static final  String WEBROOT = System.getProperty("user.dir")+File.separator+"webroot";
	
	private static final String SHUTDOWN_COMMAND = "/shutdown";
	
	private boolean shutdown = false;
	
	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.await();
	}
	
	public void await(){
		ServerSocket serverSocket = null;
		int port = 9090;
		
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("localhost"));
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
			OutputStream output= null;
			
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				Request request = new Request(input);
				request.parse();
				
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResoures();
				
				socket.close();
				
				/*String uri = request.getUri();
				if(uri != null){
					shutdown = uri.equals(SHUTDOWN_COMMAND);
				}*/
				shutdown = SHUTDOWN_COMMAND.equals(request.getUri());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
