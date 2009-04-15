package com.sparrow.dao.bill.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.bill.BillDao;
import com.sparrow.domain.Bill;

public class BillDaoImpl extends HibernateDaoSupport implements BillDao {
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.bill.BillDao#saveBill(com.sparrow.domain.Bill)
   */
  public Bill saveBill(Bill bill) {
    this.getHibernateTemplate().saveOrUpdate(bill);
    return bill;
  }

}
