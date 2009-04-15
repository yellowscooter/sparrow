package com.sparrow.service.catalog;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.Author;

public class AuthorValidator implements Validator {
  private ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  public boolean supports(Class clazz) {
    return Author.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    Author author = (Author) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required", new String[] {"First Name"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required", new String[] {"Last Name"}, "required");
    
    if (errors.getErrorCount() == 0 && productService.authorExists(author)) {
      errors.reject("author.exists");
    }

  }

}
