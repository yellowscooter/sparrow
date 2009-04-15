package com.sparrow.domain;

// Generated Jul 11, 2008 9:26:58 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * These is information about {@link SubscriptionPlan}s that the {@link User} has been on.
 * It gives the start and end dates of the plan.
 * Plan start and end dates are based on Bill payment by the User.
 * This object provides plan id, start date and end dates of a plan for a user. We can use it to get the plan history of user,
 * and also the Effective plan as of a particular date.
 */
public class UserPlan implements java.io.Serializable {

  private int userPlanId;

  private Integer userId;

  private SubscriptionPlan subscriptionPlan;

  private Date planStartDate;

  private Date planEndDate;

  public UserPlan() {
  }

  
  public UserPlan(Integer userId, SubscriptionPlan subscriptionPlan, Date planStartDate, Date planEndDate) {
    this.userId = userId;
    this.subscriptionPlan = subscriptionPlan;
    this.planStartDate = planStartDate;
    this.planEndDate = planEndDate;
  }

  public int getUserPlanId() {
    return this.userPlanId;
  }

  private void setUserPlanId(int userPlanId) {
    this.userPlanId = userPlanId;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public SubscriptionPlan getSubscriptionPlan() {
    return subscriptionPlan;
  }

  public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
    this.subscriptionPlan = subscriptionPlan;
  }


  public Date getPlanStartDate() {
    return this.planStartDate;
  }

  public void setPlanStartDate(Date planStartDate) {
    this.planStartDate = planStartDate;
  }

  public Date getPlanEndDate() {
    return this.planEndDate;
  }

  public void setPlanEndDate(Date planEndDate) {
    this.planEndDate = planEndDate;
  }

}
