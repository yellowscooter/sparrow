package com.sparrow.web.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sparrow.domain.GuardKeyHolder;


/**
 * This filter will act as a guard to the Application. It will ask the 
 * user to enter a key. If the key is valid, it will allow the user to proceed 
 * to the application, otherwise user cannot enter. This filter should be configured 
 * as the first filter in the web.xml file for the web application.
 * This filter will be typically used during development phase/maintenance to 
 * keep users away from the app, and only allow admin/qa/dev to have access. 
 *
 * @author manishk
 * @since 1.0
 */
public class WebApplicationGuardFilter implements Filter {
  private static final transient Log logger = LogFactory.getLog(WebApplicationGuardFilter.class);
  public static final String KEY_HOLDER = "key_holder";

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();
    GuardKeyHolder holder = (GuardKeyHolder)session.getAttribute(KEY_HOLDER);
    //key is null...means we need to ask the user to put in the key
    if (holder == null) {
      logger.debug("Key not present...Forwarding to guard.htm");
      req.getRequestDispatcher("/guard.htm").forward(request, response);
    }
    
    logger.debug("user has provided the key...entering the applicaiton");
    //  call the next filter
    filterChain.doFilter(request, response);
    
    
  }

  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

}
