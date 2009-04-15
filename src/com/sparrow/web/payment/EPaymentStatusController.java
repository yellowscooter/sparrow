package com.sparrow.web.payment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.domain.EPayment;
import com.sparrow.domain.Payment;
import com.sparrow.domain.User;
import com.sparrow.service.payment.PaymentService;
import com.sparrow.service.payment.PaymentServiceImpl;
import com.sparrow.service.payment.PaymentStatusEnum;
import com.sparrow.service.payment.PaymentVerificationEnum;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.service.util.MailUtil;
import com.sparrow.web.WebConstants;

/**
 * Controller that receives back control after the payment gateway has processed the payment. It takes action based on the status 
 * code returned by the payment gateway.
 * 
 * @author Manish Kumar
 * @since 1.0
 */
public class EPaymentStatusController extends AbstractController {
  private static final Log logger = LogFactory.getLog(EPaymentStatusController.class);
  private PaymentService paymentService;
  private UserService userService;
  private MailUtil mailUtil;
  
  public PaymentService getPaymentService() {
    return paymentService;
  }

  public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * The method needs to be updated to properly handle credit card payment
   */
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
                                            throws Exception {
    Map model = new HashMap();

    logger.warn(">>>>>Got control back from ccAvenue...further processing to be done.");
    
    String workingKey = WebConstants.CCAVENUE_WORKING_KEY; 
    String orderId= request.getParameter("Order_Id").trim();
    //get the bill id and payment id from orderId
    int billId = GeneralUtils.getBillIdFromTransactionId(orderId);
    int paymentId = GeneralUtils.getPaymentIdFromTransactionId(orderId);
    
    String amount=request.getParameter("Amount").trim();
    String merchantId=request.getParameter("Merchant_Id").trim();
    String authDesc=request.getParameter("AuthDesc");

    if (authDesc != null) {
      authDesc = authDesc.trim();
    }
    String checksum=request.getParameter("Checksum").trim();

    logger.warn(">>>>>cc Avenue AuthDesc=" + authDesc);
    
    String checksumVerification = GeneralUtils.verifyChecksum(merchantId, orderId , amount, authDesc, workingKey, checksum);
    logger.warn(">>>>>checksumVerification=" + checksumVerification);

    String billing_cust_name=request.getParameter("billing_cust_name");
    String billing_cust_address=request.getParameter("billing_cust_address");
    String billing_cust_country=request.getParameter("billing_cust_country");
    String billing_cust_tel=request.getParameter("billing_cust_tel");
    String billing_cust_email=request.getParameter("billing_cust_email");
    String delivery_cust_name=request.getParameter("delivery_cust_name");
    String delivery_cust_address=request.getParameter("delivery_cust_address");
    String delivery_cust_tel=request.getParameter("delivery_cust_tel");
    String delivery_cust_notes=request.getParameter("delivery_cust_notes");
    String merchant_param=request.getParameter("Merchant_Param"); 
    
    String paymentState = "userId=" + merchant_param + " orderId=" + orderId + " amount=" + amount + " merchantId=" + merchantId + " authDesc=" + authDesc;
    logger.warn(">>>>>paymentState=" + paymentState);
    
    User user = GeneralUtils.getCurrentUserFromTLS();
    //put in sanity check for user id and bill id
    //mismatch in userId or billId should not happen...throw exception and abort.
    if (user.getUserId() != Integer.parseInt(merchant_param) 
        || user.getPendingBill().getBillId() != billId
        || !WebConstants.CCAVENUE_MERACHANT_ID.equals(merchantId)) {
      throw new IllegalStateException("UserId or Bill Id do not match the values received from paymentGateway. Merchant id =" + WebConstants.CCAVENUE_MERACHANT_ID + " userId=" + user.getUserId() + " billId=" + user.getPendingBill().getBillId()
                                      + ".Values received are Merchant id =" + merchantId + " userId=" + merchant_param + " billId" + orderId);
    }
    
    //fetch the Payment using id...this is the payment that was created before we sent 
    EPayment payment = (EPayment)paymentService.getPaymentById(paymentId);
    
    //authDesc == Y for normal epayment success
    //authDesc == B for american express authorization...its a batch process and status email is only available after 5-6 hours
    //as per cc avenue
    if("true".equals(checksumVerification) && "Y".equals(authDesc)) { //success
      logger.warn(">>>>>E-payment processed successfully...saving payment info.");
      
      payment.setStatus(PaymentStatusEnum.SUCCESS.getValue());
      payment.setComment(WebConstants.CC_PAYMENT_SUCCESS_MSG);
      payment.setCcTxDate(new Date());
       
      userService.addUserPayment(payment, user);
      
      mailUtil.sendEPaymentSuccessConfirmationEmail(user, payment);
      logger.warn(">>>>>Credit card Payment info saved and email sent to user");
      
      //below messages are displayed on return jsp
      model.put("status", "cc.payment.success");
      model.put("message", "user.registration.completion");
      
    } else if ("true".equals(checksumVerification) && "B".equals(authDesc)) {
      logger.warn(">>>>>E-payment batch processeing for American Exp card...saving payment info.");
      
      payment.setStatus(PaymentStatusEnum.SUCCESS.getValue());
      payment.setComment(WebConstants.AMEX_CC_PAYMENT_SUCCESS_MSG);
      payment.setCcTxDate(new Date());
       
      userService.addUserPayment(payment, user);
      //Do not send an email here since amex verification is a batch process and takes time
      
      //below messages are displayed on return jsp
      model.put("status", "amex.cc.payment.success");
      model.put("message", "user.registration.completion");
      
    } else if("true".equals(checksumVerification) && "N".equals(authDesc)) {    //payment failure
    
      logger.warn(">>>>>E-payment processing failed.");
      
      payment.setStatus(PaymentStatusEnum.FAIL.getValue());
      payment.setComment(WebConstants.CC_PAYMENT_FAILURE_MSG);
      payment.setCcTxDate(new Date());
       
      paymentService.savePayment(payment);
      
      mailUtil.sendEPaymentFailureEmail(user, payment);
      logger.warn(">>>>>Credit card Payment failure...email sent to user");
      model.put("status", "cc.payment.failure");
      
      //out.println("<br>Thank you for shopping with us.However,the transaction has been declined.");
      
      //Here you need to put in the routines for a failed
      //transaction such as sending an email to customer
      //setting database status etc etc
    } else {      //exception...should not be here
      logger.warn(">>>>>E-payment IllegalState...this should not happen");
      throw new IllegalStateException("E-payment IllegalState...this should not happen");
      //out.println("<br>Security Error. Illegal access detected");
      
      //Here you need to simply ignore this and dont need
      //to perform any operation in this condition
    }
    
    return new ModelAndView("/public/payment/paymentSuccessCC", model);
  }

}
