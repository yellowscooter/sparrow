package com.sparrow.web.common;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.SubscriptionPlan;

public class FaqDecorator extends TableDecorator {
  
  public String getPeriod() {
    SubscriptionPlan subscriptionPlan = (SubscriptionPlan)getCurrentRowObject();
    
    return subscriptionPlan.getPeriod() + " month";
  }

}
