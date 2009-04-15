package com.sparrow.domain;

// Generated May 25, 2007 1:51:18 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * Mapping between User and Product Instance. This class provides information
 * about a Product instance that user has checked out or submitted.
 * 
 * @author manishk
 * @since 1.0
 */
public class UserProduct implements java.io.Serializable {

  private int userProductId;

  private User userByUserId;

  private ProductInstance productInstance;
  
  private User userByCheckoutUserId;
  
  private DeliveryRequest deliveryRequest;

  private Date checkoutDate;

  public UserProduct() {
  }

  public UserProduct(User userByUserId, ProductInstance productInstance) {
    this.userByUserId = userByUserId;
    this.productInstance = productInstance;
  }

  public UserProduct(User userByUserId,
      User userByCheckoutUserId, ProductInstance productInstance, 
      Date checkoutDate) {
    this.userByUserId = userByUserId;
    this.userByCheckoutUserId = userByCheckoutUserId;
    this.productInstance = productInstance;
    this.checkoutDate = checkoutDate;
  }

  public int getUserProductId() {
    return this.userProductId;
  }

  private void setUserProductId(int userProductId) {
    this.userProductId = userProductId;
  }

  public User getUserByUserId() {
    return this.userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
  }

  public User getUserByCheckoutUserId() {
    return this.userByCheckoutUserId;
  }

  public void setUserByCheckoutUserId(User userByCheckoutUserId) {
    this.userByCheckoutUserId = userByCheckoutUserId;
  }

  public ProductInstance getProductInstance() {
    return this.productInstance;
  }

  public void setProductInstance(ProductInstance productInstance) {
    this.productInstance = productInstance;
  }

  public DeliveryRequest getDeliveryRequest() {
    return deliveryRequest;
  }

  public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
    this.deliveryRequest = deliveryRequest;
  }

  public Date getCheckoutDate() {
    return this.checkoutDate;
  }

  public void setCheckoutDate(Date checkoutDate) {
    this.checkoutDate = checkoutDate;
  }

}
