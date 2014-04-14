package com.rhain.tomcat.ex05.core;

import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;

public class SimpleWrapper implements Wrapper ,Pipeline {
	
	private Servlet instance = null;
	private String servletClass;
	private Loader loader;
	private String name;
	private SimplePipeline pipeline = new SimplePipeline(this);
	protected Container parent = null;
	
	public SimpleWrapper(){
		pipeline.setBasic(new SimpleWrapperValve());
	}
	

	@Override
	public void addChild(Container paramContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContainerListener(ContainerListener paramContainerListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(
			PropertyChangeListener paramPropertyChangeListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backgroundProcess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container findChild(String paramString) {
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
		if(loader != null)
			return loader;
		if(parent != null)
			return (parent.getLoader());
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
		return parent;
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
	public void invoke(Request paramRequest, Response paramResponse)
			throws IOException, ServletException {
		pipeline.invoke(paramRequest, paramResponse);
	}

	@Override
	public void removeChild(Container paramContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainerListener(ContainerListener paramContainerListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(
			PropertyChangeListener paramPropertyChangeListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBackgroundProcessorDelay(int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCluster(Cluster paramCluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoader(Loader paramLoader) {
		this.loader = loader;
	}

	@Override
	public void setLogger(Logger paramLogger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setManager(Manager paramManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(Container paramContainer) {
		this.parent = parent;
	}

	@Override
	public void setParentClassLoader(ClassLoader paramClassLoader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRealm(Realm paramRealm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setResources(DirContext paramDirContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Valve getBasic() {
		return pipeline.getBasic();
	}

	@Override
	public void setBasic(Valve valve) {
		this.pipeline.setBasic(valve);
	}

	@Override
	public synchronized void addValve(Valve valve) {
		pipeline.addValve(valve);
		
	}

	@Override
	public Valve[] getValves() {
		return pipeline.getValves();
	}

	@Override
	public void removeValve(Valve valve) {
		pipeline.removeValve(valve);
	}

	@Override
	public void addInitParameter(String paramString1, String paramString2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInstanceListener(InstanceListener paramInstanceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapping(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSecurityReference(String paramString1, String paramString2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Servlet allocate() throws ServletException {
		if(instance == null){
			instance = loadServlet();
		}
		return instance;
	}
	
	private Servlet loadServlet() throws ServletException{
		if(instance != null)
			return instance;
		Servlet servlet = null;
		String actualClass = servletClass;
		if(actualClass == null){
			throw new ServletException("servlet class has not been specified");
		}
		Loader loader = getLoader();
		if(loader == null){
			throw new ServletException("No loader.");
		}
		ClassLoader classLoader = loader.getClassLoader();
		
		Class classClass = null;
		if(classLoader != null){
			try {
				classClass = classLoader.loadClass(actualClass);
			} catch (ClassNotFoundException e) {
				throw new ServletException(" servlet class not found");
			}
		}
		try {
			servlet = (Servlet) classClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("fail to instantiate servlet");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("fail to instantiate servlet");
		}
		servlet.init(null);
		return servlet;
	}

	@Override
	public void deallocate(Servlet paramServlet) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findInitParameter(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findInitParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findMappings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSecurityReference(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findSecurityReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getJspFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoadOnStartup() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRunAs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void incrementErrorCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUnavailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void load() throws ServletException {
		instance = loadServlet();
		
	}

	@Override
	public void removeInitParameter(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeInstanceListener(InstanceListener paramInstanceListener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMapping(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSecurityReference(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAvailable(long paramLong) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setJspFile(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoadOnStartup(int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRunAs(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	@Override
	public void unavailable(UnavailableException paramUnavailableException) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload() throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
