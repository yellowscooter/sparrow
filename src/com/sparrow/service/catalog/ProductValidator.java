package com.sparrow.service.catalog;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.Product;

/**
 * Class to Validate a Product. This class is Spring dependent.
 * @author manishk
 * @since 1.0
 */
public class ProductValidator implements Validator {

  public boolean supports(Class clazz) {
    return Product.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    Product product = (Product) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required", new String[] {"Title"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required", new String[] {"Description"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "required", new String[] {"Author"}, "required");
    
  }

}