package com.sparrow.domain;

import java.util.Date;

public class SearchVO {
  private int count;
  private String search;
  private String userName;
  private String firstName;
  private String lastName;
  private Date searchDate;
  
  
  
  public int getCount() {
    return count;
  }
  public void setCount(int count) {
    this.count = count;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getSearch() {
    return search;
  }
  public void setSearch(String search) {
    this.search = search;
  }
  public Date getSearchDate() {
    return searchDate;
  }
  public void setSearchDate(Date searchDate) {
    this.searchDate = searchDate;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  
  
}
