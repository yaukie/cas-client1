package org.yaukie.soap;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.yaukie.annotation.Soap;

@Soap(serverPath="product",serviceType=Soap.Type.Restful)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {

    @GET
    @Path("/getProductList")
    public List getProductList() {
    	List list = new ArrayList();
    	list.add("a");
    	list.add("bb");
    	list.add(22);
        return list ;
    }
 
}
