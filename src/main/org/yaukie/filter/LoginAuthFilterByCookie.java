package org.yaukie.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

 /**
  * 基于共享cookie的单点认证过滤器
  * @author yaukie
  *
  */
public class LoginAuthFilterByCookie implements Filter {

	private static final String V6_LOGIN_URL = "http://10.10.250.248/v6/jsp/login/login.jsp";
	private static final String V6_USER_URL= "http://10.10.250.248/v6/restful/GetUserInfo/getUserInfoJson";
	private static final String V6_KEEP_SESSION="http://10.10.250.248/v6/jsp/bsp/keepSession.jsp";
	private static final String V6_HASPERMISSION_URL="http://10.10.250.248/dsp/restful/HasPermission/ifHasPermission";
	
	private static final Log log = LogFactory.getLog(LoginAuthFilterByCookie.class);
 
	private static HttpServletRequest httpRequest =null ;
	private static HttpServletResponse httpResponse =null;
	private static HttpSession httpSession=null;
	private FilterConfig filterConfig;
	
	
	
    /**
     * Default constructor. 
     */
    public LoginAuthFilterByCookie() {
        // TODO Auto-generated constructor stub
    }
    
    
    
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		filterConfig = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		  httpRequest = (HttpServletRequest) request;
		  httpResponse = (HttpServletResponse) response;
		// 1,从客户端cookie里面查找ticket数据
		Cookie[] cookies = httpRequest.getCookies();
		String ssoTicket = "";
		if (cookies != null && cookies.length > 0)
		{
			for (Cookie ck : cookies)
			{
				if (ck.getName().equals("sso_token"))
				{
					ssoTicket = ck.getValue();
					if(ssoTicket.indexOf(".") !=-1){
						ssoTicket=ssoTicket.substring(0,ssoTicket.lastIndexOf("."));
					}
					
					break;
				}
			}
		}
		// 2,ssoTicket数据不存在,重定向到v6系统登录
		if (StringUtils.isEmpty(ssoTicket))
		{
			httpRequest.getSession().removeAttribute("userInfo");
			httpResponse.sendRedirect(V6_LOGIN_URL);
			return;
		} else {
			/*
			 *****session是否已经存在当前用户的登录信息*****
			 *在过滤器中,这个地方貌似永远不成立,,先暂时加上
			 */
			String tmpSsoToken =	httpRequest.getSession().getAttribute("sso_token")==null?"":httpRequest.getSession().getAttribute("sso_token").toString();
			if(ssoTicket.equals(tmpSsoToken)){
				Map info = checkSessionUserInfo(httpRequest.getSession());
				if(info!=null){
					//直接请求第三方应用
					chain.doFilter(httpRequest, httpResponse);
				}
			}

	        try {
 
	        	JSONObject json  =getJson(httpRequest,ssoTicket);
	            /*判断该用户信息是否正常(一般为过期,获取退出)*/
	            if(json.containsKey("exception")){
	            	httpRequest.getSession().removeAttribute("userInfo");
	    			httpResponse.sendRedirect(V6_LOGIN_URL);
	    			return ;
	            }
	  
	    		if (log.isDebugEnabled()) {
		    			log.debug("[json信息]{json=" + json);
		    	}
	             String user_id = json.get("user_id").toString();
	            if(user_id != null && !user_id.isEmpty()){
	            	httpRequest.getSession().setAttribute("sso_token", ssoTicket);
	            	httpRequest.getSession().setAttribute("userInfo", json);
	            	httpRequest.getSession().setAttribute("user_name", json.get("user_name"));
	            	//增加会话保持
	            	keepSession(ssoTicket);
	            	//判断该用户请求的地址是否具有访问权限,
	            	boolean flag = this.ifHasPermission(ssoTicket, user_id);
	            	if(flag){
	            		chain.doFilter(request, response);
	            	}else
	            	{
	            		return ;
	            	}
	            }
			} catch (Exception exp) {
				if(log.isErrorEnabled()){
					log.error("解析cookie错误，错误详情：", exp);
				}
				httpResponse.sendRedirect(V6_LOGIN_URL);
				return ;
			}
			
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		filterConfig = fConfig;
	}
	
	private Map checkSessionUserInfo(HttpSession session){
		Object uid = session.getAttribute("uid");
		Map userInfo = (Map)session.getAttribute("userInfo");
		if(uid!=null&&userInfo!=null&&userInfo.containsKey("uid")&&userInfo.containsKey("nick_name")&&userInfo.containsKey("user_type")){
			return userInfo;
		}
		return null;
	}
	
	/**
	 * 请求调用过程
	 * @param httpRequest
	 * @param userCookieString
	 * @return
	 */
	private JSONObject getJson (HttpServletRequest httpRequest ,String userCookieString){
		if (log.isDebugEnabled()) {
			log.debug("开始验证用户信息restfulUrl]{url=" + V6_USER_URL);
		}
		 JSONObject json=null;
		 String response ="" ;
		 RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		 CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		  HttpPost httpPost = new HttpPost(V6_USER_URL);
		  httpPost.setHeader("Cookie", "JSESSIONID=" + userCookieString + ";Path=/v6;Domain="+httpRequest.getRemoteAddr());
          HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost, HttpClientContext.create());
		    response = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
		    response=response.replaceAll("\\\\\"", "'");
		    response=response.substring(1, response.replaceAll("\\\\\"", "'").length() - 1);
	        json = JSONObject.parseObject(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      
           return json;
	}
	
	
	/**
	 * 会话保持远程操作
	 * @param userCookieString
	 */
	private void keepSession(String userCookieString){
		String response = 0+"";
		try {
			 RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
			 CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			  HttpPost httpPost = new HttpPost(V6_KEEP_SESSION);
			  httpPost.setHeader("Cookie", "JSESSIONID=" + userCookieString + ";Path=/v6;Domain="+httpRequest.getRemoteAddr());
		      HttpResponse httpResponse = httpClient.execute(httpPost, HttpClientContext.create());
			if (httpResponse.getStatusLine().getStatusCode()==200) {
				String callBackString = httpResponse.getEntity().toString();
				log.debug("请求返回结果为:" + callBackString);
 
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断用户请求的某个url是否有权限
	 * @param userCookieString
	 * @param userId
	 * @return
	 */
	private boolean ifHasPermission(String userCookieString,String userId){
		boolean flag = false;
		JSONObject json=null;
		String requestUrl = httpRequest.getRequestURI();
		if(log.isDebugEnabled()){
			log.debug("框架拦截到的用户{"+userId+"}试图请求的URL为:[ "+requestUrl+" ]");
		}
		try {
			RestfulHttpClient.HttpResponse response = RestfulHttpClient.getClient(V6_HASPERMISSION_URL)
					.addHeader("Cookie", "JSESSIONID=" + userCookieString + ";Path=/v6;Domain="+httpRequest.getRemoteAddr())
					.addQueryParam("userId", userId)
					.addQueryParam("url", requestUrl)
					.request();
		     if (response.getCode() == 200) {
		            //请求成功
		    	 	if(log.isDebugEnabled()){
		    	 		log.debug("权限判断调用结果为:"+response.getContent()+",最终发起的请求地址为:"+response.getRequestUrl());
		    	 	}
		    	  	String responseStr = response.getContent();
		    	  	responseStr=responseStr.replaceAll("\\\\\"", "'");
		    	  	responseStr=responseStr.substring(1, responseStr.replaceAll("\\\\\"", "'").length() - 1);
					 json = JSONObject.parseObject(responseStr);
					if(null !=json && json.containsKey("isHasPermission")){
						String flagStr = json.get("isHasPermission").toString();
						flag = Boolean.parseBoolean(flagStr);
					}
		        }
		
		} catch (IOException e) {
			if(log.isErrorEnabled()){
				log.error("权限判断请求失败,原因为:"+e);
			}
		}
		return flag;
	}

}
