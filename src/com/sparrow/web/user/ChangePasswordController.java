package com.sparrow.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.ChangePassword;
import com.sparrow.domain.User;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

public class ChangePasswordController extends SimpleFormController {
  UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    ChangePassword changePassword = (ChangePassword) command;
    
    userService.updatePassword(changePassword);
    
    Map model = errors.getModel();
    model.put("message", "user.password.changed");
    
    return new ModelAndView(getSuccessView(), model);
  }
  

}
