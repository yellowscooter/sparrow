package com.sparrow.service.payment;

/**
 * Enumeration of various payment methods available.
 * @author manishk
 * @since 1.0
 */
public enum PaymentMethodEnum {
  CHECK ("CK"),
  BANK_DRAFT ("DD"),
  E_PAYMENT ("CC"),
  CASH ("CA");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private PaymentMethodEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
  

}
