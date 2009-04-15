package com.sparrow.dao.bill;

import com.sparrow.domain.Bill;

public interface BillDao {
  
  /**
   * Save new or update an existing {@link Bill}
   * @param bill
   * @return
   * @since 1.0
   */
  public Bill saveBill(Bill bill);

}
