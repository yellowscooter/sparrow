package com.sparrow.service.test;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.TestCase;

import com.sparrow.domain.City;
import com.sparrow.domain.User;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.UserService;
import com.sparrow.web.WebConstants;

/**
 * Commenting out all tests since these do not relate to business logic
 * @author Manish Kumar
 * @since 1.0
 */
public class DateTestsDisabled extends AbstractServiceTests  {
  private UserService userService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  
//  public void testAvailableTimeZones() {
//    String[] timeZonesIds = TimeZone.getAvailableIDs();
//    System.out.println(timeZonesIds.toString());
//    System.out.println(timeZonesIds.length);
//    for (int i = 0; i < timeZonesIds.length; i++) {
//      System.out.println(timeZonesIds[i]);
//    }
//  }
  
  public void testDatesAndTimezones() throws Exception {
    City city = userService.getCityById(1);
//    User user = new User("username22", "password", "firstName", "lastName", "Y");
//    user.setCity(city);
//    user = userService.saveUserAsNormalUser(user);
//    System.out.println("date1: " + user.getAccountCreateDate().toString());
//    System.out.println("timestamp1: " + user.getAccountCreateDate().getTime());
//    
    System.out.println("timestampPST: " + new Date().getTime());
    //TimeZone.setDefault(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    System.out.println("timestampIST: " + new Date().getTime());
    User user2 = new User("username31", "password", "firstName", "lastName", "Y");
    user2.setCity(city);
    user2 = userService.saveUserAsNormalUser(user2);
    
    //setComplete();
    System.out.println("date2: " + user2.getAccountCreateDate().toString());
    System.out.println("timestamp2: " + user2.getAccountCreateDate().getTime());

  }
  
  public void testGetUserTimestamp() throws Exception {
    User user2 = userService.getUserByUserName("username31");
    System.out.println("date3: " + user2.getAccountCreateDate().toString());
    System.out.println("timestamp3: " + user2.getAccountCreateDate().getTime());
    
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
    System.out.println("formatter: " + formatter.format(user2.getAccountCreateDate()));
    formatter.format(user2.getAccountCreateDate());
    
    TimeZone.setDefault(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    User user3 = userService.getUserByUserName("username31");
    System.out.println("date3: " + user3.getAccountCreateDate().toString());
    System.out.println("timestamp3: " + user3.getAccountCreateDate().getTime());
    formatter.setTimeZone(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    System.out.println("formatter2: " + formatter.format(user3.getAccountCreateDate()));
  }
  
  

}
