package com.sparrow.domain;

import com.sparrow.service.payment.PaymentMethodEnum;

public class CheckPayment extends Payment {
  
  private String checkNumber;

  private String checkBankName;
  
  public CheckPayment() {
    super();
    setPaymentMethod(PaymentMethodEnum.CHECK);
  }

  public String getCheckNumber() {
    return this.checkNumber;
  }

  public void setCheckNumber(String checkNumber) {
    this.checkNumber = checkNumber;
  }

  public String getCheckBankName() {
    return this.checkBankName;
  }

  public void setCheckBankName(String checkBankName) {
    this.checkBankName = checkBankName;
  }

}
