package com.sparrow.domain;

// Generated Feb 5, 2009 2:16:28 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

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

  public ProductReview() {
  }

  public ProductReview(int productReviewId, int productId, Date reviewDate, String reviewerName, String reviewTitle,
      String review) {
    this.productReviewId = productReviewId;
    this.productId = productId;
    this.reviewDate = reviewDate;
    this.reviewerName = reviewerName;
    this.reviewTitle = reviewTitle;
    this.review = review;
  }

  public ProductReview(int productReviewId, int productId, Date reviewDate, Integer userId, String reviewerName,
      String reviewTitle, String review) {
    this.productReviewId = productReviewId;
    this.productId = productId;
    this.reviewDate = reviewDate;
    this.userId = userId;
    this.reviewerName = reviewerName;
    this.reviewTitle = reviewTitle;
    this.review = review;
  }

  public int getProductReviewId() {
    return this.productReviewId;
  }

  public void setProductReviewId(int productReviewId) {
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

}
