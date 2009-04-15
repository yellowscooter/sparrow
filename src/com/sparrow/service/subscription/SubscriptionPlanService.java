package com.sparrow.service.subscription;

import java.util.List;

import com.sparrow.domain.SubscriptionPlan;

/**
 * Interface to Subscription Plans
 * @author manishk
 * @since 1.0
 */
public interface SubscriptionPlanService {
  /**
   * Fetch a list of subscription plans
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
