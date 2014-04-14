package com.rhain.tomcat.ex03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpConnector implements Runnable{

	boolean stopped;
	
	private String scheme = "http";
	
	public String getScheme(){
		return scheme;
	}
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 9090;
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		while(!stopped){
			Socket socket= null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				continue;
			}
			HttpProcessor processor = new HttpProcessor(this);
			processor.parse(socket);
		}
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.start();
	}

}
