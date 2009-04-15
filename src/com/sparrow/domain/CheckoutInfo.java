package com.sparrow.domain;

import java.io.Serializable;

/**
 * Contains all information needed to checkout a product Instance to a User
 * @author manishk
 * @since 1.0
 */
public class CheckoutInfo implements Serializable {

  int deliveryRequestId;
  int productId;
  int priority;
  int instanceId;
  

  public int getDeliveryRequestId() {
    return deliveryRequestId;
  }
  public void setDeliveryRequestId(int deliveryRequestId) {
    this.deliveryRequestId = deliveryRequestId;
  }
  public int getInstanceId() {
    return instanceId;
  }
  public void setInstanceId(int instanceId) {
    this.instanceId = instanceId;
  }
  public int getProductId() {
    return productId;
  }
  public void setProductId(int productId) {
    this.productId = productId;
  }

  public int getPriority() {
    return priority;
  }
  
  public void setPriority(int priority) {
    this.priority = priority;
  }
  
  

}
