package com.sparrow.service.common;

import org.springframework.web.context.WebApplicationContext;

import com.sparrow.web.common.InitializationServlet;

/**
 * Placeholder for the spring {@link WebApplicationContext} object so it can be accessed in the service layer.
 * The {@link InitializationServlet} initializes the static variable at the start of the web app
 * @author Manish Kumar
 * @since 1.0
 */
public class WebApplicationContextHolder {
  private static WebApplicationContext webApplicationContext;
  
  public static void setWebApplicationContext(WebApplicationContext context) {
    webApplicationContext = context;
  }
  
  public static WebApplicationContext getWebApplicationContext() {
    return webApplicationContext;
  }

  /**
   * Util method to fetch a Bean from the spring beanFactory. Used when we programatically need the bean instance.
   * @param beanName
   * @return
   * @since 1.0
   */
  public static Object getBean(String beanName) {
    return webApplicationContext.getBean(beanName);
  }
}
