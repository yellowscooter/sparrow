package com.sparrow.web.user;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.Bill;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.Payment;
import com.sparrow.service.bill.BillStatusEnum;
import com.sparrow.service.common.DateUtils;
import com.sparrow.service.payment.PaymentMethodEnum;
import com.sparrow.service.payment.PaymentVerificationEnum;

public class UserBillListDecorator extends TableDecorator {

  public String getPaymentDetails() {
    StringBuffer display = new StringBuffer();
    Bill bill = (Bill)getCurrentRowObject();
    Payment payment = bill.getPayment();
    if (bill.getStatus().equals(BillStatusEnum.PAID.getValue())) {
      display.append("Payment Id:" + payment.getPaymentId());
      display.append(", Payment Method: " + payment.getPaymentMethod());
      display.append(", Payment Date: " + DateUtils.getMediumDateformatter().format(payment.getPaymentDate()));
      display.append(", Status: " + payment.getStatus());
      if (payment.getPaymentMethod().equals(PaymentMethodEnum.CHECK.getValue())) {
        CheckPayment checkPayment = (CheckPayment)payment;
        display.append(", Check Number: " + checkPayment.getCheckNumber());
        display.append(", Bank Name: " + checkPayment.getCheckBankName());
      }
      
      
      display.append(", Processed By: " + (payment.getProcessedbyUser() != null ? payment.getProcessedbyUser().getUsername() : ""));
      display.append(", Comment: " + payment.getComment());
      
    }
    
    return display.toString();
  }

}
