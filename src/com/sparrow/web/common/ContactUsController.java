package com.sparrow.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.EmailMessage;
import com.sparrow.service.util.MailUtil;

public class ContactUsController extends SimpleFormController {
  private MailUtil mailUtil;
  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

//  @Override
//  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
//      throws Exception {
//    
//    //set the page title
//    Map model = new HashMap();
//    model.put("pageTitle", "pageTitle.contactUs");
//    model.put("metaDescription", "metaDescription.contactUs");
//    return new ModelAndView("/public/common/contactUs", model);
//  }


  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    //  set the page title
    Map model = new HashMap();
    model.put("pageTitle", "pageTitle.contactUs");
    model.put("metaDescription", "metaDescription.contactUs");
    String mailSuccess = ServletRequestUtils.getStringParameter(request, "mailSuccess", null);
    if (mailSuccess != null) {
      model.put("message", "contact.us.message.success");
    }
    
    
    return model;
  }

  @Override
  protected ModelAndView onSubmit(Object command) throws Exception {
    Map model = new HashMap();
    
    EmailMessage emailMessage = (EmailMessage) command;
    //send email to customer service
    mailUtil.sendEmailToCustomerService(emailMessage);
    
    return new ModelAndView(getSuccessView());
  }
  
  

}
