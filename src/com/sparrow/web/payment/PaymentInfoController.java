package com.sparrow.web.payment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.EPayment;
import com.sparrow.domain.Payment;
import com.sparrow.domain.PaymentInfo;
import com.sparrow.domain.User;
import com.sparrow.service.payment.PaymentMethodEnum;
import com.sparrow.service.payment.PaymentService;
import com.sparrow.service.payment.PaymentStatusEnum;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.web.WebConstants;

/**
 * Controller to collect {@link PaymentInfo} from the end User.
 * The {@link User} calling this controller is the one who is making the payment,
 * i.e. is the one who will be charged.
 * @author manishk
 * @since 1.0
 */
public class PaymentInfoController extends SimpleFormController {
  PaymentService paymentService;
  UserService userService;
  
  public PaymentService getPaymentService() {
    return paymentService;
  }

  public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  

  @Override
  protected Object formBackingObject(HttpServletRequest request) throws Exception {
    PaymentInfo paymentInfo = new PaymentInfo();
    User user = GeneralUtils.getCurrentUserFromTLS();
    
    //set the payment info is user has already selected one, so it is selected by defalut in the UI.
    if (user.getPaymentMethod() != null) {
      paymentInfo.setPaymentMethod(user.getPaymentMethod());
    }
    return paymentInfo;
  }

  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map model = new HashMap();
    String action = ServletRequestUtils.getStringParameter(request, "action");
    request.setAttribute("action", action);
    User user = GeneralUtils.getCurrentUserFromTLS();
    model.put("user", user);
    return model;
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    User user = GeneralUtils.getCurrentUserFromTLS();
    Map model = errors.getModel();
    PaymentInfo paymentInfo = (PaymentInfo) command;
    
    //User has chosen to do Electronic Payment
    if (paymentInfo.getPaymentMethod().equals(PaymentMethodEnum.E_PAYMENT.getValue())) {
      //user has chosen to do epayment...create a new Payment object and save it. The paymentId will be
      //passed to ccAvenue as part of unique transaction id (order id). The Payment object will then be updated with the status
      //once control comes back from cc avenue, with the status
      logger.warn(">>>>>User has chosen EPayment. " + user.toString());
      EPayment payment = new EPayment();
      payment.setBill(user.getPendingBill());
      payment.setPaymentMethod(PaymentMethodEnum.E_PAYMENT);
      //set status as Processing...this will be updated after we get status back from Payment Gateway
      payment.setStatus(PaymentStatusEnum.PROCESSING.getValue());
      paymentService.savePayment(payment);
      
      String merchant_Id = WebConstants.CCAVENUE_MERACHANT_ID;

      String amount = String.valueOf(user.getPendingBill().getAmount());

      logger.warn(">>>>>Amount to be charged=" + amount);
      
      String order_id = GeneralUtils.generateTransactionId(user.getPendingBill(), payment);
      logger.warn(">>>>>OrderId = " + order_id);
      
      String redirectUrl = WebConstants.CCAVENUE_REDIRECT_URL;
      String working_key = WebConstants.CCAVENUE_WORKING_KEY;
      model.put("Merchant_Id", merchant_Id);
      
      model.put("Amount", amount);
      model.put("Order_Id", order_id);
      model.put("Redirect_Url", redirectUrl);
      
      String checkSum = GeneralUtils.getChecksum(merchant_Id, order_id, amount, redirectUrl, working_key);
      
      model.put("Checksum", checkSum);
      model.put("billing_cust_name", user.getFullname());
      model.put("billing_cust_address", user.getShippingAddress().getStreet1() + " " + user.getShippingAddress().getStreet2());
      model.put("billing_cust_country", "India");
      model.put("billing_cust_state", user.getShippingAddress().getState());
      model.put("billing_cust_tel", user.getPhone());
      model.put("billing_cust_country", "India");
      model.put("billing_cust_email", user.getUsername());
      model.put("delivery_cust_name", user.getFullname());
      model.put("delivery_cust_address", user.getShippingAddress().getStreet1() + " " + user.getShippingAddress().getStreet2());
      model.put("delivery_cust_country", "India");
      model.put("delivery_cust_state", user.getShippingAddress().getState());
      model.put("delivery_cust_tel", user.getPhone());
      
      model.put("delivery_cust_notes", "Friends Of Books Bill");
      model.put("Merchant_Param", user.getUserId());
      
      model.put("billing_cust_city", user.getShippingAddress().getCity());
      model.put("billing_zip_code", user.getShippingAddress().getPostalCode());
      model.put("delivery_cust_city", user.getShippingAddress().getCity());
      model.put("delivery_zip_code", user.getShippingAddress().getPostalCode());
      
      
      logger.warn(">>>>>Sending control to ccAvenue. Redirecting to" +  WebConstants.CCAVENUE_FORWARD_URL);

      return new ModelAndView("redirect:" + WebConstants.CCAVENUE_FORWARD_URL, model);
      //return new ModelAndView("redirect:/ccpaymentstatus.htm", model);
    } else {    //cash or cheque payment
      logger.warn(">>>>>User has chosen payment by cash or cheque." + user.toString());
      
      model.put("paymentMethod", paymentInfo.getPaymentMethod());
      userService.processUserSubscriptionPlanManualPayment(user, paymentInfo);
      
      //model.put("paymentId", payment.getPaymentId());
      model.put("amount", user.getPendingBill().getAmount());  
    }
    
    return new ModelAndView(getSuccessView(), model);
  }
  
}
