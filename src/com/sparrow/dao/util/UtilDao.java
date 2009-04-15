package com.sparrow.dao.util;

import java.util.Date;

import com.sparrow.domain.PagedList;
import com.sparrow.domain.SearchVO;
import com.sparrow.domain.User;

/**
 * Interface for some utility dao methods that do not belong to any specific dao. The implementation is non hibernate
 * (jdbc) since hibernate was not appropriate for such data access.
 * @author Manish Kumar
 * @since 1.0
 */
public interface UtilDao {

  /**
   * Update the login date of the user.
   * @param date
   * @since 1.0
   */
  public void updateLoginDate(User user, Date date);
  
  /**
   * Fetches a List of books that have been added by Users to their Bookself in descending order order of most popular.
   * 
   * @param start
   * @param objectsPerPage
   * @return
   * @since 1.0
   */
  public PagedList findBooksMostInDemand(final int start, final int objectsPerPage);
  
  /**
   * Fetches a paged list of searches done by users sorted by date descending.
   * @param start
   * @param objectsPerPage
   * @return
   * @since 1.0
   */
  public PagedList userSearch(final int start, final int objectsPerPage);
  
  /**
   * Returns a List of {@link SearchVO} populated number of times a string has been searced and search string
   * sorted in descending order.
   * @param start
   * @param objectsPerPage
   * @return
   * @since 1.0
   */
  public PagedList userSearchCount(final int start, final int objectsPerPage);
}
