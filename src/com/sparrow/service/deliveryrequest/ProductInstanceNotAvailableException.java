package com.sparrow.service.deliveryrequest;

import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;

/**
 * This exception is thrown when no {@link ProductInstance}
 * are available, but user is trying to checkout a {@link Product}.
 * 
 * @author manishk
 * @since 1.0
 */
public class ProductInstanceNotAvailableException extends Exception {

  public ProductInstanceNotAvailableException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ProductInstanceNotAvailableException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public ProductInstanceNotAvailableException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public ProductInstanceNotAvailableException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
