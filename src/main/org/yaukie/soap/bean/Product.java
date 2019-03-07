package org.yaukie.soap.bean;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@XmlRootElement(name="Product")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain=true)
@ToString
public class Product {

	private String proName;
	
	private String type;
	
	private int price ;
	
	private long  expire;
	
	
	
}
