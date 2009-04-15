package com.sparrow.domain;

/**
 * Type Safe enumeration class for Product Types.
 * @author manishk
 * @since 1.0
 */
public class ProductType {
  private String type;
  
  public static final ProductType BOOK = new ProductType("BOOK");
  /**
   * Private constructor to only allow self initialization.
   * @param type
   * @since 1.0
   */
  private ProductType(String type) {
    this.type = type;
  }
  
  public String getProductType() {
    return this.type;
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  public boolean equals(Object object) {
    //there is only one instance of a particular operator object. So it the referenecs are same
    //objects are equal, otherwise no.
    if (this == object) {
      return true;
    } else {
      return false;
    }
  }
}
