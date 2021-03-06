package com.sparrow.domain;

// Generated Jan 7, 2009 4:19:52 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * ProductReview generated by hbm2java
 */
public class ProductReview implements java.io.Serializable {

  private int productReviewId;

  private int productId;

  private Date reviewDate;

  private Integer userId;

  private String reviewerName;

  private String reviewTitle;

  private String review;

  public ProductReview () {}
  
  public ProductReview(int productId, String reviewerName, String reviewTitle, String review) {
    super();
    this.productId = productId;
    this.reviewerName = reviewerName;
    this.reviewTitle = reviewTitle;
    this.review = review;
  }



  public int getProductReviewId() {
    return this.productReviewId;
  }

  private void setProductReviewId(int productReviewId) {
    this.productReviewId = productReviewId;
  }

  public int getProductId() {
    return this.productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public Date getReviewDate() {
    return this.reviewDate;
  }

  public void setReviewDate(Date reviewDate) {
    this.reviewDate = reviewDate;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getReviewerName() {
    return this.reviewerName;
  }

  public void setReviewerName(String reviewerName) {
    this.reviewerName = reviewerName;
  }

  public String getReviewTitle() {
    return this.reviewTitle;
  }

  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }

  public String getReview() {
    return this.review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("reviewerName:", this.reviewerName)
                                    .append("\nreviewDate:", this.reviewDate)
                                    .append("\nreviewTitle:", this.reviewTitle)
                                    .append("\nreview:", this.review).toString();
  }

  

  
}
