package com.sparrow.service.catalog;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.sparrow.domain.Book;
import com.sparrow.domain.Product;

public class BookValidator extends ProductValidator {
  public void validate(Object target, Errors errors) {
    super.validate(target, errors);
    Product product = (Book) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "required", new String[] {"ISBN"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numOfPages", "required", new String[] {"NumOfPages"}, "required");
    
    
  }
}
