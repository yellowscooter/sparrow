package com.sparrow.service.subscription;

import java.util.List;

import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.service.AbstractServiceTests;

public class SubscriptionServiceTests extends AbstractServiceTests {
  private SubscriptionPlanService subscriptionPlanService;
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }


  public void testGetActiveSubscriptionPlans() {
    int count = jdbcTemplate.queryForInt("select count(0) from subscription_plan where status = 'ACTIVE'");
    List subPlans = subscriptionPlanService.getActiveSubscriptionPlans();
    assertEquals("Correct count of sub plans not fetched", count, subPlans.size());
  }
  
  public void testGetSubscriptionPlanById() {
    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionPlanById(1);
    assertEquals("Correct plan fetched", "One Month-Basic", subscriptionPlan.getName());
  }

}
