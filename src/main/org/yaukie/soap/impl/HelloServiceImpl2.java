package org.yaukie.soap.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaukie.soap.HelloService2;

public class HelloServiceImpl2 implements HelloService2 {

	private static final Log log = LogFactory.getLog(HelloServiceImpl2.class);
	
 

	@Override
	public String sayHello(String name) {
		return name;
	}


	
}
