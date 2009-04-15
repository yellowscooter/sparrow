package com.sparrow.dao.deliveryrequest;

/**
 * Enumeration of various statuses a delivery request can be in.
 * @author manishk
 * @since 1.0
 */
public enum DeliveryRequestStatusEnum {
  PENDING ("PENDING"),
  INTRANSIT ("INTRANSIT"),
  COMPLETE ("COMPLETE");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private DeliveryRequestStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
