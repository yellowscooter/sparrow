package com.sparrow.domain;

// Generated Sep 29, 2007 8:11:55 PM by Hibernate Tools 3.2.0.b9

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Domain object for Delivery Request
 */
public class DeliveryRequest implements java.io.Serializable {

  private int deliveryRequestId;
  
  private User user;

  private Date requestDate;

  private String status;

  private Date requestIntransitDate;

  private Date requestCompleteDate;
  
  private Date requestAppliedDate;
  
  private Set userProductSet = new HashSet();

  public DeliveryRequest() {
  }

  public DeliveryRequest(User user, Date requestDate) {
    this.user = user;
    this.requestDate = requestDate;
  }
  
  /**
   * If returns true, this is a transient instance (has not been persisted, or new instance)
   * If returns false, this is a persisted or detached instance (for edit)
   * @return
   * @since 1.0
   */
  public boolean isNew() {
    return (this.deliveryRequestId == 0);
  }


  public int getDeliveryRequestId() {
    return this.deliveryRequestId;
  }

  private void setDeliveryRequestId(int deliveryRequestId) {
    this.deliveryRequestId = deliveryRequestId;
  }
  
  

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getRequestDate() {
    return this.requestDate;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getRequestIntransitDate() {
    return this.requestIntransitDate;
  }

  public void setRequestIntransitDate(Date requestIntransitDate) {
    this.requestIntransitDate = requestIntransitDate;
  }

  public Date getRequestCompleteDate() {
    return this.requestCompleteDate;
  }

  public void setRequestCompleteDate(Date requestCompleteDate) {
    this.requestCompleteDate = requestCompleteDate;
  }

  public Set getUserProductSet() {
    return userProductSet;
  }

  public void setUserProductSet(Set userProductSet) {
    this.userProductSet = userProductSet;
  }

  public Date getRequestAppliedDate() {
    return requestAppliedDate;
  }

  public void setRequestAppliedDate(Date requestAppliedDate) {
    this.requestAppliedDate = requestAppliedDate;
  }



}
