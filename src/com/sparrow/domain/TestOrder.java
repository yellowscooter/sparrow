package com.sparrow.domain;

import java.util.ArrayList;
import java.util.List;

public class TestOrder {
  
  private int orderId;
  private String orderDesc;
  List lineItems = new ArrayList();

  public String getOrderDesc() {
    return orderDesc;
  }
  public void setOrderDesc(String orderDesc) {
    this.orderDesc = orderDesc;
  }
  public int getOrderId() {
    return orderId;
  }
  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }
  public List getLineItems() {
    return lineItems;
  }
  public void setLineItems(List lineItems) {
    this.lineItems = lineItems;
  }

}
