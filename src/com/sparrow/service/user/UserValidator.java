package com.sparrow.service.user;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import com.sparrow.domain.User;
import com.sparrow.service.common.AddressValidator;
import com.sparrow.service.util.ValidationUtilsWrapper;

/**
 * Validator for {@link User}.
 * @author manishk
 * @since 1.0
 */
public class UserValidator implements Validator {
  private UserService userService;
  AddressValidator addressValidator;
  private int minPasswordLength;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public AddressValidator getAddressValidator() {
    return addressValidator;
  }

  public void setAddressValidator(AddressValidator addressValidator) {
    this.addressValidator = addressValidator;
  }

  public int getMinPasswordLength() {
    return minPasswordLength;
  }

  public void setMinPasswordLength(int minPasswordLength) {
    this.minPasswordLength = minPasswordLength;
  }

  public boolean supports(Class clazz) {
    return User.class.isAssignableFrom(clazz);
  }

  /**
   * Validates a {@link User}. Some validations are only fired for new user
   */
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    //validate username
    if (user.isNew()) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required", new String[] {"Username"}, "required");
      if (!StringUtils.isBlank(user.getUsername())) {
        //    validate if username is valid email.
        if (!ValidationUtilsWrapper.validateEmail(user.getUsername())) {
          errors.rejectValue("username", "user.username.invalid");
        }  
      }
      
      //password
     
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required", new String[] {"Password"}, "required");
      if (!StringUtils.isBlank(user.getPassword())) {
        //    validate password is min length
        if (user.getPassword().length() < minPasswordLength ) {
          errors.rejectValue("password", "user.password.minLength", new Integer[] {new Integer(minPasswordLength)}, "");  
        }
      }
      //confirm password
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required", new String[] {"Reconfirm Password"}, "required");
      if (!StringUtils.isBlank(user.getConfirmPassword())) {
        //    validate if passwords match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
          errors.rejectValue("password", "user.password.doNotMatch");
        }
      }
    
    }
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "required", new String[] {"FirstName"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "required", new String[] {"LastName"}, "required");
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "required", new String[] {"Phone Number"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "required", new String[] {"Location"}, "required");
    
    //call the address validator
    try {
      errors.pushNestedPath("shippingAddress");
      ValidationUtils.invokeValidator(this.addressValidator, user.getShippingAddress(), errors);
    } finally {
        errors.popNestedPath();
    }
    
    //terms of use should be validated in the end since messages are displayed in order they are found
    if (user.isNew()) {
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "acceptTerms", "user.terms.acceptance.required");  
    }
    
    
    //only if errors count == 0 do we need to go to second level of validations
    if (user.isNew() && errors.getErrorCount() == 0) {
      
      //validate if username is valid email.
//      if (!ValidationUtilsWrapper.validateEmail(user.getUsername())) {
//        errors.rejectValue("username", "user.username.invalid");
//      }
      
      //check if the user already exists...if yes, reject duplicate username
      if (userService.getUserAlreadyExists(user.getUsername())) {
        errors.rejectValue("username", "user.already.exists");
      }
      
      //validate password is so min length
//      if (user.getPassword().length() < minPasswordLength ) {
//        errors.rejectValue("password", "user.password.minLength", new Integer[] {new Integer(minPasswordLength)}, "");  
//      }
      
      //    validate if passwords match
//      if (!user.getPassword().equals(user.getConfirmPassword())) {
//        errors.rejectValue("password", "user.password.doNotMatch");
//      }
    }
      
    
    
    
    

  }

}
