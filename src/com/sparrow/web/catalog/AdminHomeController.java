package com.sparrow.web.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Simple controller to forward to the admin home page.
 * @author manishk
 * @since 1.0
 */
public class AdminHomeController extends AbstractController {
  @Override
  public ModelAndView handleRequestInternal(
                        HttpServletRequest request,
                        HttpServletResponse response) throws Exception {

      return new ModelAndView("admin/adminMain");
  }

}
