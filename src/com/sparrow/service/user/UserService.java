package com.sparrow.service.user;

import java.util.Date;
import java.util.List;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;

import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.Bill;
import com.sparrow.domain.ChangePassword;
import com.sparrow.domain.City;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.Payment;
import com.sparrow.domain.PaymentInfo;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.SearchVO;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.domain.UserPlan;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserRole;
import com.sparrow.service.bill.MultiplePendingBillsException;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.payment.PaymentMethodNotSupportedException;
import com.sparrow.service.payment.PaymentService;
import com.sparrow.service.subscription.SubscriptionPlanNotSelectedException;

public interface UserService {

  /**
   * Save new or update a user.
   * @param user
   * @return
   * @since 1.0
   */
  public abstract User saveUser(User user);

  /**
   * Save a User and give {@link UserRole#ROLE_USER} to it.
   * @param user the user instance.
   * @return
   * @since 1.0
   */
  public abstract User saveUserAsNormalUser(User user);
  
  /**
   * Update a {@link User} password.
   * @param changePassword
   * @since 1.0
   */
  public void updatePassword (ChangePassword changePassword);

  /**
   * Get user by username
   * @param username
   * @return
   * @throws UserNotFoundException if user with the given username is not found.
   * @throws MultiplePendingBillsException when its found that user has multiple pending bills (this sould not happen).
   * @since 1.0
   */
  public abstract User getUserByUserName(String username)
      throws UserNotFoundException, MultiplePendingBillsException;
  
  /**
   * Get a {@link User} by userId
   * @param userId
   * @return
   * @since 1.0
   */
  public User getUserById(int userId) throws MultiplePendingBillsException;
  
  /**
   * Returns true of {@link User} with a given username already exists in the system,
   * else returns false.
   * 
   * @param username the username to check
   * @return true of user exists, else false
   * @since 1.0
   */
  public boolean getUserAlreadyExists(String username);

  /**
   * Save the association between a user and a Product
   * @param userProduct
   * @return
   * @since 1.0
   */
  public abstract UserProduct saveUserProduct(UserProduct userProduct);


//  /**
//   * Save or update a ProductRequest
//   * @param productRequest
//   * @return
//   * @since 1.0
//   */
//  public abstract ProductRequest saveProductRequest(
//      ProductRequest productRequest);

//  /**
//   * Save the entire {@link ProductRequest} list for a {@link User}.
//   * @param user
//   * @since 1.0
//   */
//  public abstract void saveProductRequestListForUser(User user);

  /**
   * Add a new ProductRequest to the queue given a User and Product
   * if the request is not already in queue.
   * 
   * @param user the user adding the ProductRequest
   * @param product the Product requested
   * 
   * @throws ProductRequestAlreadyExistsException if the ProductRequest already exists in queue.
   * @since 1.0
   */
  public abstract void addProductRequest(User user, Product product)
      throws ProductRequestAlreadyExistsException;

  /**
   * Remove the {@link ProductRequest} from the list
   * 
   * @param user
   * @param priority the priority item to remove.
   * @since 1.0
   */
  public abstract void removeProductRequest(User user, int priority);

  /**
   * Move the ProductRequest one level up in the ProductRequest list
   * Note: The priority field of ProductRequest instances in the List will become incorrect
   * after this call.
   * @param user
   * @param index the index of ProductRequest to be moved one level up
   * @since 1.0
   */
  public abstract void moveProductRequestOneUp(User user, int index);

  /**
   * Move the ProductRequest one level down in the ProductRequest list
   * Note: The priority field of ProductRequest instances in the List will become incorrect
   * after this call.
   * @param user
   * @param index the index of ProductRequest to be moved one level down
   * @since 1.0
   */
  public abstract void moveProductRequestOneDown(User user, int index);

  /**
   * Move the ProductRequest to the head of the ProductRequest list
   * Note: The priority field of ProductRequest instances in the List will become incorrect
   * after this call.
   * @param user
   * @param index the index of ProductRequest to be moved to the head
   * @since 1.0
   */
  public abstract void moveProductRequestToHead(User user, int index);

  /**
   * Move the ProductRequest to the tail of the ProductRequest list
   * Note: The priority field of ProductRequest instances in the List will become incorrect
   * after this call.
   * @param index the index of ProductRequest to be moved to the tail
   * @since 1.0
   */
  public abstract void moveProductRequestToTail(User user, int index);
  
  
  /**
   * Fetches a {@link PagedList} of {@link User}s with role {@link UserRole#ROLE_USER} 
   * starting from the given pageNumber.
   * Number of records fetched is based on userListPageSize property.
   * If searchCriteria is not null, will only return {@link User}s whose username or lastname 
   * matches the criteria.
   * @param pageNumber
   * @param searchCriteria the string to search on 
   * @return
   * @since 1.0
   */
  public PagedList getUserList(int pageNumber, SearchCriteria searchCriteria, UserStatusEnum status);
  
    
  /**
   * Returns the page size property from the property file.
   * @return
   * @since 1.0
   */
  public int getUserListPageSize();
  
  /**
   * Fetches the user with given userId, updates its status as verified,
   * and saves it.
   * @param userId
   * @return
   * @since 1.0
   */
  //public User saveAndVerifyUser(int userId);
  
  /**
   * Returns a List of {@link City}s to which renatal service is available.
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
  
  
  /**
   * Fetches the subscription plan with the given subscriptionPlanId
   * and assigns it to the user. If a new user has just selected a subscription plan for the first time,
   * this method also creates a {@link Bill} for the user. Incase the new subscriptionPlanId is same as the planId 
   * of the current user plan, then this method simply reutrns without taking any action.
   * 
   * @param user {@link User} to whome the paln is to be assigned
   * @param subscriptionPlanId the plan id
   * @since 1.0
   */
  public void saveUserSubscriptionPlan(User user, int subscriptionPlanId);
  
  
  /**
   * Handle payment by a user. This method will do the following
   * 1) Update the payment method of user
   * 2) Call {@link PaymentService} to process the payment
   * 3) Update the user membership status based on the payment processing status
   *  
   * @param user
   * @param paymentInfo
   * @throws PaymentMethodNotSupportedException if payment type is not supported
   * @throws SubscriptionPlanNotSelectedException
   * @since 1.0
   */
  public void processUserSubscriptionPlanManualPayment(User user, PaymentInfo paymentInfo) 
                                throws PaymentMethodNotSupportedException, SubscriptionPlanNotSelectedException;
  
  /**
   * Search for {@link User}s by lastname
   * @param pageNumber
   * @param searchCriteria
   * @return
   * @since 1.0
   */
  public PagedList searchByLastName(int pageNumber, String searchCriteria, UserStatusEnum status);
  
  public PagedList searchByUserId(int pageNumber, String searchCriteria, UserStatusEnum status);
  
  /**
   * 
   * @param pageNumber
   * @param searchCriteria
   * @param status
   * @return
   * @throws IllegalArgumentException if search criteria passed is non numeric
   * @since 1.0
   */
  public PagedList searchByUsername(int pageNumber, String searchCriteria, UserStatusEnum status) throws IllegalArgumentException;
  
  public PagedList searchByPhoneNumber(int pageNumber, String searchCriteria, UserStatusEnum status);
  
  public PagedList searchByMobilePhoneNumber(int pageNumber, String searchCriteria, UserStatusEnum status);

  /**
   * Fetch a list of payments a user has made.
   * 
   * @param user
   * @return
   * @since 1.0
   */
  public List getUserPaymentsList(User user);
  
  public List getUserPaymentsList(int userId);
  
  /**
   * Fetches the membershipExpirationThreshold days property from SystemProperties and adds 
   * that many days to the current date. 
   * If for any {@link User#getExpirationDate()} <= the above value, then user membership is about
   * to expire and a warning messages should be displayed. This comparasion will be done in the view.
   *  
   * @return the Date against which comparasions are made to check if membership is about to expire. 
   * @since 1.0
   */
  public Date getMembershipExpirationWarningCompareDate();
  
  /**
   * Getter method to fetch the number of days value from property file
   * @return
   * @since 1.0
   */
  public int getMembershipExpirationThreshold();

  /**
   * Add Payment for a User. This method first checks if a Pending bill exists for user, and adds payment
   * against that {@link Bill}.
   * 
   * @param payment
   * @param userId
   * @since 1.0
   */
  public void addUserPayment(Payment payment, int userId);
  
  public void addUserPayment(Payment payment, User user);
  
  /**
   * Update the login date of the user
   * @param user
   * @since 1.0
   */
  public void updateLoginDate(User user);
  
  /**
   * Save the search criteria the user has searched on.
   * 
   * @param searchCriteria the criteria that the user entered
   * @since 1.0
   */
  public void saveUserSearch(SearchCriteria searchCriteria);
  
  /**
   * Save or update a {@link UserPlan}
   * @param userPlan
   * @return TODO
   * @since 1.0
   */
  public UserPlan saveUserPlan(UserPlan userPlan);
  
  /**
   * Effective Subscription plan is one which is to be followed as of a particular date. e.g. after selecting plan 1 and paying the bill,
   * User may decide to change the {@link SubscriptionPlan} to 2. This plan 2 will come into effect as of a future date. 
   * So the plan effective as of today is still plan 1
   * 
   * This method returns the {@link SubscriptionPlan} for a {@link User} that is effective as of today.
   * @param user
   * @return
   * @since 1.0
   */
  public SubscriptionPlan getUserEffectivePlan(User user);
  
  /**
   * Mark a user as inactive
   * @param user
   * @since 1.0
   */
  public void setUserAsInactive(User user);
  
  /**
   * Mark a user as inactive
   * @param user
   * @since 1.0
   */
  public void setUserAsInactive(int userId);
  
  public void setUserAsTerminated(int userId);
  
  public void setUserAsSuspended(int userId);
  
  public void setUserAsActive(int userId);
  
  /**
   * Loops over all products in {@link ProductRequest} list and populates the {@link ProductInstance}s for each {@link Product}.
   * Delegates to {@link DeliveryRequestService#getProductInstances(List)}
   * @param productRequests
   * @since 1.0
   */
  public void getProductInstances(List productRequests);
  
  /**
   * Fetch all users whose account expiration date has passed.
   * 
   * @param pageNumber
   * @return
   * @since 1.0
   */
  public PagedList getUsersWithExpiredAccounts(int pageNumber);
  
  /**
   * Fetch all users whose account expiration date has passed.
   * 
   * @return
   * @since 1.0
   */
  public List getUsersWithExpiredAccounts();
  
  
  /**
   * Fetches a paged list of searches done by users sorted by date descending.
   * @param pageNumber
   * @return
   * @since 1.0
   */
  public PagedList userSearch(int pageNumber);
  
  /**
   * Returns a List of {@link SearchVO} populated number of times a string has been searced and search string
   * sorted in descending order.
   * @param pageNumber
   * @return
   * @since 1.0
   */
  public PagedList userSearchCount(int pageNumber);
  
}