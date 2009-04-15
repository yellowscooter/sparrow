package com.sparrow.domain;

public class BooksMostInDemandReportVO {
  
  /**
   * Count of number of times a book was found in user's bookshelfs
   */
  private int count;
  private int productId;
  private String productName;
  
  private Product product;
  
  
  public BooksMostInDemandReportVO() {
    
  }
  public BooksMostInDemandReportVO(int count, int productId, String productName) {
    super();
    this.count = count;
    this.productId = productId;
    this.productName = productName;
  }
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public int getProductId() {
    return productId;
  }
  public void setProductId(int productId) {
    this.productId = productId;
  }
  public String getProductName() {
    return productName;
  }
  public void setProductName(String productName) {
    this.productName = productName;
  }
  public Product getProduct() {
    return product;
  }
  public void setProduct(Product product) {
    this.product = product;
  }
  
  
}
