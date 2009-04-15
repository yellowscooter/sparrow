package com.sparrow.service.deliveryrequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.CheckoutInfo;
import com.sparrow.domain.ProductInstance;
import com.sparrow.service.catalog.ProductService;


public class CheckoutValidator implements Validator {
  private static final transient Log logger = LogFactory.getLog(CheckoutValidator.class);
  private ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  public boolean supports(Class clazz) {
    return CheckoutInfo.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    logger.debug("CheckoutValidator");    
    CheckoutInfo checkoutInfo = (CheckoutInfo) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "instanceId", "required", new String[] {"Instance Id"}, "required");
    //if the first check has passed, then do the rest
    if (errors.getErrorCount() == 0) {
      ProductInstance productInstance = productService.getProductInstanceById(checkoutInfo.getInstanceId());
      if (productInstance == null) {
        errors.rejectValue("instanceId", "product.instance.not.found");
      } else {
        //check if we passed in the right instance id
        boolean isInstanceOf = productInstance.isInstanceOf(checkoutInfo.getProductId());
        if (!isInstanceOf) {
          errors.rejectValue("instanceId", "illegal.product.instance");
        }
      }  
    }
    
    
    
    
  }

}
