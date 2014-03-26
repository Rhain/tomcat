package com.rhain.tomcat.ex02;

import java.io.IOException;

public class StaticResourceProcessor {

	public void process(Request request,Response response){
		try {
			response.sendStaticResoures();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
