package com.sparrow.web.user;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.service.util.GeneralUtils;
/**
 * Decorator for select subscription plan grid.
 * @author manishk
 * @since 1.0
 */
public class SelectSubscriptionPlanDecorator extends TableDecorator {
  
  public String getSelectPlan() {
    SubscriptionPlan subscriptionPlan = (SubscriptionPlan)getCurrentRowObject();
    String checked = "";
    HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
    //User user = GeneralUtils.getCurrentUserFromTLS();
    User user = (User)request.getAttribute("currentUser");
    //if user has not selected a plan, make annual plan the default selection
    //if user has already chosen a subscription plan,
    //set that as selected value in the view
    SubscriptionPlan userSubscriptionPlan = user.getSubscriptionPlan();
//    if ((userSubscriptionPlan == null && subscriptionPlan.getPeriod() == 12) 
//              || subscriptionPlan.equals(userSubscriptionPlan)) {
//      checked = "checked";
//    } 
    if (subscriptionPlan.equals(userSubscriptionPlan)) {
      checked = "checked";
    } 
    
    
    String html = "<input type=\"radio\" " + checked + " name=\"subscriptionPlanId\" value=\"" + subscriptionPlan.getPlanId() + "\">";
    
    return html;
  }
  
  public String getPlanDetails() {
    SubscriptionPlan subscriptionPlan = (SubscriptionPlan)getCurrentRowObject();
    HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
    //User user = GeneralUtils.getCurrentUserFromTLS();
    //get from request instead from GeneralUtils since GL does a query each time...getPlanDetails method is exceuted in a loop by displayTag
    User user = (User)request.getAttribute("currentUser");
    SubscriptionPlan userSubscriptionPlan = user.getSubscriptionPlan();
    String currentPlanMsg = "";
    if (subscriptionPlan.equals(userSubscriptionPlan)) {
      currentPlanMsg = "(You are currently on this plan)";
    }
    
    StringBuffer html = new StringBuffer("<span style='font-weight: bolder;font-size:13px;'>" + subscriptionPlan.getName() + " for Rs.&nbsp;" + subscriptionPlan.getFee() + "</span>&nbsp; " + currentPlanMsg + "<br>");
    html.append("Limit " + subscriptionPlan.getMaxRentalsPerMonth() + " books per month. ");
    html.append((subscriptionPlan.getDeposit() == 0 ? "No security deposit.<br>" : "Refundable deposit Rs.&nbsp;" + subscriptionPlan.getDeposit() + ".<br><br>"));
    //html.append(subscriptionPlan.getDescription());
    
    return html.toString();
  }
}
