package com.sparrow.service.catalog;

public enum CategoryTypeEnum {
  LEFTNAV ("LEFTNAV"), 
  AWARD ("AWARD");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private CategoryTypeEnum (String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }

}
