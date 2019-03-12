package org.yaukie.soap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.yaukie.annotation.Soap;
import org.yaukie.soap.bean.Product;

@Soap(serverPath="product",serviceType=Soap.Type.Restful)
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {
	
    @GET
    @Path("/getProductXML")
    @Produces(MediaType.APPLICATION_ATOM_XML)
    public List<Product> getProductXML() {
    	  List list = new ArrayList();
    	Product pro = new Product();
    	pro.setProName("苹果");
    	pro.setPrice(43);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro1 = new Product();
    	pro.setProName("苹果1");
    	pro.setPrice(3);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro2 = new Product();
    	pro.setProName("苹果2");
    	pro.setPrice(23);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro3 = new Product();
    	pro.setProName("苹果3");
    	pro.setPrice(52);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	list.add(pro);
    	list.add(pro1);
    	list.add(pro2);
    	list.add(pro3);
    	
        return list ;
    }
 
    @GET
    @Path("/getProductJSON")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductJSON() {
    	  List list = new ArrayList();
    	Product pro = new Product();
    	pro.setProName("苹果");
    	pro.setPrice(43);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro1 = new Product();
    	pro.setProName("苹果1");
    	pro.setPrice(44);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro2 = new Product();
    	pro.setProName("苹果2");
    	pro.setPrice(55);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	Product pro3 = new Product();
    	pro.setProName("苹果3");
    	pro.setPrice(66);
    	pro.setType("fruit");
    	pro.setExpire(System.currentTimeMillis()+4000);
    	list.add(pro);
    	list.add(pro1);
    	list.add(pro2);
    	list.add(pro3);
    	
        return list ;
    }
}
