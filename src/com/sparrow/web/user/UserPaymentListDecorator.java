package com.sparrow.web.user;

import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.BankDraftPayment;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.EPayment;
import com.sparrow.domain.Payment;
import com.sparrow.service.payment.PaymentVerificationEnum;


public class UserPaymentListDecorator extends TableDecorator {
  
  public String getRadio() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    display = "<input type=\"radio\" name=\"selector\" id=\"selector\" value=\"" + payment.getPaymentId() + "\"/>";
    
    return display;
  }
  public String getCcConfirmationId() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof EPayment) {
      display = ((EPayment)payment).getCcConfirmationId();
    }
    return display;
  }
  
  public Date getCcTxDate() {
    Date display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof EPayment) {
      display = ((EPayment)payment).getCcTxDate();
    }
    return display;
  }
  
  public String getCheckNumber() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof CheckPayment) {
      display = ((CheckPayment)payment).getCheckNumber();
    }
    return display;
  }
  
  public String getCheckBankName() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof CheckPayment) {
      display = ((CheckPayment)payment).getCheckBankName();
    }
    return display;
  }

  public String getDraftNumber() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof BankDraftPayment) {
      display = ((BankDraftPayment)payment).getDraftNumber();
    }
    return display;
  }
  
  public String getDraftBankName() {
    String display = null;
    Payment payment = (Payment)getCurrentRowObject();
    if (payment instanceof BankDraftPayment) {
      display = ((BankDraftPayment)payment).getDraftBankName();
    }
    return display;
  }

  
}
