package com.sparrow.service;

/**
 * Constants for the Service Layer.
 * @author manishk
 * @since 1.0
 */
public class ServiceConstants {
  /**
   * Suffixes to be added to image names when they are saved.
   */
  public static final String SMALL_IMAGE_SUFFIX = "_sm.";
  public static final String LARGE_IMAGE_SUFFIX = "_lg.";
  
  /**
   * Constant to set a user account as enabled when it is first created.
   */
  public static final String USER_ACCOUNT_ENABLED = "Y";
  public static final String USER_ACCOUNT_DISABLED = "N";
  
  public static final int EXPIRED_ACCOUNTS_PAGED_LIST_SIZE = 50;
  
  public static final int BOOKS_IN_DEMAND_PAGED_LIST_SIZE = 250;
  
  public static final int USER_SEARCH_PAGED_LIST_SIZE = 50;

}
