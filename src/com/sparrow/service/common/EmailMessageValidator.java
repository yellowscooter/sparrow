package com.sparrow.service.common;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.EmailMessage;
import com.sparrow.service.util.ValidationUtilsWrapper;

public class EmailMessageValidator implements Validator {

  public boolean supports(Class clazz) {
    return EmailMessage.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    EmailMessage emailMessage = (EmailMessage) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromEmailId", "required", new String[] {"From email address"}, "required");
    
    if (!StringUtils.isBlank(emailMessage.getFromEmailId())) {
      //    validate if user emai is valid email.
      if (!ValidationUtilsWrapper.validateEmail(emailMessage.getFromEmailId())) {
        errors.rejectValue("fromEmailId", "invalid.email.id");
      }  
    }
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "required", new String[] {"Message"}, "required");
    

  }

}
