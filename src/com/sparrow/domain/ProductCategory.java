package com.sparrow.domain;


/**
 * A single Product in a Category. This class represents an 
 * association between Product and Category classes, 
 * to facilitate many-to-many mapping.
 *  
 * @author manishk
 * @since 1.0
 */
public class ProductCategory implements java.io.Serializable {

  private ProductCategoryId id;

  private Category category;

  private Product product;
  
  /**
   * Indicates if this is a primary category for a product.
   * A product can only have one primary category. Can have 2 values, Y or N...default is N
   */
  private String isPrimary = "N";

  public ProductCategory() {
  }

  public ProductCategory(Category category, Product product) {
    this.category = category;
    this.product = product;
    id = new ProductCategoryId(category.getCategoryId(), product.getProductId());
  }

  public ProductCategoryId getId() {
    return this.id;
  }

  private void setId(ProductCategoryId id) {
    this.id = id;
  }

  public Category getCategory() {
    return this.category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Product getProduct() {
    return this.product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
  
  public String getIsPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(String isPrimary) {
    this.isPrimary = isPrimary;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((category == null) ? 0 : category.hashCode());
    result = PRIME * result + ((product == null) ? 0 : product.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final ProductCategory other = (ProductCategory) obj;
    if (category == null) {
      if (other.category != null)
        return false;
    } else if (!category.equals(other.category))
      return false;
    if (product == null) {
      if (other.product != null)
        return false;
    } else if (!product.equals(other.product))
      return false;
    return true;
  }
  
  

}
