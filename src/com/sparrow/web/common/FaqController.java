package com.sparrow.web.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.service.subscription.SubscriptionPlanService;

public class FaqController extends AbstractController {
  
  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    //set the page title
    Map model = new HashMap();
    model.put("pageTitle", "pageTitle.faq");
    model.put("metaDescription", "metaDescription.faq");
    return new ModelAndView("/public/common/faq", model);
  }

}
