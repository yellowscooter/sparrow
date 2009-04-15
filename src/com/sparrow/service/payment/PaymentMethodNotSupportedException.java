package com.sparrow.service.payment;

/**
 * This exception is thrown when an unsupported payment method is
 * user for payment.
 * @author manishk
 * @since 1.0
 */
public class PaymentMethodNotSupportedException extends Exception {

  public PaymentMethodNotSupportedException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public PaymentMethodNotSupportedException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public PaymentMethodNotSupportedException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public PaymentMethodNotSupportedException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
