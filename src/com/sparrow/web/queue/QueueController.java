package com.sparrow.web.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.User;
import com.sparrow.service.catalog.ProductServiceImpl;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.web.WebConstants;

public class QueueController extends SimpleFormController {
  UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }


  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
    //call superclass method first...it will create a new form backing command object 
    //if this is a new request. We need command object for search.
    //If its a form submission, then default implementation will do nothing.
    ModelAndView modelAndView = super.handleRequestInternal(request, response);
    
    User user = GeneralUtils.getCurrentUserFromTLS();
    String action = ServletRequestUtils.getStringParameter(request, "action");
    int currentPriority = ServletRequestUtils.getIntParameter(request, "priority", -1);
    
    if (action == null) {
      //do nothing for now
    } else if (action.equals(WebConstants.MOVE_TO_HEAD)) {
      userService.moveProductRequestToHead(user, currentPriority);
    } else if (action.equals(WebConstants.MOVE_ONE_UP)) {
      userService.moveProductRequestOneUp(user, currentPriority);
    } else if (action.equals(WebConstants.MOVE_ONE_DOWN)) {
      userService.moveProductRequestOneDown(user, currentPriority);
    } else if (action.equals(WebConstants.MOVE_TO_TAIL)) {
      userService.moveProductRequestToTail(user, currentPriority);
    } else if (action.equals(WebConstants.REMOVE_FROM_QUEUE)) {
      userService.removeProductRequest(user, currentPriority);
    }
    //fetch the bookshelf list
    request.setAttribute("productRequestsList", user.getProductRequests());
    //fetch the UserProducts list...this is the list of products currently with the user
    //List userProductsList = userService.getUserProductsByUser(user);
    Set userProductsList = user.getUserProducts();
    request.setAttribute("userProductsList", userProductsList);
    
    return modelAndView;
  }

}
