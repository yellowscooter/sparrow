package com.sparrow.domain;

// Generated May 11, 2007 4:25:10 PM by Hibernate Tools 3.2.0.b9

/**
 * Product Category key class.
 */
public class ProductCategoryId implements java.io.Serializable {

  private int categoryId;

  private int productId;

  public ProductCategoryId() {
  }

  public ProductCategoryId(int categoryId, int productId) {
    this.categoryId = categoryId;
    this.productId = productId;
  }

  public int getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getProductId() {
    return this.productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  public boolean equals(Object other) {
    if ((this == other))
      return true;
    if ((other == null))
      return false;
    if (!(other instanceof ProductCategoryId))
      return false;
    ProductCategoryId castOther = (ProductCategoryId) other;

    return (this.getCategoryId() == castOther.getCategoryId())
        && (this.getProductId() == castOther.getProductId());
  }

  public int hashCode() {
    int result = 17;

    result = 37 * result + this.getCategoryId();
    result = 37 * result + this.getProductId();
    return result;
  }

}
