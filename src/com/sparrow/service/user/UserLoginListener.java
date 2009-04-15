package com.sparrow.service.user;

import java.util.Date;

import org.acegisecurity.event.authentication.AuthenticationSuccessEvent;
import org.acegisecurity.event.authorization.AuthorizedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.sparrow.domain.User;

/**
 * Listener class that listens for {@link AuthorizedEvent} and updates the 
 * last login time of the user.
 * 
 * @author manishk
 * @since 1.0
 */
public class UserLoginListener implements ApplicationListener {
  UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }


  public void onApplicationEvent(ApplicationEvent event) {

    //on userlogin, update the login time
    if ( event instanceof AuthenticationSuccessEvent )
    {
      AuthenticationSuccessEvent authenticationEvent = ( AuthenticationSuccessEvent ) event;
      //on anonymous login (see acegi doc), a string is passed as UserDetail
      //we are only interested in logging application users.
      if (authenticationEvent.getAuthentication().getPrincipal() instanceof User) {
        User user = (User) authenticationEvent.getAuthentication().getPrincipal();
        userService.updateLoginDate(user);
      }
      
    }

  }

}
