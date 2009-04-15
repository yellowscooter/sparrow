package com.sparrow.dao.user;

import java.util.List;

import com.sparrow.dao.NoDataFoundException;
import com.sparrow.domain.City;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.UserPlan;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserRole;
import com.sparrow.domain.User;
import com.sparrow.domain.UserSearch;

/**
 * User maintenance interface.
 * @author manishk
 * @since 1.0
 */
public interface UserDao {

  /**
   * Save new or update a user.
   * @param user instance
   * @return the new or updated User
   * @since 1.0
   */
  public User saveUser(User user);
  
  /**
   * Fetch a user by its user id
   * @param userId
   * @return
   * @since 1.0
   */
  public User getUserById(int userId);
  
  /**
   * Fetches a User by username.
   * @param username
   * @return the User instance
   * @throws IllegalStateException if more than one users with the same username are found.
   * @throws NoDataFoundException if user is not found.
   * @since 1.0
   */
  public User getUserByUserName(String username) throws NoDataFoundException;
  
  /**
   * Save Authority granted to a User.
   * @param authority
   * @return
   * @since 1.0
   */
  public UserRole saveRole(UserRole authority); 
  
  /**
   * Save new or update UserProduct
   * 
   * @param userProduct
   * @return new or updated UserProduct instance.
   * @since 1.0
   */
  public UserProduct saveUserProduct(UserProduct userProduct);
  
  
  /**
   * Returns true if User has pending returns from more than 1 previous delivery requests checkouts,
   * else returns false.
   * At any time, user can only have a maximum of {@link SubscriptionPlan#getMaxProductsWithUser()} assigned to his/her name.
   * 
   * @param user
   * @param deliveryRequest TODO
   * @return
   * @since 1.0
   */
  public boolean userHasPendingReturnsFromPreviousDeliveryRequests(User user, DeliveryRequest deliveryRequest);
  
//  /**
//   * Save new or update a ProductRequest
//   * @param productRequest
//   * @return new or updated ProductRequest
//   * @since 1.0
//   */
//  public ProductRequest saveProductRequest(ProductRequest productRequest);
  
//  /**
//   * Saves the ProductRequest list for a user, maintaing the order.
//   * 
//   * @param user the user whose ProductRequest list is to be saved
//   * @since 1.0
//   */
//  public void saveProductRequestListForUser(User user);
  

  
  /**
   * Fetch a {@link PagedList} list of {@link User}s
   * If searchCriteria is not null, will only return {@link User}s whose username or lastname 
   * matches the criteria.
   * If there is no search criteria, pass an empty {@link SearchCriteria} instance
   *  
   * @param start the index from where to start
   * @param length the number of records to be returned, starting from start index
   * @param search {@link SearchCriteria} 
   * @parma status Status of user's you want to search A-Active,I-Inactive,S-Suspended,T-Terminated
   * @throws IllegalArgumentException if {@link SearchCriteria} is null
   * @return {@link PagedList} fully populated.
   * @since 1.0
   */
  public PagedList search(int start, int length, SearchCriteria searchCriteria, String status);
  
  /**
   * Returns a List of {@link City}s to which renatal service is available,
   * sorted by city name.
   * @return
   * @since 1.0
   */
  public List getCityList();
  
  /**
   * Fetch a {@link City} by its id.
   * @param cityId
   * @return
   * @since 1.0
   */
  public City getCityById(int cityId);
  
//  /**
//   * Fetch a list of payments a user has made.
//   * 
//   * @param user
//   * @return
//   * @since 1.0
//   */
//  public List getUserPaymentsList(User user);
  
  /**
   * Save {@link UserSearch}
   */
  public void saveUserSearch(UserSearch userSearch);
  
  /**
   * Save or update a {@link UserPlan}
   * @param userPlan
   * @since 1.0
   */
  public UserPlan saveUserPlan(UserPlan userPlan);
  
  /**
   * Effective Subscription plan is one which is to be followed as of a particular date. e.g. after selecting plan 1 and paying the bill,
   * User may decide to change the {@link SubscriptionPlan} to 2. This plan 2 will come into effect as of a future date. 
   * So the plan effective as of today is still plan 1
   * 
   * This method returns the {@link SubscriptionPlan} for a {@link User} that is effective as of today. If there is no effective plan,
   * the method returns null.
   * A value of NULL simply means that user has not paid the bill for the current time period.
   * @param user
   * @return
   * @since 1.0
   */
  public SubscriptionPlan getUserEffectivePlan(User user);
  
  /**
   * Fetch all users whose account expiration date has passed.
   * 
   * @param start
   * @param objectsPerPage
   * @return
   * @since 1.0
   */
  public PagedList getUsersWithExpiredAccounts(final int start, final int objectsPerPage);
  
  /**
   * Fetch all users whose account expiration date has passed...this method returns all users, without any paging.
   * @return
   * @since 1.0
   */
  public List getUsersWithExpiredAccounts();
  
  
}
