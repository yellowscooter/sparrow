package com.sparrow.service.catalog;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.User;
import com.sparrow.service.user.UserNotFoundException;
import com.sparrow.service.user.UserService;

public class UserEditor extends PropertyEditorSupport {
  private UserService userService;
  
  public UserEditor(UserService userService) {
    this.userService = userService;
  }

  /**
   * Sets the User instance for a given username. If the user is not found,
   * sets null.
   */
  public void setAsText(String username) throws IllegalArgumentException {
    User user;
    if (username == null || "".equals(username)) {
      user = null;
    } else {
      try {
        user = userService.getUserByUserName(username);  
      }  catch (UserNotFoundException unf) {
        //just set an empty user instance
        user = new User();
      }  
    }
    setValue(user);
  }
  
  public String getAsText() {
    User value = (User)getValue();
    return (value != null ? String.valueOf(value.getUserId()) : "");
  }

}
