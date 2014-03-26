package com.rhain.tomcat.ex02;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1 {

	public void process(Request request,Response response){
		String uri = request.getUri();
		String servletName = uri.substring(uri.lastIndexOf("/")+1);
		URLClassLoader loader = null;
		
		try{
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(Constants.WEBROOT);
			String repository = (new URL("file",null,classPath.getCanonicalPath()+File.separator)).toString();
			urls[0] = new URL(null,repository,streamHandler);
			loader = new URLClassLoader(urls);
		}catch(Exception e){
			System.out.println(e.toString());
		}
		Class myclass = null;
		try {
			myclass = loader.loadClass(servletName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) myclass.newInstance();
			servlet.service((ServletRequest)request, (ServletResponse)response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
