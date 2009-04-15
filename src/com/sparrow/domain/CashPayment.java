package com.sparrow.domain;

import com.sparrow.service.payment.PaymentMethodEnum;

public class CashPayment extends Payment {

  public CashPayment() {
    super();
    setPaymentMethod(PaymentMethodEnum.CASH);
  }

 
}
