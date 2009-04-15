package com.sparrow.service.bill;

import com.sparrow.domain.Bill;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;

public interface BillService {

  /**
   * Save new or update an existing {@link Bill}
   * @param bill
   * @return
   * @since 1.0
   */
  public Bill saveBill(Bill bill);
  
  /**
   * Creates a {@link Bill} for {@link User} based on the Users 
   * currently selected {@link SubscriptionPlan}
   * @param user
   * @return
   * @since 1.0
   */
  public Bill createUserBill(User user);
  
  
  /**
   * Cancel User's pending bill
   * @param user
   * @since 1.0
   */
  public void cancelPendingBill(User user);
}
