package com.sparrow.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Search Criteria.
 * @author manishk
 * @since 1.0
 */
public class SearchCriteria {
  /*
   * The search criteria
   */
  private String criteria;
  
  /**
   * Search key of the entity that we are searching
   */
  private String searchKey;

  public SearchCriteria() {
  }
  
  public SearchCriteria(String criteria) {
    this.criteria = StringUtils.trim(criteria);
  }
  
  public SearchCriteria(String searchKey, String criteria) {
    this.criteria = StringUtils.trim(criteria);
    this.searchKey = searchKey;
  }
  
  public String getCriteria() {
    return criteria;
  }

  
  public void setCriteria(String criteria) {
    this.criteria = StringUtils.trim(criteria);
  }

  public String getSearchKey() {
    return searchKey;
  }

  /**
   * Search key of the entity that we are searching.
   * e.g, searching for {@link User}, which has username as one of its fields, 
   * search key can be username if we want to restrict on username.
   * @param criteria
   * @since 1.0
   */
  public void setSearchKey(String searchKey) {
    this.searchKey = searchKey;
  }
  
  /**
   * Returns a criteria decorated with wildcard characters.
   * e.g. 'test' will be converted to '%test%'
   * @return
   * @since 1.0
   */
  public String getDecoratedCriteria() {
    String decoratedCriteria = this.getCriteria();
    //userId is a numeric key...if you add wildcard char to a numeric field,
    //an exception is thrown
    if ((this.getCriteria() != null) && this.searchKey != "userId") {
      decoratedCriteria = "%" + this.getCriteria() + "%";
    }
    
    return decoratedCriteria;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("searchKey", this.searchKey).append("criteria", this.criteria).toString();
  }

  
  
  
  

}
