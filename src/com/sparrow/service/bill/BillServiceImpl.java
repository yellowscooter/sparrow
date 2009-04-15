package com.sparrow.service.bill;

import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.sparrow.dao.bill.BillDao;
import com.sparrow.domain.Bill;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.service.user.UserServiceImpl;

public class BillServiceImpl implements BillService {
  private static transient Log logger = LogFactory.getLog(BillService.class);
  private BillDao billDao;
  private ResourceBundleMessageSource messageSource;
  
  public BillDao getBillDao() {
    return billDao;
  }

  public void setBillDao(BillDao billDao) {
    this.billDao = billDao;
  }
  
  
  public ResourceBundleMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.bill.BillService#saveBill(com.sparrow.domain.Bill)
   */
  public Bill saveBill(Bill bill) {
    return billDao.saveBill(bill);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.bill.BillService#createUserBill(com.sparrow.domain.User)
   */
  public Bill createUserBill(User user) {
    //get subscription plan for user
    SubscriptionPlan subscriptionPlan = user.getSubscriptionPlan();
    Bill bill;
    int payment = 0;
    String description;
    //If user has been billed once, then this is not the first bill. Only the first Bill will have deposit added to the amount
    //Bills after that will just have the fee
    if (user.isUserBeenBilledOnce()) {      //not the first bill
      payment = subscriptionPlan.getFee();
      description = messageSource.getMessage("user.subscrition.fee", null, null) + " " + subscriptionPlan.getFee();
    } else {    //first Bill, so add deposit
      payment = subscriptionPlan.getFee() + subscriptionPlan.getDeposit();
      description = messageSource.getMessage("user.subscrition.fee", null, null) + " " + subscriptionPlan.getFee() + " + " 
                    + messageSource.getMessage("user.subscrition.deposit", null, null) + " " + subscriptionPlan.getDeposit();
    }
    bill = new Bill(user, subscriptionPlan, payment, new Date());
    //newly created bills are in pending status
    bill.setStatus(BillStatusEnum.PENDING.getValue());
    bill.setDescription(description);
    
    user.getBills().add(bill);
    bill = this.saveBill(bill);
    if (logger.isDebugEnabled()) {
      logger.debug("Bill created for user=" + user.getUsername() + "Bill Id, Amount = " + bill.getBillId() + "," + bill.getAmount());  
    }
    
    return bill;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.bill.BillService#cancelPendingBill(com.sparrow.domain.User)
   */
  public void cancelPendingBill(User user) {
    if (user.getPendingBill() == null) {
      throw new IllegalArgumentException("User does not have a pending bill, userId=" + user.getUserId());
    }
    Bill pendingBill = user.getPendingBill();
    
    pendingBill.setStatus(BillStatusEnum.CANCELLED.getValue());
    pendingBill.setComment(messageSource.getMessage("subscription.change.pending.bill.cancel", null, null));
    
  }
  
  
  

}
