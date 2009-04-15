package com.sparrow.domain;

/**
 * Contains information needed to return a {@link ProductInstance}
 * @author manishk
 * @since 1.0
 */
public class ReturnInfo {
  int productInstanceId;
  String comment;
  
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public int getProductInstanceId() {
    return productInstanceId;
  }
  public void setProductInstanceId(int productInstanceId) {
    this.productInstanceId = productInstanceId;
  }
  
  
}
