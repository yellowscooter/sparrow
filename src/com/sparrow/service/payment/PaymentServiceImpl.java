package com.sparrow.service.payment;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sparrow.dao.payment.PaymentDao;
import com.sparrow.domain.BankDraftPayment;
import com.sparrow.domain.Bill;
import com.sparrow.domain.CashPayment;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.EPayment;
import com.sparrow.domain.Payment;
import com.sparrow.domain.PaymentInfo;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.service.subscription.SubscriptionPlanNotSelectedException;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;


public class PaymentServiceImpl implements PaymentService {
  private static final Log logger = LogFactory.getLog(PaymentServiceImpl.class);
  PaymentDao paymentDao;
  
  UserService userService;
  
  public PaymentDao getPaymentDao() {
    return paymentDao;
  }

  public void setPaymentDao(PaymentDao paymentDao) {
    this.paymentDao = paymentDao;
  }
  
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.payment.PaymentService#savePayment(com.sparrow.domain.Payment)
   */
  public Payment savePayment(Payment payment) {
    return paymentDao.savePayment(payment);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.payment.PaymentService#updateAndVerifyPaymentDetails(com.sparrow.domain.Payment)
   */
  public Payment updateAndVerifyPaymentDetails(Payment payment) {
    //set the processed by user
    User user = GeneralUtils.getCurrentUserFromTLS();
    payment.setProcessedbyUser(user);
    payment.setProcessedDate(new Date());
    if (logger.isInfoEnabled()) {
      logger.info("Payment id:" + payment.getPaymentId() + " updated by user: " + user.getUserId());
    }
    return this.savePayment(payment);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.payment.PaymentService#getPaymentById(int)
   */
  public Payment getPaymentById(int paymentId) {
    return paymentDao.getPaymentById(new Integer(paymentId));
  }

  

 
  
  
}
