package com.sparrow.service.payment;
/**
 * Enumeration of statuses of a Payment.
 * @author manishk
 * @since 1.0
 */
public enum PaymentStatusEnum { 
    SUCCESS ("Y"),
    FAIL ("N"),
    PROCESSING("P");
    
    /**
     * The code value of an enum type...this is used in comparasions
     */
    private final String value;
    
    private PaymentStatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
    
}
