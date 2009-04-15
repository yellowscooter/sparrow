package com.sparrow.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.service.subscription.SubscriptionPlanService;

public class HowItWorksController extends SimpleFormController {
  
  private SubscriptionPlanService subscriptionPlanService;
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }

  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    //  Get avilable plans
    List subscriptionPlanList = subscriptionPlanService.getActiveSubscriptionPlans();
    //  displayTag will get the list from request
    request.setAttribute("subscriptionPlanList", subscriptionPlanList);
    
    //  set the page title
    Map model = new HashMap();
    model.put("pageTitle", "pageTitle.howItWorks");
    model.put("metaDescription", "metaDescription.howItWorks");
    return model;
  }
  
}
