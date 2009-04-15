package com.sparrow.service.bill;

/**
 * Thrown when no pending bill is found for a User, and an attempt is made to make a payment.
 * 
 * @author Manish Kumar
 * @since 1.0
 */
public class NoPendingBillFoundException extends RuntimeException {

  public NoPendingBillFoundException() {
    // TODO Auto-generated constructor stub
  }

  public NoPendingBillFoundException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public NoPendingBillFoundException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  public NoPendingBillFoundException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

}
