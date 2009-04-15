package com.sparrow.domain;

/**
 * Represents a Product instance. A product instance is the real product in the system.
 * The product instance Id is the unique identifier of a Product instance. 
 * @author manishk
 * @since 1.0
 */
public class ProductInstance implements java.io.Serializable {

  /**
   * The unique instance id of a Product instance.
   */
  private int productInstanceId;

  /**
   * The Product whose instance this class represents.
   */
  private Product product;

  /**
   * The status of the Product instance
   * A -- Available
   * C -- Checked out
   * D -- Deleted
   */
  private String status;

  /**
   * If the instance is checked out by a user, 
   * UserProduct instance represents the checkout details if the Product has 
   * bee checked out by a User.
   */
  private UserProduct userProduct;
  
  /**
   * If this product instance whas submitted by a User,
   * this is the user who submitted it.
   */
  private User submittedBy;

  public ProductInstance() {
  }

  public ProductInstance(Product product) {
    this.product = product;
  }

  public ProductInstance(User user, Product product,
      String status, UserProduct userProduct) {
    this.submittedBy = user;
    this.product = product;
    this.status = status;
    this.userProduct = userProduct;
  }

  public int getProductInstanceId() {
    return this.productInstanceId;
  }

  private void setProductInstanceId(int productInstanceId) {
    this.productInstanceId = productInstanceId;
  }

  public User getSubmittedBy() {
    return this.submittedBy;
  }

  public void setSubmittedBy(User user) {
    this.submittedBy = user;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public UserProduct getUserProduct() {
    return this.userProduct;
  }

  public void setUserProduct(UserProduct userProduct) {
    this.userProduct = userProduct;
  }
  
  /**
   * If returns true, this is a transient instance (has not been persisted, or new instance)
   * If returns false, this is a persisted or detached instance (for edit)
   * @return
   * @since 1.0
   */
  public boolean isNew() {
    return (this.productInstanceId == 0);
  }
  
  /**
   * Returns true if a {@link ProductInstance} is an instance of the given {@link Product}
   * @param product
   * @return
   * @since 1.0
   */
  public boolean isInstanceOf(Product product) {
    boolean isInstanceOf = false;
    //if product is null, return false
    if (product == null) {
      return isInstanceOf;
    } else {
      isInstanceOf = isInstanceOf(product.getProductId());
    }
    return isInstanceOf;
  }
  
  /**
   * Returns true if a {@link ProductInstance} is an instance of the {@link Product} 
   * with given productId
   * 
   * @param productId
   * @return
   * @since 1.0
   */
  public boolean isInstanceOf(int productId) {
    boolean isInstanceOf = false;
    
    if (this.getProduct().getProductId() == productId) {
      isInstanceOf = true;
    }
    return isInstanceOf;
  }
  
  
 
}
