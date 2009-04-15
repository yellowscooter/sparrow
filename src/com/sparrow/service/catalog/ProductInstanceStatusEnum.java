package com.sparrow.service.catalog;

import com.sparrow.domain.ProductInstance;

/**
 * Enumeration of {@link ProductInstance} statuses.
 * 
 * @author manishk
 * @since 1.0
 */
public enum ProductInstanceStatusEnum {
  AVAILABLE ("A"),
  CHECKEDOUT ("C"),
  DELETED ("D");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private ProductInstanceStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
