package com.sparrow.service.deliveryrequest;

/**
 * This exceptio is thrown if during the checkout process,
 * it is found that user has not reuturned previous checkedout books.
 * 
 * @author manishk
 * @since 1.0
 */
public class MaximumAllowedCheckoutProductsExceeded extends Exception {

  public MaximumAllowedCheckoutProductsExceeded() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MaximumAllowedCheckoutProductsExceeded(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public MaximumAllowedCheckoutProductsExceeded(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public MaximumAllowedCheckoutProductsExceeded(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

  
}
