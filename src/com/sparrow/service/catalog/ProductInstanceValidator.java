package com.sparrow.service.catalog;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.service.user.UserServiceImpl;

public class ProductInstanceValidator implements Validator {
  
  public boolean supports(Class clazz) {
    return ProductInstance.class.isAssignableFrom(clazz);
  }
  
  public void validate(Object target, Errors errors) {
    ProductInstance productInstance = (ProductInstance) target;
    //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "submittedBy", "notFound", new String[] {"User"}, "notFound");
    if (productInstance.getSubmittedBy() != null && productInstance.getSubmittedBy().getUsername() == null) {
      errors.rejectValue("submittedBy", "notFound", new String[] {"User"}, "notFound");
    }
  }
  
  

}
