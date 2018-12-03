package org;

import org.yaukie.helper.PublishWebServiceHelper;
import org.yaukie.soap.HelloService2;
public class Test {
	public static void main(String[] args) {
		HelloService2 service = (HelloService2) PublishWebServiceHelper.createWebClient("http://localhost:8088/casClient1/soap/HelloService2?wsdl", HelloService2.class);
		service.sayHello("xx");
	}
}
