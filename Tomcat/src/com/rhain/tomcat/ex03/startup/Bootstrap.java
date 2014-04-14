package com.rhain.tomcat.ex03.startup;

import com.rhain.tomcat.ex03.connector.http.HttpConnector;

public class Bootstrap {
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
