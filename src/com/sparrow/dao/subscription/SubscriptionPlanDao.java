package com.sparrow.dao.subscription;

import java.util.List;

import com.sparrow.domain.SubscriptionPlan;

/**
 * Dao interface to Subscription Plans
 * @author manishk
 * @since 1.0
 */
public interface SubscriptionPlanDao {


  /**
   * Fetch a list of subscription plans
   * @return
   * @since 1.0
   */
  public List getSubscriptionPlans();
  
  /**
   * Fetch a list of currently active subscription plans, i.e. status = ACTIVE
   * @return
   * @since 1.0
   */
  public List getActiveSubscriptionPlans();
  
  /**
   * Get a plan by its Id
   * @param subscriptionPlanId
   * @return
   * @since 1.0
   */
  public SubscriptionPlan getSubscriptionPlanById(int subscriptionPlanId);

}
