package com.sparrow.domain;

import com.sparrow.service.payment.PaymentMethodEnum;

/**
 * This value object captures the payment info input by the user
 * @author manishk
 * @since 1.0
 */
public class PaymentInfo {
  
  /**
   * Set CHECK as default...so its selected in the UI
   */
  private String paymentMethod = PaymentMethodEnum.E_PAYMENT.getValue();
  
  /**
   * This will only be populated if paymentType is credit Card
   */
  private CreditCardInfo creditCardInfo = new CreditCardInfo();
  
  private boolean useShippingAddressAsBillingAddress;

  public CreditCardInfo getCreditCardInfo() {
    return creditCardInfo;
  }

  public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
    this.creditCardInfo = creditCardInfo;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public boolean isUseShippingAddressAsBillingAddress() {
    return useShippingAddressAsBillingAddress;
  }

  public void setUseShippingAddressAsBillingAddress(
      boolean useShippingAddressAsBillingAddress) {
    this.useShippingAddressAsBillingAddress = useShippingAddressAsBillingAddress;
  } 
  
  
  

}
