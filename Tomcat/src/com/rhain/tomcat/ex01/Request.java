package com.rhain.tomcat.ex01;

import java.io.IOException;
import java.io.InputStream;

public class Request {

	private InputStream input;
	private String uri;
	
	public Request(InputStream input){
		this.input = input;
	}
	
	public void parse(){
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] bytes = new byte[2048];
		try {
			i = input.read(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			i = -1;
		}
		for(int j=0;j<i;j++){
			request.append((char)bytes[j]);
		}
		System.out.println(request.toString());
		uri = parseUri(request.toString());
	}
	
	public String parseUri(String request){
		int index1 ,index2;
		index1 = request.indexOf(' ');
		if(index1 != -1){
			index2 = request.indexOf(' ', index1 + 1);
			if(index2 > index1){
				uri = request.substring(index1+1, index2);
				return uri;
			}
		}
		return null;
	}
	
	public String getUri(){
		return uri;
	}
}
