package com.sparrow.service.subscription;

import com.sparrow.domain.User;

/**
 * This exception is thrown if a {@link User} has not selcted a subscription plan. 
 * @author manishk
 * @since 1.0
 */
public class SubscriptionPlanNotSelectedException extends Exception {

  public SubscriptionPlanNotSelectedException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public SubscriptionPlanNotSelectedException(String message, Throwable cause) {
    super(message, cause);
    // TODO Auto-generated constructor stub
  }

  public SubscriptionPlanNotSelectedException(String message) {
    super(message);
    // TODO Auto-generated constructor stub
  }

  public SubscriptionPlanNotSelectedException(Throwable cause) {
    super(cause);
    // TODO Auto-generated constructor stub
  }

}