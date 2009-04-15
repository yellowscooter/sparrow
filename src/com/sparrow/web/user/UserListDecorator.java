package com.sparrow.web.user;

import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.Author;
import com.sparrow.domain.Product;
import com.sparrow.domain.User;

public class UserListDecorator extends TableDecorator {

  @Override
  public String addRowClass() {
    String cssClass = null;
    Date membershipExpirationWarningCompareDate = (Date)this.getPageContext().getRequest().getAttribute("membershipExpirationWarningCompareDate");
    User user = (User) getCurrentRowObject();
    if (user.isNewRegistration()) {
      cssClass = "warning";
    } else if (!user.isNewRegistration() && user.getExpirationDate().before(membershipExpirationWarningCompareDate)) { //an active registration that is about to expire
      cssClass = "expiredAccount";
    }
    return cssClass;
  }
  
  public String getAddress() {
    StringBuffer message = new StringBuffer();
    User user = (User)getCurrentRowObject();
    message.append(user.getShippingAddress().getStreet1());
    message.append("<br>");
    message.append(user.getShippingAddress().getStreet2());
    message.append("<br>");
    message.append(user.getShippingAddress().getCity());
    message.append("<br>");
    message.append(user.getShippingAddress().getState());
    message.append("<br>");
    message.append(user.getShippingAddress().getPostalCode());
    
    return message.toString();
  }
  
  public String getDetailsLink() {
    User user = (User)getCurrentRowObject();
    StringBuffer display = new StringBuffer(); 
    display.append("<input type='button' value='Details' onClick=\"window.open('userdetails.htm?userId=" + user.getUserId() + "', 'userDetails','width=950,height=675,toolbar=no,menubar=no,scrollbars=yes')\">");  
    
    return display.toString();
  }

  
//  public String getLink1() {
//    User user = (User) getCurrentRowObject();
//    StringBuffer display = new StringBuffer();
//    display.append("<a href=\"userlist.htm?action=verify&userId=" + user.getUserId() + "\">Verify User</a>");
//
//    return display.toString();
//  }
}
