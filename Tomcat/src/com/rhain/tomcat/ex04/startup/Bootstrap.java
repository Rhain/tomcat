package com.rhain.tomcat.ex04.startup;

import java.io.IOException;

import org.apache.catalina.LifecycleException;

import com.rhain.tomcat.ex04.core.HttpConnector;
import com.rhain.tomcat.ex04.core.SimpleContainer;

public class Bootstrap {

	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		SimpleContainer container = new SimpleContainer();
		connector.setContainer(container);
		try {
			connector.initialize();
			connector.start();
			
			System.in.read();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
