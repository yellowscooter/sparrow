package com.sparrow.domain;

import java.util.Date;

import com.sparrow.service.payment.PaymentMethodEnum;

public class EPayment extends Payment {
  
  private String ccConfirmationId;

  private Date ccTxDate;
  
  public EPayment() {
    super();
    setPaymentMethod(PaymentMethodEnum.E_PAYMENT);
  }
  

  public EPayment(Date ccTxDate) {
    super();
    this.ccTxDate = ccTxDate;
    setPaymentMethod(PaymentMethodEnum.E_PAYMENT);
  }


  public String getCcConfirmationId() {
    return this.ccConfirmationId;
  }

  public void setCcConfirmationId(String ccConfirmationId) {
    this.ccConfirmationId = ccConfirmationId;
  }

  public Date getCcTxDate() {
    return this.ccTxDate;
  }

  public void setCcTxDate(Date ccTxDate) {
    this.ccTxDate = ccTxDate;
  }

}
