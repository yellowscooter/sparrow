package com.sparrow.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.User;
import com.sparrow.service.subscription.SubscriptionPlanService;
import com.sparrow.service.user.UserService;
import com.sparrow.service.user.UserServiceImpl;
import com.sparrow.service.util.GeneralUtils;

/**
 * Controller to manage selecting a subscription plan for a new user
 * and also updating a plan for an existing user.
 * 
 * Note: This controller is tied to selectSubscriptionPlan.jsp
 * @author manishk
 * @since 1.0
 */
public class SelectSubscriptionPlanFormController extends AbstractController {
  private static transient Log logger = LogFactory.getLog(SelectSubscriptionPlanFormController.class);
  private SubscriptionPlanService subscriptionPlanService;
  private UserService userService;
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    String action = ServletRequestUtils.getStringParameter(request, "action");
    ModelAndView modelAndView = null;
    Map model = new HashMap();

    
    if (action == null || action.equals("update")) {
      if (logger.isDebugEnabled()) {
        logger.debug("Getting subscription plan list. Action = " + action);
      }
      List subscriptionPlanList = subscriptionPlanService.getActiveSubscriptionPlans();
      
      //displayTag will get the list from request
      request.setAttribute("subscriptionPlanList", subscriptionPlanList);
      request.setAttribute("action", action);
      
      modelAndView =  new ModelAndView("public/user/selectSubscriptionPlan");
    } else if ("subscriptionPlanSelect".equals(action) || "subscriptionPlanUpdate".equals(action)) {
      
      User user = GeneralUtils.getCurrentUserFromTLS();
      String subscriptionPlanId = ServletRequestUtils.getRequiredStringParameter(request, "subscriptionPlanId");
      userService.saveUserSubscriptionPlan(user, Integer.parseInt(subscriptionPlanId));
      
      //different success view for select and update
      if ("subscriptionPlanSelect".equals(action)) {
        modelAndView = new ModelAndView("redirect:paymentinfo.htm");  
      } else if ("subscriptionPlanUpdate".equals(action)) {
        model.put("message", "subscription.plan.updated.success");
        modelAndView = new ModelAndView("forward:myaccount.htm", model);
      }
      
      if (logger.isDebugEnabled()) {
        logger.debug("Added user to subscription plan. User = " + user.getUsername() + ", plan id=" + subscriptionPlanId);
      }
    }
    
    return modelAndView; 
    
  }
  
  

  

}
