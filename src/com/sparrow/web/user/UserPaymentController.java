package com.sparrow.web.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.CashPayment;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.Payment;
import com.sparrow.domain.User;
import com.sparrow.service.payment.PaymentMethodEnum;
import com.sparrow.service.payment.PaymentService;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

/**
 * Controller to update payment information
 * @author manishk
 * @since 1.0
 */
public class UserPaymentController extends SimpleFormController {
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
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map model = new HashMap();
    
    int userId = ServletRequestUtils.getRequiredIntParameter(request, "userId");
    User user = userService.getUserById(userId);
    model.put("user", user);
    return model;
  }

  @Override
  protected Object formBackingObject(HttpServletRequest request) throws Exception {
    String paymentMethod = ServletRequestUtils.getRequiredStringParameter(request, "paymentMethod");
    Payment payment = null;
    
    if (PaymentMethodEnum.CHECK.getValue().equals(paymentMethod)) {
      payment = new CheckPayment();  
    } else if (PaymentMethodEnum.CASH.getValue().equals(paymentMethod)) {
      payment = new CashPayment();
    }
    
    return payment;
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    Payment payment = (Payment) command;
    int userId = ServletRequestUtils.getRequiredIntParameter(request, "userId");
    
    //set the processed by user of payment and date
    payment.setProcessedbyUser(GeneralUtils.getCurrentUserFromTLS());
    payment.setProcessedDate(new Date());
    
    //paymentService.updateAndVerifyPaymentDetails(payment);
    userService.addUserPayment(payment, userId);
    ModelMap model = new ModelMap();
    model.put("message", "Payment info updated");
    return new ModelAndView(getSuccessView(), model);
  }

}
