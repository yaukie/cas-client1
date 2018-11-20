package org.yaukie.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class LoginAuthFilterBySession implements Filter {
	private static final Log log = LogFactory.getLog(LoginAuthFilterBySession.class);
	private static final String V6_LOGIN_URL = "http://10.10.250.248/v6/jsp/login/login.jsp";
	private static final String V6_USER_URL= "http://10.10.250.248/v6/restful/GetUserInfo/getUserInfoJson";
	private static HttpServletRequest httpRequest =null ;
	private static HttpServletResponse httpResponse =null;
	private static HttpSession httpSession=null;
	
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {

		filterConfig = fConfig;
			
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
	     httpRequest = (HttpServletRequest) request;
		 httpResponse = (HttpServletResponse) response;
		 httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
		 httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		 httpResponse.setHeader("Access-Control-Max-Age", "0");
		 httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token,Access-Control-Allow-Headers");
		 httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		 httpResponse.setHeader("XDomainRequestAllowed","1");
	 
        filterChain.doFilter(httpRequest, httpResponse);
		
	}

	@Override
	public void destroy() {
		
	} 
	
	
}