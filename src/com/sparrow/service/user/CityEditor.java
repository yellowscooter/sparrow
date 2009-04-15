package com.sparrow.service.user;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.City;

public class CityEditor extends PropertyEditorSupport {
  private UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  public CityEditor(UserService userService) {
    super();
    this.userService = userService;
  }

  /**
   * Sets the user as null if passed Id is null or "" (In the view, this value can be used to
   * create a dummy "Please Select" option in the dropdown.) 
   */
  public void setAsText(String cityId) throws IllegalArgumentException {
    if (cityId == null || "".equals(cityId)) {
      setValue(null);
    } else {
      City city = userService.getCityById(Integer.parseInt(cityId));
      setValue(city);
    }
  }
  
  public String getAsText() {
    City value = (City)getValue();
    return (value != null ? String.valueOf(value.getCityId()) : "");
  }


}
