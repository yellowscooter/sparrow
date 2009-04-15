package com.sparrow.service.payment;

import java.util.Date;
import java.util.GregorianCalendar;

import com.sparrow.domain.BankDraftPayment;
import com.sparrow.domain.Bill;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.EPayment;
import com.sparrow.domain.Payment;
import com.sparrow.domain.User;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.bill.BillService;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.UserService;

public class PaymentServiceTests extends AbstractServiceTests {
  private PaymentService paymentService;
  private UserService userService;
  private BillService billService;
  
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public PaymentService getPaymentService() {
    return paymentService;
  }

  public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
  
  public BillService getBillService() {
    return billService;
  }

  public void setBillService(BillService billService) {
    this.billService = billService;
  }

  public void testSaveEPayment() throws Exception {
    //MembershipFee membershipFee = paymentService.getCurrentAnnualMembershipFee();
    User user = userService.getUserByUserName("user@friendsofbooks.com"); 
    EPayment payment = new EPayment();
    //payment.setMembershipFee(membershipFee);
    //payment.setUser(user);
    payment.setBill(user.getPendingBill());
    payment.setCcConfirmationId("1000");
    payment.setCcTxDate(new Date());
    //payment.setVerification("PENDING");
    Payment savedPayment = paymentService.savePayment(payment);
    assertTrue("The payment id should be greter than 0 for saved payment", savedPayment.getPaymentId() > 0);
    //just check if updates are happening
    User user2 = userService.getUserByUserName("admin@friendsofbooks.com");
    savedPayment.setProcessedbyUser(user2);
    paymentService.savePayment(savedPayment);
    
  }
  
  public void testSaveCheckPayment() throws Exception {
    //MembershipFee membershipFee = paymentService.getCurrentAnnualMembershipFee();
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    Bill bill = billService.createUserBill(user);

    CheckPayment payment = new CheckPayment();
    //payment.setMembershipFee(membershipFee);
    //payment.setUser(user);
    payment.setCheckBankName("Bank of India");
    payment.setCheckNumber("420");
    payment.setBill(bill);
    //payment.setVerification("PENDING");
    Payment savedPayment = paymentService.savePayment(payment);
    assertTrue("The payment id should be greter than 0 for saved payment", savedPayment.getPaymentId() > 0);
    
  }
  
//  public void testSaveBankDraftPayment() throws Exception {
//    //MembershipFee membershipFee = paymentService.getCurrentAnnualMembershipFee();
//    User user = userService.getUserByUserName("user@friendsofbooks.com"); 
//    BankDraftPayment payment = new BankDraftPayment();
//    //payment.setMembershipFee(membershipFee);
//    //payment.setUser(user);
//    payment.setDraftBankName("Bank of India");
//    payment.setDraftNumber("100011");
//    //payment.setVerification("PENDING");
//    Payment savedPayment = paymentService.savePayment(payment);
//    assertTrue("The payment id should be greter than 0 for saved payment", savedPayment.getPaymentId() > 0);
//    //see if i can get the payment back by id
//    Payment payment1 = paymentService.getPaymentById(savedPayment.getPaymentId());
//    assertNotNull("Payment object should have been fetched", payment1);
//  }
//  
  public void testGetPaymentById() {
    Payment payment = paymentService.getPaymentById(1000);
    assertNotNull("Payment object should have been fetched", payment);
    
    
  }
  
  
  
  

}
