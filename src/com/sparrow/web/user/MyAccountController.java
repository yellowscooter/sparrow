package com.sparrow.web.user;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.User;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

/**
 * Controller for use account information.
 * 
 * @author manishk
 * @since 1.0
 */
public class MyAccountController extends SimpleFormController {
  UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    //call superclass method first...it will create a new form backing command object 
    //if this is a new request. We need command object for search.
    //If its a form submission, then default implementation will do nothing.
    ModelAndView modelAndView = super.handleRequestInternal(request, response);
    
    User user = GeneralUtils.getCurrentUserFromTLS();
    //ModelAndView modelAndView = new ModelAndView("public/user/myAccount");
    modelAndView.addObject("user", user);
    
    return modelAndView;
  }

}
