package com.sparrow.dao.subscription.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.subscription.SubscriptionPlanDao;
import com.sparrow.domain.SubscriptionPlan;


public class SubscriptionPlanDaoImpl extends HibernateDaoSupport implements SubscriptionPlanDao {

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.subscription.SubscriptionPlanDao#getSubscriptionPlans()
   */
  public List getSubscriptionPlans() {
    return this.getHibernateTemplate().loadAll(SubscriptionPlan.class);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.subscription.SubscriptionPlanDao#getActiveSubscriptionPlans()
   */
  public List getActiveSubscriptionPlans() {
    return this.getHibernateTemplate().find("from SubscriptionPlan where status = 'ACTIVE' order by period, maxRentalsPerMonth");
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.subscription.SubscriptionPlanDao#getSubscriptionPlanById(int)
   */
  public SubscriptionPlan getSubscriptionPlanById(int subscriptionPlanId) {
    return (SubscriptionPlan)this.getHibernateTemplate().load(SubscriptionPlan.class, subscriptionPlanId);
  }

  
}
