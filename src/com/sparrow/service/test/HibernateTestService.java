package com.sparrow.service.test;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.domain.TestOrder;

public class HibernateTestService extends HibernateDaoSupport implements HibernateTest {
  
  /* (non-Javadoc)
   * @see com.sparrow.service.test.HibernateTest#saveTestOrder(com.sparrow.domain.TestOrder)
   */
  public TestOrder saveTestOrder(TestOrder order) {
    return (TestOrder)this.getHibernateTemplate().merge(order);
  }

  public TestOrder getTestOrder(int orderId) {
    return (TestOrder)this.getHibernateTemplate().load(TestOrder.class, orderId);
  }
  
  
  
  

}
