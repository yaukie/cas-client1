package org.yaukie.soap;

import org.yaukie.annotation.Soap;
@Soap(serviceType=Soap.Type.WebService)
public interface HelloService4 {

	void sayHello(String name);
	
}
