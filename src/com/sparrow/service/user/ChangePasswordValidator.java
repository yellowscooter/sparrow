package com.sparrow.service.user;

import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.dao.user.hibernate.UserDaoImpl;
import com.sparrow.domain.ChangePassword;

/**
 * Validator for {@link ChangePassword}.
 * 
 * @author manishk
 * @since 1.0
 */
public class ChangePasswordValidator implements Validator {
  private static final Log logger = LogFactory.getLog(ChangePasswordValidator.class);
  
  PasswordValidationUtil passwordValidationUtil;
  private int minPasswordLength;
  
  public int getMinPasswordLength() {
    return minPasswordLength;
  }

  public void setMinPasswordLength(int minPasswordLength) {
    this.minPasswordLength = minPasswordLength;
  }
  
  public PasswordValidationUtil getPasswordValidationUtil() {
    return passwordValidationUtil;
  }

  public void setPasswordValidationUtil(
      PasswordValidationUtil passwordValidationUtil) {
    this.passwordValidationUtil = passwordValidationUtil;
  }

  public boolean supports(Class clazz) {
    return ChangePassword.class.isAssignableFrom(clazz);
  }

  /**
   * Validate the {@link ChangePassword} object by doing incremental validations. 
   */
  public void validate(Object target, Errors errors) {
    ChangePassword changePassword = (ChangePassword) target;
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "required", new String[] {"Old Password"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "required", new String[] {"New Password"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmNewPassword", "required", new String[] {"Confirm New Password"}, "required");
    
    if (errors.getErrorCount() == 0 && !passwordValidationUtil.newPasswordMatchesOldPassword(changePassword.getOldPassword())) {
      errors.rejectValue("oldPassword", "user.password.wrong.password");
      if (logger.isDebugEnabled()) {
        logger.debug("Old and new passwords do not match");
      }
    }
    
    if (errors.getErrorCount() == 0) {
      if (!passwordValidationUtil.passwordSatisfiesMinLength(changePassword.getNewPassword())) {
        errors.rejectValue("newPassword", "user.password.minLength", new Integer[] {new Integer(minPasswordLength)}, "");  
      }
      if (logger.isDebugEnabled()) {
        logger.debug("Password does not satisfy min length of " + minPasswordLength);
      }

    }
    
    if (errors.getErrorCount() == 0) {
      if (!passwordValidationUtil.reconfirmPasswordsMatch(changePassword.getNewPassword(), changePassword.getConfirmNewPassword())) {
        errors.rejectValue("newPassword", "user.password.reconfirm.doNotMatch");  
      }
      if (logger.isDebugEnabled()) {
        logger.debug("New and reconfirm passwords do not match");
      }

    }
    
    

  }

}
