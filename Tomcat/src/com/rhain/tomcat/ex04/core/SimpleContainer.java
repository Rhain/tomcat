package com.rhain.tomcat.ex04.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;

public class SimpleContainer implements Container {
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator  + "webroot";

	@Override
	public void addChild(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContainerListener(ContainerListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backgroundProcess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container findChild(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container[] findChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBackgroundProcessorDelay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loader getLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getMappingObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getParentClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pipeline getPipeline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirContext getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {
		String servletName = ( (HttpServletRequest) request).getRequestURI();
	    servletName = servletName.substring(servletName.lastIndexOf("/") + 1);
	    URLClassLoader loader = null;
	    try {
	      URL[] urls = new URL[1];
	      URLStreamHandler streamHandler = null;
	      File classPath = new File(WEB_ROOT);
	      String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
	      urls[0] = new URL(null, repository, streamHandler);
	      loader = new URLClassLoader(urls);
	    }
	    catch (IOException e) {
	      System.out.println(e.toString() );
	    }
	    Class myClass = null;
	    try {
	      myClass = loader.loadClass(servletName);
	    }
	    catch (ClassNotFoundException e) {
	      System.out.println(e.toString());
	    }

	    Servlet servlet = null;

	    try {
	      servlet = (Servlet) myClass.newInstance();
	      servlet.service((HttpServletRequest) request, (HttpServletResponse) response);
	    }
	    catch (Exception e) {
	      System.out.println(e.toString());
	    }
	    catch (Throwable e) {
	      System.out.println(e.toString());
	    }
		
	}

	@Override
	public void removeChild(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainerListener(ContainerListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackgroundProcessorDelay(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCluster(Cluster arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoader(Loader arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogger(Logger arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setManager(Manager arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(Container arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParentClassLoader(ClassLoader arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRealm(Realm arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResources(DirContext arg0) {
		// TODO Auto-generated method stub
		
	}

}
