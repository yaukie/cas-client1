package org.yaukie.zipkin;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Frontend {
	
	private static Logger log = Logger.getLogger(Frontend.class);

  @RequestMapping("/index")
  public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "index";
  }
  @RequestMapping("/about")
  public String about(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "about";
  }
  @RequestMapping("/cuisines")
  public String cuisines(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "cuisines";
  }
  @RequestMapping("/gallery")
  public String gallery(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "gallery";
  }
  @RequestMapping("/codes")
  public String codes(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "codes";
  }
  @RequestMapping("/booking")
  public String booking(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "booking";
  }
  
  @RequestMapping("/noAuth")
  public String noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 if(log.isDebugEnabled()){
		 log.debug("service Frontend starts ");
	 }
	 String context = request.getContextPath();
	 request.getSession().setAttribute("casClient", context);
	return "noAuth";
  }
}