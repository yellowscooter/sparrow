package com.sparrow.service.bill;

/**
 * Enumeration of statuses a Bill can be in
 * @author Manish Kumar
 * @since 1.0
 */
public enum BillStatusEnum {
  PENDING ("PENDING"),
  CANCELLED ("CANCELLED"),
  PAID ("PAID");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private BillStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
