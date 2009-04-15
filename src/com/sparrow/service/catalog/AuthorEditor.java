package com.sparrow.service.catalog;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.Author;

/**
 * PropertyEditor that sets an Author instance for an author id.
 * 
 * @author manishk
 * @since 1.0
 */
public class AuthorEditor extends PropertyEditorSupport {
  private ProductService productService;
  
  /**
   * Constructor that takes in ProductServiceImpl as argument.
   * @param productService
   * @since 1.0
   */
  public AuthorEditor(ProductService productService) {
    this.productService = productService;
  }
  
  /**
   * Sets the author as null if passed Id is null or -1 (In the view, this value can be used to
   * create a dummy "Please Select" option in the dropdown.) 
   */
  public void setAsText(String authorId) throws IllegalArgumentException {
    if (authorId == null || "-1".equals(authorId)) {
      setValue(null);
    } else {
      Author author = productService.getAuthorById(Integer.parseInt(authorId));
      setValue(author);
    }
  }
  
  public String getAsText() {
    Author value = (Author)getValue();
    return (value != null ? String.valueOf(value.getAuthorId()) : "");
  }

}
