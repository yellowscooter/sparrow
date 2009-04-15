package com.sparrow.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.providers.ProviderManager;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Author;
import com.sparrow.domain.City;
import com.sparrow.domain.Company;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.AuthorEditor;
import com.sparrow.service.catalog.CategoryEditor;
import com.sparrow.service.catalog.CompanyEditor;
import com.sparrow.service.user.CityEditor;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

public class AddUserFormController extends SimpleFormController {
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
    refData.put("pageTitle", "pageTitle.addNewUser");
    refData.put("metaDescription", "metaDescription.addNewUser");
    
    
    
    return refData;
  }
    


  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    User user = (User) command;
    String password = user.getPassword();
    
    userService.saveUserAsNormalUser(user);
    Map model = errors.getModel();
    model.put("message", "user.Created");
    
    //the user just created his/her account. login the user automatically using the username/password specified
    WebApplicationContext webApplicationContext = getWebApplicationContext();
    ProviderManager providerManager = (ProviderManager)webApplicationContext.getBean("authenticationManager");
    String detail = request.getRemoteAddr();
    //to perform login, we should pass undigested password...i.e. value as provided by the
    //user on the UI
    GeneralUtils.performUserLogin(user.getUsername(), password, providerManager, detail);
    
    return new ModelAndView(getSuccessView(), model);
  }
  
  

}
