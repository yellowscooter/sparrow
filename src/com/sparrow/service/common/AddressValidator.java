package com.sparrow.service.common;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.Address;



/**
 * Validates an {@link Address} instance.
 * @author manishk
 * @since 1.0
 */
public class AddressValidator implements Validator {

  public boolean supports(Class clazz) {
    return Address.class.isAssignableFrom(clazz);
  }

  /*
   * (non-Javadoc)
   * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
   */
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street1", "required", new String[] {"Address Line1"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "required", new String[] {"City"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "required", new String[] {"State"}, "required");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "required", new String[] {"Postal Code"}, "required");

  }

}
