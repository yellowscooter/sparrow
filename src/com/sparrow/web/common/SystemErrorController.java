package com.sparrow.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.service.util.MailUtil;


/**
 * Controller to handle System Exceptions
 * @author Manish Kumar
 * @since 1.0
 */
public class SystemErrorController extends AbstractController {
  private static transient Log logger = LogFactory.getLog(SystemErrorController.class);
  private MailUtil mailUtil;
  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    logger.error("***********An unhandled Exception has occured*********");
    //send an email to admin
    mailUtil.sendMailToAdmin("A System Exception has occurred", "Check the log file for details....look for this message ***********An unhandled Exception has occured*********");
    
    return new ModelAndView("forward:/systemError.jsp");  
    
  }

}
