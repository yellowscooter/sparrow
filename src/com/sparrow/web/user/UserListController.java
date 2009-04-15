package com.sparrow.web.user;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.PagedList;
import com.sparrow.service.user.UserService;
import com.sparrow.web.WebConstants;

public class UserListController extends SimpleFormController {
  private static final String NEW_USER_LIST = "userList";
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
    //String action = ServletRequestUtils.getStringParameter(request, "action", null);
    //display tag starts page count from 1...so if none is specified (first invocation), use 1 as default
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    String searchCriteria = ServletRequestUtils.getStringParameter(request, "searchCriteria", null);
    String status = ServletRequestUtils.getStringParameter(request, "status", UserStatusEnum.ACTIVE.getValue());
    String searchBy = ServletRequestUtils.getStringParameter(request, "searchBy", WebConstants.SEARCH_BY_LASTNAME);
    
    UserStatusEnum statusEnum = UserStatusEnum.getEnumForValue(status);

    PagedList userList = null;
    
    if (WebConstants.SEARCH_BY_LASTNAME.equals(searchBy)) {
      userList = userService.searchByLastName(page, searchCriteria, statusEnum);  
    } else if (WebConstants.SEARCH_BY_USERID.equals(searchBy)) {
      try {
        userList = userService.searchByUserId(page, searchCriteria, statusEnum);
      } catch (IllegalArgumentException ile) {
        //do nothing here...basically we do not do the search
        //as user passed a non numberic user id
      }
      
    } else if (WebConstants.SEARCH_BY_USERNAME.equals(searchBy)) {
      userList = userService.searchByUsername(page, searchCriteria, statusEnum);
    } else if (WebConstants.SEARCH_BY_PHONE.equals(searchBy)) {
      userList = userService.searchByPhoneNumber(page, searchCriteria, statusEnum);
    } else if (WebConstants.SEARCH_BY_MOBILEPHONE.equals(searchBy)) {
      userList = userService.searchByMobilePhoneNumber(page, searchCriteria, statusEnum);
    }   
    
    request.setAttribute(NEW_USER_LIST, userList);
    request.setAttribute("searchCriteria", searchCriteria);
    request.setAttribute("status", status);
    request.setAttribute("searchBy", searchBy);
    
    Date membershipExpirationWarningCompareDate = userService.getMembershipExpirationWarningCompareDate();
    request.setAttribute("membershipExpirationWarningCompareDate", membershipExpirationWarningCompareDate);
    
    return new ModelAndView(this.getSuccessView());
  }

}