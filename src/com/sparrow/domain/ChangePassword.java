package com.sparrow.domain;

/**
 * Simple value object to hold change password data.
 * 
 * @author manishk
 * @since 1.0
 */
public class ChangePassword {
  private String oldPassword;
  private String newPassword;
  private String confirmNewPassword;
  
  public String getConfirmNewPassword() {
    return confirmNewPassword;
  }
  public void setConfirmNewPassword(String confirmNewPassword) {
    this.confirmNewPassword = confirmNewPassword;
  }
  public String getNewPassword() {
    return newPassword;
  }
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
  public String getOldPassword() {
    return oldPassword;
  }
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }
  
  

}
