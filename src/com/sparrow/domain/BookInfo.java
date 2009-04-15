package com.sparrow.domain;

import java.util.List;



/**
 * This class is used to map to the xml document geenrated by collectorz.
 * It contains a list of {@link Product} 
 * Note: This class is only used to load Product data into the system using
 * Castor. It is used in no other way.
 * 
 * @author manishk
 * @since 1.0
 */
public class BookInfo {
  private List productList;

  public List getProductList() {
    return productList;
  }

  public void setProductList(List productList) {
    this.productList = productList;
  }
  
  
  
  

}
