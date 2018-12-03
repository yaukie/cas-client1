package org.yaukie.soap.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaukie.soap.HelloService3;

public class HelloServiceImpl3 implements HelloService3 {

	private static final Log log = LogFactory.getLog(HelloServiceImpl3.class);

	@Override
	public void sayHello(String name) {
		log.debug(name);
		
	}
	


	
}
