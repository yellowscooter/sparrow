package com.sparrow.domain;

import java.util.Date;

import com.sparrow.service.payment.PaymentMethodEnum;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * This class represents one time payment made by a {@link User}. Subclasses will represent
 * a specific payment method i.e. cash payment, check payment, credit card payment etc 
 * @author manishk
 * @since 1.0
 * @see {@link EPayment}, {@link CheckPayment}, {@link CashPayment}, {@link BankDraftPayment}
 */
public abstract class Payment implements java.io.Serializable {

  private int paymentId;
  
  private Date paymentDate = new Date();

  private Bill bill;
  
  private String status;

  private String comment;

  private User processedbyUser;
  
  private Date processedDate;
  
  /*
   * This enum value will be set when an implementation of this class is instantiated
   * This can then be used to display the type of Payment method an instance of this class is
   * (no need to do instance of checks)
   */
  private PaymentMethodEnum paymentMethod;

  public Payment() {
  }

  public Payment(int paymentId, 
      int amount, String status) {
    this.paymentId = paymentId;
    
    this.status = status;
    
  }

  
  public int getPaymentId() {
    return this.paymentId;
  }

  private void setPaymentId(int paymentId) {
    this.paymentId = paymentId;
  }
  
  
  
  public Bill getBill() {
    return bill;
  }

  public void setBill(Bill bill) {
    this.bill = bill;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }


  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public User getProcessedbyUser() {
    return processedbyUser;
  }

  public void setProcessedbyUser(User processedbyUser) {
    this.processedbyUser = processedbyUser;
  }
  
  public Date getProcessedDate() {
    return processedDate;
  }

  public void setProcessedDate(Date processedDate) {
    this.processedDate = processedDate;
  }

  public PaymentMethodEnum getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + paymentId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Payment other = (Payment) obj;
    if (paymentId != other.paymentId)
      return false;
    return true;
  }


}
