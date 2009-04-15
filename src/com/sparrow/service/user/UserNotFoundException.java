package com.sparrow.service.user;

/**
 * This exception is thrown if a User with 
 * a particulat id or username is not found in the system.
 * @author manishk
 * @since 1.0
 */
public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public UserNotFoundException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
