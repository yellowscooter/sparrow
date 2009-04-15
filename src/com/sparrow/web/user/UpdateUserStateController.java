package com.sparrow.web.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.Product;
import com.sparrow.domain.User;
import com.sparrow.service.user.UserService;
import com.sparrow.web.catalog.ProductNotAssignedToCategoryException;

public class UpdateUserStateController extends SimpleFormController {
  private UserService userService;

  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  
//  protected Map referenceData(HttpServletRequest request) throws Exception {
//    Map refData = new HashMap();
//    int userId = ServletRequestUtils.getIntParameter(request, "userId");
//    User user = userService.getUserById(userId);
//    
//    refData.put("user", user);
//    
//    return new ModelMap("refData", refData);
//  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView modelAndView = new ModelAndView("/admin/user/updateUserState");
    int userId = ServletRequestUtils.getIntParameter(request, "userId");
    String submitAction = ServletRequestUtils.getStringParameter(request, "submitAction");
    
    if (submitAction == null) {
      User user = userService.getUserById(userId);
      modelAndView.addObject("user", user);
      String action = ServletRequestUtils.getStringParameter(request, "action");
      modelAndView.addObject("action", action);
    
    } else if (UserStatusEnum.INACTIVE.getValue().equals(submitAction)) {
      userService.setUserAsInactive(userId);  
      
      modelAndView.addObject("message", "User marked as inactive.");    
      modelAndView.setViewName(getSuccessView());
    } else if (UserStatusEnum.TERMINATED.getValue().equals(submitAction)) {
      userService.setUserAsTerminated(userId);  
      
      modelAndView.addObject("message", "User account Termintated.");    
      modelAndView.setViewName(getSuccessView());
      
    } else if (UserStatusEnum.SUSPENDED.getValue().equals(submitAction)) {
      userService.setUserAsSuspended(userId);  
      
      modelAndView.addObject("message", "User account Suspended.");    
      modelAndView.setViewName(getSuccessView());
      
    } else if (UserStatusEnum.ACTIVE.getValue().equals(submitAction)) {
      userService.setUserAsActive(userId);  
      
      modelAndView.addObject("message", "User marked as Active.");    
      modelAndView.setViewName(getSuccessView());
      
    }
    
    // TODO Auto-generated method stub
    return modelAndView;
  }

}
