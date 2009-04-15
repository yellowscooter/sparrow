package com.sparrow.web.deliveryrequest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.service.deliveryrequest.DeliveryRequestService;

/**
 * 
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestListController extends SimpleFormController {
  DeliveryRequestService deliveryRequestService;
  private static final String PENDING_DELIVERY_REQUEST_LIST = "pendingDeliveryRequestList";
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
    List pendingDeliveryRequestList = deliveryRequestService.getPendingDeliveryRequestList();
    
    request.getSession().setAttribute(PENDING_DELIVERY_REQUEST_LIST, pendingDeliveryRequestList);  
    return new ModelAndView(this.getSuccessView());
  }

}