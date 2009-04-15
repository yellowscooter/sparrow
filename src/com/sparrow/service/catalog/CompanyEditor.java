package com.sparrow.service.catalog;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.Author;
import com.sparrow.domain.Company;

/**
 * PropertyEditor that sets a Company instance for an author id.
 * 
 * @author manishk
 * @since 1.0
 */
public class CompanyEditor extends PropertyEditorSupport {
  private ProductService productService;
  
  /**
   * Constructor that takes in ProductServiceImpl as argument.
   * @param productService
   * @since 1.0
   */
  public CompanyEditor(ProductService productService) {
    this.productService = productService;
  }
  
  /**
   * Sets the company as null if passed Id is null or -1 (In the view, this value can be used to
   * create a dummy "Please Select" option in the dropdown.) 
   */
  public void setAsText(String companyId) throws IllegalArgumentException {
    if (companyId == null || "-1".equals(companyId)) {
      setValue(null);
    } else {
      Company company = productService.getCompanyById(Integer.parseInt(companyId));
      setValue(company);
    }
  }
  
  public String getAsText() {
    Company value = (Company)getValue();
    return (value != null ? String.valueOf(value.getCompanyId()) : "");
  }
}
