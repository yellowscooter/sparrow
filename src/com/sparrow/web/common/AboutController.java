package com.sparrow.web.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class AboutController extends AbstractController {

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    
    //set the page title
    Map model = new HashMap();
    model.put("pageTitle", "pageTitle.about");
    model.put("metaDescription", "metaDescription.about");
    return new ModelAndView("/public/common/about", model);
  }

}
