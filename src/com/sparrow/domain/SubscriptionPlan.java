package com.sparrow.domain;

/**
 * Domain object for Subscription Plan. 
 * @author manishk
 * @since 1.0
 */
public class SubscriptionPlan implements java.io.Serializable {

  private int planId;

  private String name;

  private String description;

  private int maxRentalsPerMonth;

  private int fee;

  private int period;

  private String status;
  
  private int deposit;
  
  private int maxProductsWithUser;

  public SubscriptionPlan() {
  }

  public SubscriptionPlan(int planId, String name, String description, int maxRentalsPerMonth, int fee, int period,
      String status) {
    this.planId = planId;
    this.name = name;
    this.description = description;
    this.maxRentalsPerMonth = maxRentalsPerMonth;
    this.fee = fee;
    this.period = period;
    this.status = status;
  }

  public int getPlanId() {
    return this.planId;
  }

  private void setPlanId(int planId) {
    this.planId = planId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getMaxRentalsPerMonth() {
    return this.maxRentalsPerMonth;
  }

  public void setMaxRentalsPerMonth(int maxRentalsPerMonth) {
    this.maxRentalsPerMonth = maxRentalsPerMonth;
  }

  public int getFee() {
    return this.fee;
  }

  public void setFee(int fee) {
    this.fee = fee;
  }

  public int getPeriod() {
    return this.period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
  public int getDeposit() {
    return deposit;
  }

  public void setDeposit(int deposit) {
    this.deposit = deposit;
  }
  
  

  public int getMaxProductsWithUser() {
    return maxProductsWithUser;
  }

  public void setMaxProductsWithUser(int maxProductsWithUser) {
    this.maxProductsWithUser = maxProductsWithUser;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((name == null) ? 0 : name.hashCode());
    result = PRIME * result + planId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final SubscriptionPlan other = (SubscriptionPlan) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (planId != other.planId)
      return false;
    return true;
  }
  
  

}
