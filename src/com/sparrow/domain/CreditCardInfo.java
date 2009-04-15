package com.sparrow.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Information required to process credit card payment.
 * @author manishk
 * @since 1.0
 */
public class CreditCardInfo {
  /**
   * VISA, MASTERCARD etc
   */
  private String creditCardType;

  /**
   * Name of owner of card
   */
  private String nameOnCreditCard;
  
  private String creditCardNumber;
  
  private String expirationMonth;
  
  private String expirationYear;
  
  private String securityCVCCode;
  
  private Address billingAddress = new Address();

  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public String getCreditCardType() {
    return creditCardType;
  }

  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  public String getExpirationMonth() {
    return expirationMonth;
  }

  public void setExpirationMonth(String expirationMonth) {
    this.expirationMonth = expirationMonth;
  }

  public String getExpirationYear() {
    return expirationYear;
  }

  public void setExpirationYear(String expirationYear) {
    this.expirationYear = expirationYear;
  }

  public String getNameOnCreditCard() {
    return nameOnCreditCard;
  }

  public void setNameOnCreditCard(String nameOnCreditCard) {
    this.nameOnCreditCard = nameOnCreditCard;
  }

  public String getSecurityCVCCode() {
    return securityCVCCode;
  }

  public void setSecurityCVCCode(String securityCVCCode) {
    this.securityCVCCode = securityCVCCode;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("nameOnCreditCard",
        this.nameOnCreditCard).append("creditCardNumber",
        this.creditCardNumber).append("expirationMonth", this.expirationMonth).append("expirationYear",
            this.expirationYear)
        .append("billingAddress", this.billingAddress).append(
            "securityCVCCode", this.securityCVCCode)
        .append("creditCardType", this.creditCardType).toString();
  }
  
  
  
}
