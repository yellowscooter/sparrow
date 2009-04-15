package com.sparrow.web.catalog;

import com.sparrow.domain.Category;
import com.sparrow.domain.Product;

/**
 * This exception is thrown if a {@link Product} not assigned to any 
 * {@link Category} is being activated.
 * @author manishk
 * @since 1.0
 */
public class ProductNotAssignedToCategoryException extends Exception {

  public ProductNotAssignedToCategoryException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ProductNotAssignedToCategoryException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public ProductNotAssignedToCategoryException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public ProductNotAssignedToCategoryException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
