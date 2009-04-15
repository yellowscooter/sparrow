package com.sparrow.web.deliveryrequest;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.User;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.user.UserService;

/**
 * Controller class to process delivery requests
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestController extends SimpleFormController {
  DeliveryRequestService deliveryRequestService;
  UserService userService;
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
    ModelMap map = new ModelMap();
    int deliveryRequestId = ServletRequestUtils.getRequiredIntParameter(request, "deliveryRequestId");
    //get the deliveryRequest object
    DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(deliveryRequestId);
    map.put("deliveryRequest", deliveryRequest);
    
    //User user = userService.getUserById(deliveryRequest.getUserId());
    User user = deliveryRequest.getUser();
    //populate all products with product instances
    deliveryRequestService.getProductInstances(user.getProductRequests());
    
    map.put("user", user);
    map.put("productRequestsList", user.getProductRequests());
    
    Date membershipExpirationWarningCompareDate = userService.getMembershipExpirationWarningCompareDate();
    map.put("membershipExpirationWarningCompareDate", membershipExpirationWarningCompareDate);
    
    //set boolean value if user has exceeded books/month limit.
    boolean isUserInMonthlyDeliveryRequestLimitForPlan = deliveryRequestService.isUserInMonthlyDeliveryRequestLimitForPlan(user);
    map.put("isUserInMonthlyDeliveryRequestLimitForPlan", isUserInMonthlyDeliveryRequestLimitForPlan);
    
    return new ModelAndView(this.getSuccessView(), map);
  }
}
