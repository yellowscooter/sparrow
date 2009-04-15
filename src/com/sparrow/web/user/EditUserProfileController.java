package com.sparrow.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.City;
import com.sparrow.domain.User;
import com.sparrow.service.user.CityEditor;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

public class EditUserProfileController extends SimpleFormController {
  UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  /**
   * Sets up a custom property editor for empty fields.
   */
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    binder.registerCustomEditor(City.class, new CityEditor(userService));
  }
  
  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    List cityList = userService.getCityList();
    refData.put("cityList", cityList);
    
    return refData;
  }

  @Override
  protected Object formBackingObject(HttpServletRequest request) throws Exception {
    return GeneralUtils.getCurrentUserFromTLS();
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    User user = (User) command;
    
    userService.saveUser(user);
    
    Map model = errors.getModel();
    model.put("message", "user.profile.updated.success");
    
    return new ModelAndView(getSuccessView(), model);
  }
  
  
  
  
  
  

}
