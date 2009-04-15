package com.sparrow.web.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.domain.User;
import com.sparrow.service.user.UserService;

public class UserBillListController extends AbstractController {
  private UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  
  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    int userId = ServletRequestUtils.getRequiredIntParameter(request, "userId");
    User user = userService.getUserById(userId);
    
    request.setAttribute("userBillList", new ArrayList(user.getBills()));
    request.setAttribute("user", user);
    
    return new ModelAndView("admin/user/userBillList");
  }

}
