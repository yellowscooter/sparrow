package com.sparrow.web.deliveryrequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.User;
import com.sparrow.service.deliveryrequest.DeliveryRequestAlreadyExistsException;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.service.util.MailUtil;

/**
 * Controller class to handle new delivery requests.
 * 
 * @author manishk
 * @since 1.0
 */
public class NewDeliveryRequestController extends AbstractController {
  private DeliveryRequestService deliveryRequestService;
  private int recommendedBooksInBookshelf;
  private MailUtil mailUtil;
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public int getRecommendedBooksInBookshelf() {
    return recommendedBooksInBookshelf;
  }

  public void setRecommendedBooksInBookshelf(int recommendedBooksInBookshelf) {
    this.recommendedBooksInBookshelf = recommendedBooksInBookshelf;
  }

  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
                                                throws Exception {
    //  setting response characteristics so its not cached.
    response.setContentType("text/plain;charset=utf-8");  
    
    response.setHeader("Cache-Control", "no-cache");  
    response.setDateHeader("Expires", 0);  
    response.setHeader("Pragma", "no-cache");  
    
    ModelAndView modelAndView = new ModelAndView("/public/common/ajaxResponseBg");
    Map model = new HashMap();
    
    User currentUser = GeneralUtils.getCurrentUserFromTLS();
    DeliveryRequest deliveryRequest;
    
    try {
      deliveryRequest = deliveryRequestService.getInCompleteDeliveryRequestByUser(currentUser);  
    } catch (DeliveryRequestAlreadyExistsException de) {
      //if delivery reqeust already exists...do nothing. Just display message to user
      //that we are working on it.
      model.put("message", "delivery.request.exists");
      modelAndView.addAllObjects(model);
      return modelAndView; 
    }
    //validate if user has sufficient books in bookshelf for new delivery request
    if (currentUser.getProductRequests().size() < recommendedBooksInBookshelf) {
      model.put("message", "delivery.request.incomplete.queue");
      modelAndView.addAllObjects(model);
      return modelAndView; 
    }
    
    
    
    DeliveryRequest newDeliveryRequest = new DeliveryRequest(currentUser, new Date());
    DeliveryRequest deliveryRequest2 = deliveryRequestService.saveDeliveryRequest(newDeliveryRequest);
    
    //  send confirmation email to user and admin
    mailUtil.sendDeliveryRequestConfirmationEmail(GeneralUtils.getCurrentUserFromTLS(), deliveryRequest2);
    
    //validate if user has not used max allowed delivery requests per month under the plan
    //If yes, we need to tell the user that they will have to wait till next plan month for request to be completed
    if (deliveryRequestService.isUserInMonthlyDeliveryRequestLimitForPlan(currentUser)) { //user still has available books
      model.put("message", "delivery.request.sent");
    } else {    //user is maxed out
      model.put("message", "delivery.request.sent.nextmonth");
    }
    
    
    
    modelAndView.addAllObjects(model);
    return modelAndView;
  }

}
