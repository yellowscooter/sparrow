package com.sparrow.service.user;

import org.jasypt.digest.StringDigester;

import com.sparrow.domain.User;
import com.sparrow.service.util.GeneralUtils;

/**
 * Utility class to provide password validation methods.
 * @author manishk
 * @since 1.0
 */
public class PasswordValidationUtil {
  private int minPasswordLength;
  private StringDigester stringDigester;
  
  public int getMinPasswordLength() {
    return minPasswordLength;
  }

  public void setMinPasswordLength(int minPasswordLength) {
    this.minPasswordLength = minPasswordLength;
  }

  public StringDigester getStringDigester() {
    return stringDigester;
  }

  public void setStringDigester(StringDigester stringDigester) {
    this.stringDigester = stringDigester;
  }


  /**
   * Returns true if the given password matches the password of the currently logged in {@link User}.
   * Else returns false.
   * @param password
   * @return
   * @since 1.0
   */
  public boolean newPasswordMatchesOldPassword (String password) {
    User user = GeneralUtils.getCurrentUserFromTLS();
    return stringDigester.matches(password, user.getPassword());

  }
  
  /**
   * Checks if the password satisfies the required minimum length.
   * @param password
   * @return
   * @since 1.0
   */
  public boolean passwordSatisfiesMinLength(String password) {
    boolean isValid = false;
    if (password.length() >= minPasswordLength ) {
      isValid = true;
    }
    return isValid;
  }
  
  /**
   * Returns true if the specified passwords match.
   * @param password
   * @param confirmPassword
   * @return
   * @since 1.0
   */
  public boolean reconfirmPasswordsMatch(String password, String confirmPassword) {
    if (password == null || confirmPassword == null) {
      throw new IllegalArgumentException("Password and ConfirmPassword cannot be NULL");
    }
    boolean isValid = false;
    if (password.equals(confirmPassword)) {
      isValid = true;
    }
    return isValid;
  }

}
