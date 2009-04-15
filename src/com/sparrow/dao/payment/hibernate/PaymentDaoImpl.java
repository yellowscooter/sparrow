package com.sparrow.dao.payment.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.NoDataFoundException;
import com.sparrow.dao.payment.PaymentDao;
import com.sparrow.domain.Payment;

public class PaymentDaoImpl extends HibernateDaoSupport implements PaymentDao {
  
  private static final Log logger = LogFactory.getLog(PaymentDaoImpl.class);


  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.payment.PaymentDao#savePayment(com.sparrow.domain.Payment)
   */
  public Payment savePayment(Payment payment) {
    //return (Payment)this.getHibernateTemplate().merge(payment);
    this.getHibernateTemplate().saveOrUpdate(payment);
    return payment;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.payment.PaymentDao#getPaymentById(java.lang.Long)
   */
  public Payment getPaymentById(Integer paymentId) {
    return (Payment)this.getHibernateTemplate().load(Payment.class, paymentId);
  }
  
  
  
  
  
}
