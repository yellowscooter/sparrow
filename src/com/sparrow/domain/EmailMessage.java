package com.sparrow.domain;

import java.io.Serializable;

/**
 * All information related to an email message.
 * @author Manish Kumar
 * @since 1.0
 */
public class EmailMessage implements Serializable {
  private String fromEmailId;
  private String senderTitle;
  private String toEmailId;
  private String ccEmailId;
  private String bccEmailId;
  private String subject;
  private String message;
  
  public String getFromEmailId() {
    return fromEmailId;
  }
  public void setFromEmailId(String fromEmailId) {
    this.fromEmailId = fromEmailId;
  }
  public String getSenderTitle() {
    return senderTitle;
  }
  public void setSenderTitle(String senderTitle) {
    this.senderTitle = senderTitle;
  }
  public String getBccEmailId() {
    return bccEmailId;
  }
  public void setBccEmailId(String bccEmailId) {
    this.bccEmailId = bccEmailId;
  }
  public String getCcEmailId() {
    return ccEmailId;
  }
  public void setCcEmailId(String ccEmailId) {
    this.ccEmailId = ccEmailId;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getToEmailId() {
    return toEmailId;
  }
  public void setToEmailId(String toEmailId) {
    this.toEmailId = toEmailId;
  } 
  
  
}
