package com.sparrow.domain;

public class TestLineItem {
  private String orderId;
  private String lineItemDesc;
  private Product product;
  
  public String getLineItemDesc() {
    return lineItemDesc;
  }
  public void setLineItemDesc(String lineItemDesc) {
    this.lineItemDesc = lineItemDesc;
  }
  public String getOrderId() {
    return orderId;
  }
  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }
  public Product getProduct() {
    return product;
  }
  public void setProduct(Product product) {
    this.product = product;
  }
  
  
}
