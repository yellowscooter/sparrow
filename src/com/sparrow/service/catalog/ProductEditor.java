package com.sparrow.service.catalog;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.Author;
import com.sparrow.domain.Product;

public class ProductEditor extends PropertyEditorSupport {
  private ProductService productService;
  
  /**
   * Constructor that takes in ProductServiceImpl as argument.
   * @param productService
   * @since 1.0
   */
  public ProductEditor(ProductService productService) {
    this.productService = productService;
  }
  

  public void setAsText(String productId) throws IllegalArgumentException {
    if (productId == null) {
      setValue(null);
    }
    Product product = productService.getProductById(Integer.parseInt(productId));
    setValue(product);
  }
  
  public String getAsText() {
    Product value = (Product)getValue();
    return (value != null ? String.valueOf(value.getProductId()) : "");
  }
}
