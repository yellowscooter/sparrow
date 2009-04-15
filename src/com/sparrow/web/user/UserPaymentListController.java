package com.sparrow.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.service.user.UserService;

/**
 * Handle listing of all user Payments
 * 
 * @author manishk
 * @since 1.0
 */
public class UserPaymentListController extends AbstractController {
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
    List userPaymentsList = userService.getUserPaymentsList(userId);
    
    request.setAttribute("userPaymentsList", userPaymentsList);
    
    return new ModelAndView("admin/user/userPaymentsList");
  }
  
  
}
