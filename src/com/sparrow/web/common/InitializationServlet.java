package com.sparrow.web.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sparrow.service.common.WebApplicationContextHolder;




/**
 * <p>Initialization Servlet that can be used to bootstrap various aspects of the application.
 * 
 * 
 * <p>This servlet is configured in <code>web.xml</code> to be loaded
 * on startup.  This servlet is not intended to service any request,
 * it merely uses the web services automatic loading mechanism to
 * get itself launched on startup.</p>
 * 
 * <p>The primary method used for initialization is the 
 * standard servlet method {@link #init}.</p>
 * 
*/
public class InitializationServlet extends HttpServlet {
  private static transient Log logger = LogFactory.getLog(InitializationServlet.class);
  
  /**
   * This service method is not meant to handle requests
   */
  protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
    String msg = "Unauthorized access to GPInitialization Servlet";
    logger.error(msg);
    throw new RuntimeException(msg);
  }

  /**
   * <p>Standard Servlet method called to indicate this servlet is being created
   * and put into service.  This method is used to bootstrap and initialize
   * various aspects of the application.</p>
   * <p>Checks if the following system properties have been set to expected values. If the values 
   * are not set, logs the messages and exits.
   * 
   * <p>Initialization has the following steps:<br/>
   * <ul>
   * Checks if the following system properties have been set to expected values. These 
   * properties should be set as system properties (e.g. by using the -D option)
   * 
   * <li>user.timezone=Asia/Calcutta
   * </ul></p>
   */
  public void init() throws ServletException {
    logger.info("*******************************************");
    logger.info("Initializing the application...");
    logger.info("\n  Runtime Environment" +
                "\n    Java Version " + System.getProperty("java.version") +
                "\n    Working Dir " + System.getProperty("user.dir") +
                "\n    OS           " + System.getProperty("os.name"));
    logger.info(System.getProperties());
    
//Not setting the time zone to Asia/Calcutta
//If we do this, mysql is adding the offset (13.5 hours for PST/IST) to the time it stores. The when you display this 
//time, DataFormat adds the offset again to display the time...this leads to incorrect date being displayed.
//mysql should not add any time to the current timestamp passed to it...not sure if this is a bug or I am missing something
    
    
    //set the default timezone to IST
//    TimeZone timeZoneIST = TimeZone.getTimeZone("Asia/Calcutta");
//    TimeZone.setDefault(timeZoneIST);
//    
//    logger.info( "\n  TimeZone Display Name: " + TimeZone.getDefault().getDisplayName());
//    logger.info( "\n  TimeZone Id: " + TimeZone.getDefault().getID());
//    if (!TimeZone.getDefault().getID().equals(WebConstants.IST_TIME_ZONE)) {
//      logger.fatal("Incorrect timezone set...should be Asia/Calcutta");
//      logger.fatal("Exiting the system");
//      System.exit(1);
//    }
    
    //one time application wide initialization of WebApplicationContextHolder...see this class for details
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    WebApplicationContextHolder.setWebApplicationContext(ctx);
    
    
    logger.info("Finished initializing/validating the Application startup.");
    logger.info("*******************************************"); 
  }
}
