package com.sparrow.domain;

// Generated Mar 30, 2008 6:08:13 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;

/**
 * Domain object for user_search
 * @author Manish Kumar
 * @since 1.0
 */
public class UserSearch implements java.io.Serializable {

  private int searchId;

  private String searchCriteria;

  private Date searchDate;

  private Integer userId;

  public UserSearch() {
  }

  
  public UserSearch(String searchCriteria, Date searchDate, Integer userId) {
    this.searchCriteria = searchCriteria;
    this.searchDate = searchDate;
    this.userId = userId;
  }

  public int getSearchId() {
    return this.searchId;
  }

  private void setSearchId(int searchId) {
    this.searchId = searchId;
  }

  public String getSearchCriteria() {
    return this.searchCriteria;
  }

  public void setSearchCriteria(String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  public Date getSearchDate() {
    return this.searchDate;
  }

  public void setSearchDate(Date searchDate) {
    this.searchDate = searchDate;
  }

  public Integer getUserId() {
    return this.userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

}
