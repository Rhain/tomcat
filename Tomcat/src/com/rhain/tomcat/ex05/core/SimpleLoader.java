package com.rhain.tomcat.ex05.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

public class SimpleLoader implements Loader {
	
	public static final String WEB_ROOT = System.getProperty("user.dir")+File.separator + "webroot";
	
	ClassLoader classLoader = null;
	Container container = null;
	
	public SimpleLoader(){
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
			urls[0] = new URL(null,repository,streamHandler);
			classLoader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addPropertyChangeListener(
			PropertyChangeListener paramPropertyChangeListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRepository(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] findRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public DefaultContext getDefaultContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDelegate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getReloadable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(
			PropertyChangeListener paramPropertyChangeListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContainer(Container paramContainer) {
		this.container = paramContainer;
	}

	@Override
	public void setDefaultContext(DefaultContext paramDefaultContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDelegate(boolean paramBoolean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReloadable(boolean paramBoolean) {
		// TODO Auto-generated method stub
		
	}

}
