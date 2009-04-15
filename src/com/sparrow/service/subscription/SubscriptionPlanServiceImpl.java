package com.sparrow.service.subscription;

import java.util.List;

import com.sparrow.dao.subscription.SubscriptionPlanDao;
import com.sparrow.domain.SubscriptionPlan;

public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
  private SubscriptionPlanDao subscriptionPlanDao;
  
  public SubscriptionPlanDao getSubscriptionPlanDao() {
    return subscriptionPlanDao;
  }

  public void setSubscriptionPlanDao(SubscriptionPlanDao subscriptionPlanDao) {
    this.subscriptionPlanDao = subscriptionPlanDao;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.subscription.SubscriptionPlanService#getActiveSubscriptionPlans()
   */
  public List getActiveSubscriptionPlans() {
    return subscriptionPlanDao.getActiveSubscriptionPlans();
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.subscription.SubscriptionPlanService#getSubscriptionPlanById(int)
   */
  public SubscriptionPlan getSubscriptionPlanById(int subscriptionPlanId) {
    return subscriptionPlanDao.getSubscriptionPlanById(subscriptionPlanId);
  }
  
  

}
