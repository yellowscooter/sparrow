package com.sparrow.service.payment;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.PaymentInfo;
import com.sparrow.service.common.AddressValidator;

/**
 * Validate payment information.
 * @author manishk
 * @since 1.0
 */
public class PaymentValidator implements Validator {
  AddressValidator addressValidator;
  
  public AddressValidator getAddressValidator() {
    return addressValidator;
  }

  public void setAddressValidator(AddressValidator addressValidator) {
    this.addressValidator = addressValidator;
  }

  public boolean supports(Class clazz) {
    return PaymentInfo.class.isAssignableFrom(clazz);
  }

  
  public void validate(Object target, Errors errors) {
    PaymentInfo paymentInfo = (PaymentInfo) target;
    //only validate billing address if credit card payment selected
    //and shipping address is not used as billing address.
    if (paymentInfo.getPaymentMethod().equals(PaymentMethodEnum.E_PAYMENT)
        && !paymentInfo.isUseShippingAddressAsBillingAddress()) {
      //validate the billing address
      try {
        errors.pushNestedPath("creditCardInfo.billingAddress");
        ValidationUtils.invokeValidator(this.addressValidator, paymentInfo.getCreditCardInfo().getBillingAddress(), errors);
      } finally {
          errors.popNestedPath();
      }  
    }
  }

}
