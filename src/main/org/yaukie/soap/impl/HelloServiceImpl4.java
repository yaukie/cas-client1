package org.yaukie.soap.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yaukie.soap.HelloService4;

public class HelloServiceImpl4 implements HelloService4 {

	private static final Log log = LogFactory.getLog(HelloServiceImpl4.class);
	
 

	@Override
	public void sayHello(String name) {
		log.debug("AAAAAAAA=="+name);
	}


	
}
