package com.sparrow.service.catalog;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import com.sparrow.domain.ProductInstance;

/**
 * {@link PropertyEditor} that sets {@link ProductInstance} for a 
 * product instance id.
 * @author manishk
 * @since 1.0
 */
public class ProductInstanceEditor extends PropertyEditorSupport {
private ProductService productService;
  
  /**
   * Constructor that takes in ProductServiceImpl as argument.
   * @param productService
   * @since 1.0
   */
  public ProductInstanceEditor(ProductService productService) {
    this.productService = productService;
  }
  
  /**
   * Sets the {@link ProductInstance} as null if passed Id is null or -1.
   */
  public void setAsText(String productInstanceId) throws IllegalArgumentException {
    if (productInstanceId == null || "-1".equals(productInstanceId)) {
      setValue(null);
    } else {
      ProductInstance productInstance = productService.getProductInstanceById(Integer.parseInt(productInstanceId));
      setValue(productInstance);
    }
  }
  
  public String getAsText() {
    ProductInstance value = (ProductInstance)getValue();
    return (value != null ? String.valueOf(value.getProductInstanceId()) : "");
  }

}
