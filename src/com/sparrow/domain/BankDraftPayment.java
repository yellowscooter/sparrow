package com.sparrow.domain;

import com.sparrow.service.payment.PaymentMethodEnum;

public class BankDraftPayment extends Payment {
  private String draftNumber;

  private String draftBankName;
  
  public BankDraftPayment() {
    super();
    setPaymentMethod(PaymentMethodEnum.BANK_DRAFT);
  }

  public String getDraftNumber() {
    return this.draftNumber;
  }

  public void setDraftNumber(String draftNumber) {
    this.draftNumber = draftNumber;
  }

  public String getDraftBankName() {
    return this.draftBankName;
  }

  public void setDraftBankName(String draftBankName) {
    this.draftBankName = draftBankName;
  }

}
