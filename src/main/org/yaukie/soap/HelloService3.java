package org.yaukie.soap;

import org.yaukie.annotation.Soap;
@Soap(serviceType=Soap.Type.WebService)
public interface HelloService3 {

	void sayHello(String name);
	
}
