package com.sparrow.service.deliveryrequest;

import com.sparrow.domain.DeliveryRequest;

/**
 * Exception thrown when an Incomplete (PENDING, INTRANSIT) {@link DeliveryRequest} already exists 
 * and user is trying to create a new {@link DeliveryRequest}
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestAlreadyExistsException extends Exception {

  public DeliveryRequestAlreadyExistsException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public DeliveryRequestAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public DeliveryRequestAlreadyExistsException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public DeliveryRequestAlreadyExistsException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}
