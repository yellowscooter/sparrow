package com.sparrow.service.deliveryrequest;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ReturnInfo;
import com.sparrow.service.catalog.ProductInstanceStatusEnum;
import com.sparrow.service.catalog.ProductService;

/**
 * Validator for {@link ReturnInfo} object.
 * @author manishk
 * @since 1.0
 */
public class ReturnValidator implements Validator {
  private ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  public boolean supports(Class clazz) {
    return ReturnInfo.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    ReturnInfo returnInfo = (ReturnInfo) target;
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productInstanceId", "required", new String[] {"Instance Id"}, "required");
    
    //  if the first check has passed, then do the rest
    if (errors.getErrorCount() == 0) {
      ProductInstance productInstance = productService.getProductInstanceById(returnInfo.getProductInstanceId());
      if (productInstance == null) {
        errors.rejectValue("productInstanceId", "product.instance.not.found");
      } else if (!productInstance.getStatus().equals(ProductInstanceStatusEnum.CHECKEDOUT.getValue())) {
        //if the status of the product instance is not Checkedout, then we should not be returning it.
        //Its an IllegalState
        errors.rejectValue("productInstanceId", "product.instance.not.checkedout");
      }
    }
  }

}
