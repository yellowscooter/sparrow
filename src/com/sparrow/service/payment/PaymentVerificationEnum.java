package com.sparrow.service.payment;

public enum PaymentVerificationEnum {
  PENDING ("PENDING"),
  COMPLETE ("COMPLETE");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private PaymentVerificationEnum(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }
  

}
