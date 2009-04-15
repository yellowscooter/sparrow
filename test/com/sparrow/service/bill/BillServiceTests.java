package com.sparrow.service.bill;

import java.util.Date;

import com.sparrow.domain.Bill;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.subscription.SubscriptionPlanService;
import com.sparrow.service.user.UserService;

/**
 * Test Bill service
 * @author Manish Kumar
 * @since 1.0
 */
public class BillServiceTests extends AbstractServiceTests {
  private BillService billService;
  private UserService userService;
  private SubscriptionPlanService subscriptionPlanService;
  
  public BillService getBillService() {
    return billService;
  }

  public void setBillService(BillService billService) {
    this.billService = billService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }

  public void testSaveBill() {
    User user = userService.getUserById(1001);
    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionPlanById(1);
    Bill bill = new Bill(user, subscriptionPlan, 1000, new Date());
    billService.saveBill(bill);    
    
  }
  
  
  public void testCreateUserBill() {
    User user = userService.getUserById(1001);
    Bill bill = billService.createUserBill(user);
    assertTrue("Bill not created", bill.getBillId() != 0);
  }
}
