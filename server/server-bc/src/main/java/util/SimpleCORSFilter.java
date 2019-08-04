package util;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class SimpleCORSFilter implements Filter {
	private String[] domain={
			"http://localhost:4865"};
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	  HttpServletRequest httpRequest = (HttpServletRequest) req; 
	  HttpServletResponse response = (HttpServletResponse) res;
    String curOrigin = httpRequest.getHeader("Origin");  
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:4865");
    if(curOrigin != null) {  
        for (int i = 0; i < domain.length; i++) {  
            if(curOrigin.equals(domain[i])) {  
            	response.setHeader("Access-Control-Allow-Origin", curOrigin);
            	break;
            } 
        }  
    } else { // 对于无来源的请求(比如在浏览器地址栏直接输入请求的)，那就只允许我们自己的机器可以吧  
    	response.setHeader("Access-Control-Allow-Origin", "*");  
    } 
    
   // response.setHeader("Access-Control-Allow-Origin", "http://api.7bcapital.com");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Content-Type","application/json");
    chain.doFilter(req, res);
  }
  public void init(FilterConfig filterConfig) {}
  public void destroy() {}
}
