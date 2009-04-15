package com.sparrow.service.util;

import org.apache.commons.validator.EmailValidator;

/**
 * Provides user input validation utilities. Acts as a wrapper to Commons Validator API.
 * 
 * @author manishk
 * @since 1.0
 */
public class ValidationUtilsWrapper {
  
  public static boolean validateEmail(String email) {
    EmailValidator emailValidator = EmailValidator.getInstance();
    return emailValidator.isValid(email);
  }
  

}
