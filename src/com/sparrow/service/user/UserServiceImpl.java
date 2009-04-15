package com.sparrow.service.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasypt.digest.StringDigester;

import com.sparrow.dao.NoDataFoundException;
import com.sparrow.dao.user.UserDao;
import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.dao.util.UtilDao;
import com.sparrow.domain.Bill;
import com.sparrow.domain.ChangePassword;
import com.sparrow.domain.City;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.Payment;
import com.sparrow.domain.PaymentInfo;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.UserPlan;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserRole;
import com.sparrow.domain.UserRoleId;
import com.sparrow.domain.User;
import com.sparrow.domain.UserSearch;
import com.sparrow.service.ServiceConstants;
import com.sparrow.service.bill.BillService;
import com.sparrow.service.bill.BillStatusEnum;
import com.sparrow.service.bill.MultiplePendingBillsException;
import com.sparrow.service.bill.NoPendingBillFoundException;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.payment.PaymentMethodNotSupportedException;
import com.sparrow.service.payment.PaymentService;
import com.sparrow.service.payment.PaymentStatusEnum;
import com.sparrow.service.subscription.SubscriptionPlanNotSelectedException;
import com.sparrow.service.subscription.SubscriptionPlanService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.service.util.MailUtil;

/**
 * User service implementation class.
 * @author manishk
 * @since 1.0
 */
public class UserServiceImpl implements UserDetailsService, UserService {
  private static transient Log logger = LogFactory.getLog(UserServiceImpl.class);
  private UserDao userDao;
  private DeliveryRequestService deliveryRequestService;
  private PasswordValidationUtil passwordValidationUtil;
  private SubscriptionPlanService subscriptionPlanService;
  private StringDigester stringDigester;
  private MailUtil mailUtil;
  private PaymentService paymentService;
  private int userListPageSize;
  private int membershipExpirationThreshold;
  private BillService billService;
  private UtilDao utilDao;
  
  public UserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }
  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }

  public PasswordValidationUtil getPasswordValidationUtil() {
    return passwordValidationUtil;
  }

  public void setPasswordValidationUtil(PasswordValidationUtil passwordValidationUtil) {
    this.passwordValidationUtil = passwordValidationUtil;
  }
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }
  
  public StringDigester getStringDigester() {
    return stringDigester;
  }

  public void setStringDigester(StringDigester stringDigester) {
    this.stringDigester = stringDigester;
  }

  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }
  
  public PaymentService getPaymentService() {
    return paymentService;
  }

  public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
  
  public int getUserListPageSize() {
    return userListPageSize;
  }

  public void setUserListPageSize(int userListPageSize) {
    this.userListPageSize = userListPageSize;
  }
  
  public int getMembershipExpirationThreshold() {
    return membershipExpirationThreshold;
  }

  public void setMembershipExpirationThreshold(int membershipExpirationThreshold) {
    this.membershipExpirationThreshold = membershipExpirationThreshold;
  }
  
  public BillService getBillService() {
    return billService;
  }

  public void setBillService(BillService billService) {
    this.billService = billService;
  }
  
  public UtilDao getUtilDao() {
    return utilDao;
  }

  public void setUtilDao(UtilDao utilDao) {
    this.utilDao = utilDao;
  }

  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUser(com.sparrow.domain.User)
   */
  public User saveUser(User user) {
    //if its a new user, enable the account by default
    if (user.isNew()) {
      //create new account enabled by default
      user.setAccountEnabled(ServiceConstants.USER_ACCOUNT_ENABLED);
      user.setStatus(UserStatusEnum.ACTIVE.getValue());
      
      Date today = new Date();
      //set creation date as current date
      user.setAccountCreateDate(today);
      if (logger.isDebugEnabled()) {
        logger.debug("AccountCreateDate for " + user.getUsername() + " is: " + today.getTime());
      }
      //set the membership start date as current date
      user.setStartDate(today);
      //setting the expiration date as today...this simply means payment has not been processed for this user.
      user.setExpirationDate(today);
      
      //if shipping address is empty, set it to null so it is not saved to he database
      //an empty inner bean is created with user is created so spring tags can do proper bindind.
      //spring tags throw an exception if inner bean is null
      if (user.getShippingAddress().isEmpty()) {
        user.setShippingAddress(null);
      }
      if (user.getBillingAddress().isEmpty()) {
        user.setBillingAddress(null);
      }
      
      //encrypt the password
      user.setPassword(digestPassword(user.getPassword()));
      
    }
    if (logger.isInfoEnabled()) {
      logger.info("User Created:" + user.toString());  
    }
    return userDao.saveUser(user);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUserAsNormalUser(com.sparrow.domain.User)
   */
  public User saveUserAsNormalUser(User user) {
    User savedUser = this.saveUser(user);
    //add user to normal user role
    //savedUser.addRoleToUser(UserRole.ROLE_USER);
    //user.addRoleToUser(UserRole.ROLE_USER);
    //User savedUser = this.saveUser(user);
    //userDao.saveUser(savedUser);
    UserRole authority = new UserRole(new UserRoleId(savedUser.getUserId(), UserRole.ROLE_USER));
    userDao.saveRole(authority);
    
    //since the user has just registered, create an automatic delivery request for the user
    DeliveryRequest newDeliveryRequest = new DeliveryRequest(savedUser, new Date());
    deliveryRequestService.saveDeliveryRequest(newDeliveryRequest);
    
        
    //send out an email to the user and admin
    mailUtil.sendUserRegistrationConfirmationEmail(savedUser);
    
    if (logger.isDebugEnabled()) {
      logger.debug("User created and confirmaion email sent. Username: " + user.getUsername());
    }
    return savedUser;
    
  }
  
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#updatePassword(com.sparrow.domain.ChangePassword)
   */
  public void updatePassword(ChangePassword changePassword) {
    User user = GeneralUtils.getCurrentUserFromTLS();
    //double check if the user specified the correct old password
    //if (!passwordValidationUtil.newPasswordMatchesOldPassword(changePassword.getOldPassword())) {
    if (!passwordValidationUtil.newPasswordMatchesOldPassword(changePassword.getOldPassword())) {
      logger.warn("The old password does not match the current password of user:" + user.getUsername());
      throw new SecurityException("The old password does not match the current password of user:" + user.getUsername());
    }
    //  encrypt and set the password
    user.setPassword(digestPassword(changePassword.getNewPassword()));
    
    saveUser(user);
  }

  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getUserByUserName(java.lang.String)
   */
  public User getUserByUserName(String username) throws UserNotFoundException, MultiplePendingBillsException {
    User user;
    try {
      user = userDao.getUserByUserName(username);  
    } catch (NoDataFoundException e) {
      String err = "User not found with username: " + username;
      logger.warn(err);
      throw new UserNotFoundException(err, e);
    }
    
    //initialize the pending bill
    //doing this here so if there is a MultiplePendingBill exception, it is caught here
    user.setPendingBill(user.getPendingBill());
    
    
    return user;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getUserById(int)
   */
  public User getUserById(int userId) throws MultiplePendingBillsException {
    User user = userDao.getUserById(userId);
    
    //initialize the pending bill
    //doing this here so if there is a MultiplePendingBill exception, it is caught here
    user.setPendingBill(user.getPendingBill());
    
    return user;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getUserAlreadyExists(java.lang.String)
   */
  public boolean getUserAlreadyExists(String username) {
    boolean userAlreadyExists = false;
    try {
      //this call will throw exception of user does not exist
      this.getUserByUserName(username);
      userAlreadyExists = true;
    } catch (UserNotFoundException e) {
      //do nothing since userAlreadyExists is already false
    }
    
    return userAlreadyExists;
  }

  /**
   * @see {@link UserDetailsService#loadUserByUsername(String)}
   */
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails user;
    try {
      user = this.getUserByUserName(username);  
    } catch (UserNotFoundException ue) {
      throw new UsernameNotFoundException("User with given username not found:" + username);
    }
    
    return user;
  }
  
  
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUserProduct(com.sparrow.domain.UserProduct)
   */
  public UserProduct saveUserProduct(UserProduct userProduct) {
    return userDao.saveUserProduct(userProduct);
  }
  
  
//  /* (non-Javadoc)
//   * @see com.sparrow.service.user.UserService#saveProductRequest(com.sparrow.domain.ProductRequest)
//   */
//  public ProductRequest saveProductRequest(ProductRequest productRequest) {
//    return userDao.saveProductRequest(productRequest);
//  }
  
//  /* (non-Javadoc)
//   * @see com.sparrow.service.user.UserService#saveProductRequestListForUser(com.sparrow.domain.User)
//   */
//  public void saveProductRequestListForUser(User user) {
//    userDao.saveProductRequestListForUser(user);
//  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#addProductRequest(com.sparrow.domain.User, com.sparrow.domain.Product)
   */
  public void addProductRequest(User user, Product product) throws ProductRequestAlreadyExistsException {
    //ProductRequest productRequest = new ProductRequest(user, product);
    ProductRequest productRequest = new ProductRequest(product, new Date());
    user.addProductRequest(productRequest);
    this.saveUser(user);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#removeProductRequest(com.sparrow.domain.User, int)
   */
  public void removeProductRequest(User user, int priority) {
    user.removeProductRequest(priority);
    this.saveUser(user);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#moveProductRequestOneUp(com.sparrow.domain.User, int)
   */
  public void moveProductRequestOneUp(User user, int index) {
    //only if there has been an update to the list do we need to save
    if (user.moveProductRequestOneUp(index)) {
      this.saveUser(user);
    }
    
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#moveProductRequestOneDown(com.sparrow.domain.User, int)
   */
  public void moveProductRequestOneDown(User user, int index) {
    if (user.moveProductRequestOneDown(index)) {
      this.saveUser(user);
    }
    
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#moveProductRequestToHead(com.sparrow.domain.User, int)
   */
  public void moveProductRequestToHead(User user, int index) {
    if (user.moveProductRequestToHead(index)) {
      this.saveUser(user);
    }
    
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.user.UserService#moveProductRequestToTail(com.sparrow.domain.User, int)
   */
  public void moveProductRequestToTail(User user, int index) {
    if (user.moveProductRequestToTail(index)) {
      this.saveUser(user);
    }
    
  }
  

  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getUserList(int)
   */
  public PagedList getUserList(int pageNumber, SearchCriteria searchCriteria, UserStatusEnum status) {
    int objectsPerPage = this.getUserListPageSize();
    //if null, create a new empty criteria
    if (searchCriteria == null) {
      searchCriteria = new SearchCriteria();
    }
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)*objectsPerPage;
    PagedList userList = userDao.search(start, objectsPerPage, searchCriteria, status.getValue());
    userList.setObjectsPerPage(objectsPerPage);
    userList.setPageNumber(pageNumber);
    
    return userList;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveAndVerifyUser(int)
   */
//  public User saveAndVerifyUser(int userId) {
//    //not sure why this method was added, and why its not being used in the system
//    User currentUser = GeneralUtils.getCurrentUserFromTLS();
//    User user = this.getUserById(userId);
//    //user.setStatus(UserStatusEnum.VERIFIED.getValue());
//    user.setStatusUpdatedBy(currentUser.getUserId());
//    user.setStatusUpdateDate(new Date());
//    //update user
//    user = userDao.saveUser(user);
//    //user is verified...not create a new delivery request for the user
//    //this is the first set of books to be delivered.
//    DeliveryRequest deliveryRequest = new DeliveryRequest(user.getUserId(), new Date());
//    deliveryRequestService.saveDeliveryRequest(deliveryRequest); 
//    
//    return user;
//  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getCityList()
   */
  public List getCityList() {
    return userDao.getCityList();
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getCityById(int)
   */
  public City getCityById(int cityId) {
    return userDao.getCityById(cityId);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUserSubscriptionPlan(com.sparrow.domain.User, int)
   */
  public void saveUserSubscriptionPlan(User user, int subscriptionPlanId) {
    boolean isFirstTimePlanSelection = false;
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null");
    }
    SubscriptionPlan currentPlan = user.getSubscriptionPlan();
    //if current plan is same as the new plan user has selected, simply return without taking any action.
    if (currentPlan != null && currentPlan.getPlanId() == subscriptionPlanId) {
      return;
    }
    //if user's subscription plan is null, it means user is a new user who has not yet selected a plan
    //after that, plan will never be null since user can only update a plan, never delete it
    if (currentPlan == null) {
      isFirstTimePlanSelection = true;
    }
    
    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionPlanById(subscriptionPlanId);
    if (subscriptionPlan == null) {
      String msg = "Subscription plan does not exist. Plan id = " + subscriptionPlanId; 
      logger.debug(msg);
      throw new IllegalStateException (msg);
    }
    user.setSubscriptionPlan(subscriptionPlan);
    this.saveUser(user);
    
    if (isFirstTimePlanSelection) {
      billService.createUserBill(user);
    } else {    //user has updated the subscription plan
      //handle plan updates
      
      //if user has pending bill against the old plan, cancel the bill and create a new bill for the new subscription plan
      if (user.getPendingBill() != null) {
        billService.cancelPendingBill(user);
        billService.createUserBill(user);  
      }
    }
  }
  
  

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#processUserSubscriptionPlanPayment(com.sparrow.domain.User, com.sparrow.domain.PaymentInfo)
   */
  public void processUserSubscriptionPlanManualPayment(User user, PaymentInfo paymentInfo) 
              throws PaymentMethodNotSupportedException, SubscriptionPlanNotSelectedException {
    //set the chosen payment method of the user
    user.setPaymentMethod(paymentInfo.getPaymentMethod());
    
    //send out an email to user
    mailUtil.sendManualPaymentEmail(user, paymentInfo);
    
    this.saveUser(user);    
  }

  public PagedList searchByLastName(int pageNumber, String searchCriteria, UserStatusEnum status) {
    SearchCriteria searchCriteria2 = new SearchCriteria("lastname", searchCriteria);
    return this.getUserList(pageNumber, searchCriteria2, status);
  }
  
  

  public PagedList searchByUserId(int pageNumber, String searchCriteria, UserStatusEnum status) throws IllegalArgumentException {
    if (!StringUtils.isNumeric(searchCriteria)) {
      throw new IllegalArgumentException("search criteria for user id search should be numeric");
    }
    try {
      Integer.parseInt(searchCriteria);  
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("search criteria for user id search should be numeric");
    }
    
    //userId is the properto of User object
    SearchCriteria searchCriteria2 = new SearchCriteria("userId", searchCriteria);
    return this.getUserList(pageNumber, searchCriteria2, status);
  }
  
  public PagedList searchByUsername(int pageNumber, String searchCriteria, UserStatusEnum status) {
    //username is the properto of User object
    SearchCriteria searchCriteria2 = new SearchCriteria("username", searchCriteria);
    return this.getUserList(pageNumber, searchCriteria2, status);
  }
  
  
  public PagedList searchByPhoneNumber(int pageNumber, String searchCriteria, UserStatusEnum status) {
    //  userId is the property of User object
    SearchCriteria searchCriteria2 = new SearchCriteria("phone", searchCriteria);
    return this.getUserList(pageNumber, searchCriteria2, status);
  }
  
  

  public PagedList searchByMobilePhoneNumber(int pageNumber, String searchCriteria, UserStatusEnum status) {
    //  userId is the property of User object
    SearchCriteria searchCriteria2 = new SearchCriteria("mobilePhone", searchCriteria);
    return this.getUserList(pageNumber, searchCriteria2, status);
  }

  public List getUserPaymentsList(User user) {
    Set paymentsSet = user.getPayments();
    List paymentsList = new ArrayList(paymentsSet);
    
    return paymentsList;
  }
  
  public List getUserPaymentsList(int userId) {
    User user = this.getUserById(userId);
    return this.getUserPaymentsList(user);
  }

  private String digestPassword(String password) {
    String digest = stringDigester.digest(password);
    if (logger.isDebugEnabled()) {
      logger.debug("Password digested for user. Digest is: " + digest);
    }
    return digest;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getMembershipExpirationWarningCompareDate()
   */
  public Date getMembershipExpirationWarningCompareDate() {
    Date todaysDate = new Date();
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(todaysDate);
    calendar.add(Calendar.DAY_OF_MONTH, membershipExpirationThreshold);
    
    return calendar.getTime();
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.payment.PaymentService#addUserPayment(com.sparrow.domain.Payment, int)
   */
  public void addUserPayment(Payment payment, int userId) {
    User user = this.getUserById(userId);
    addUserPayment(payment, user);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.payment.PaymentService#addUserPayment(com.sparrow.domain.Payment, int)
   */
  public void addUserPayment(Payment payment, User user) {
    Bill pendingBill = user.getPendingBill();
    //you cannot pay if user has no pending bill...
    if (pendingBill == null) {
      throw new NoPendingBillFoundException("No Pending Bill found for user: userid, username" + user.getUserId() + ", " + user.getUsername());
    }
    payment.setBill(pendingBill);
    //pendingBill.setPayment(payment);
    //remove the existing pending payment from the Payments set which is loaded when you load user 
    //remove will simply do a check on equals and remove the payment object from the set
    pendingBill.getPayments().remove(payment);
    //add the updated payment to the payments set 
    pendingBill.getPayments().add(payment);
    //if you do not do the above, we get a NonUniqueObjectException exception, since same payment object is
    //loaded in the pendingBill.payments set, and is also passed in as argument to this method.
    
    //update the user's preferred payment method to the last payment method
    user.setPaymentMethod(payment.getPaymentMethod().getValue());
    
    
    if (PaymentStatusEnum.SUCCESS.getValue().equals(payment.getStatus())) {
      //update the status of the bill as Paid
      pendingBill.setStatus(BillStatusEnum.PAID.getValue());
      
      //payment verified...update the expiration date
      updateUserMembershipExpirationDate(user);
      //send success email
    } else if (PaymentStatusEnum.FAIL.getValue().equals(payment.getStatus())) {
      //no need to update the Bill status
      //send failure email
    }
    this.saveUser(user);
  }
  
  
  /**
   * This method should be called after payment has been processed. It will extend the 
   * membership expiration date based on the User's membership plan. This method also creates
   * a {@link UserPlan} and saves it. This object provides plan id, start date and end dates of a plan for a user.
   * 
   * @param user
   * @since 1.0
   */
  private void updateUserMembershipExpirationDate(User user) {
    //  payment has been processed for the subscription plan
    //  update the user membership expiration date by adding the number of months
    //  in the subscription plan that the user paid for
    SubscriptionPlan subscriptionPlan = user.getSubscriptionPlan();
    int periodInMonths = subscriptionPlan.getPeriod();
    //  //expiration date should never be null since we set the expiration date as start date when a new user accoutn is created
    Date initialExpirationDate = user.getExpirationDate();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(initialExpirationDate);
    //  
    //  //add period months
    calendar.add(Calendar.MONTH, periodInMonths);
    //subtract 1 day...e.g. if start is 1st Oct, then expiration should be 31st Oct, not 1st Nov
    //which is what you will get by adding a month
    //only do this the first time since we do not want to subtract a day each time a the expiration date is updated
    if (user.isNewRegistration()) {
      calendar.add(Calendar.DAY_OF_MONTH, -1);  
    }    
    user.setExpirationDate(calendar.getTime());
    
    //plan start date is from next day to the expiration of current plan...so just add 1 day to the expiration date.
    Date planStartDate = DateUtils.addDays(initialExpirationDate, 1);
    //now also create a UserPlan so we know which plans a user is on, and for which dates
    UserPlan userPlan = new UserPlan(user.getUserId(), subscriptionPlan, planStartDate, user.getExpirationDate());
    this.saveUserPlan(userPlan);
    
    
  }

  /**
   * This method updates the login date using jdbc, since we want to avoid stale object exception when the update happens.
   * Anyway, the last login date is only shown when the user logs in the next time...so it does not matter if the User object is out of sync.
   */
  public void updateLoginDate(User user) {
    if (logger.isDebugEnabled()) {
      logger.debug("Updating last login date for user " + user.getUsername());
    }
    utilDao.updateLoginDate(user, new Date());
    
    //send out an alert email to admin if the user is an inactive user
    if (UserStatusEnum.INACTIVE.getValue().equals(user.getStatus()) || UserStatusEnum.SUSPENDED.getValue().equals(user.getStatus())) {
      mailUtil.sendInactiveUserLoginEmail(user);
      this.setUserAsActive(user.getUserId());
    }
  
    if (UserStatusEnum.SUSPENDED.getValue().equals(user.getStatus())) {
      mailUtil.sendSuspendedUserLoginEmail(user);
      this.setUserAsActive(user.getUserId());
    }
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUserSearch(com.sparrow.domain.SearchCriteria)
   */
  public void saveUserSearch(SearchCriteria searchCriteria) {
    Integer userId = null;
    User user = GeneralUtils.getCurrentUserFromTLS();
    if (user != null) {
      userId = user.getUserId();
    }
    UserSearch userSearch = new UserSearch(searchCriteria.getCriteria(), new Date(), userId);
    userDao.saveUserSearch(userSearch);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#saveUserPlan(com.sparrow.domain.UserPlan)
   */
  public UserPlan saveUserPlan(UserPlan userPlan) {
    return userDao.saveUserPlan(userPlan);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.user.UserService#getUserEffectivePlan(com.sparrow.domain.User)
   */
  public SubscriptionPlan getUserEffectivePlan(User user) {
    return userDao.getUserEffectivePlan(user);
  }

  public void setUserAsInactive(User user) {
    this.updateUserStatus(user, UserStatusEnum.INACTIVE);    
  }
  
  private void setUserAsActive(User user) {
    this.updateUserStatus(user, UserStatusEnum.ACTIVE);
  }
  
  
  /**
   * Since this method will first fetch user, it will attach the object to hibernate session
   * This will lead to automatic saving of the user object.
   */
  public void setUserAsActive(int userId) {
    User user = this.getUserById(userId);
    this.setUserAsActive(user);
    
  }

  public void setUserAsInactive(int userId) {
    User user = this.getUserById(userId);
    this.setUserAsInactive(user);
    
  }
  
  public void setUserAsTerminated(int userId) {
    User user = this.getUserById(userId);
    this.updateUserStatus(user, UserStatusEnum.TERMINATED);  
  }
  
  

  public void setUserAsSuspended(int userId) {
    User user = this.getUserById(userId);
    this.updateUserStatus(user, UserStatusEnum.SUSPENDED);  
  }

  public void getProductInstances(List productRequests) {
    deliveryRequestService.getProductInstances(productRequests);
    
  }

  public PagedList getUsersWithExpiredAccounts(int pageNumber) {
    int objectsPerPage = ServiceConstants.EXPIRED_ACCOUNTS_PAGED_LIST_SIZE;
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)* objectsPerPage;
    PagedList userList = userDao.getUsersWithExpiredAccounts(start, objectsPerPage);
    userList.setObjectsPerPage(objectsPerPage);
    userList.setPageNumber(pageNumber);
    
    return userList;
  }

  public PagedList userSearch(int pageNumber) {
    int objectsPerPage = ServiceConstants.USER_SEARCH_PAGED_LIST_SIZE;
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)* objectsPerPage;
    PagedList userSearchList = utilDao.userSearch(start, objectsPerPage);
    userSearchList.setObjectsPerPage(objectsPerPage);
    userSearchList.setPageNumber(pageNumber);
    
    return userSearchList;
  }
  
  public PagedList userSearchCount(int pageNumber) {
    int objectsPerPage = ServiceConstants.USER_SEARCH_PAGED_LIST_SIZE;
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)* objectsPerPage;
    PagedList userSearchList = utilDao.userSearchCount(start, objectsPerPage);
    userSearchList.setObjectsPerPage(objectsPerPage);
    userSearchList.setPageNumber(pageNumber);
    
    return userSearchList;
  }
  
  private void updateUserStatus(User user, UserStatusEnum status) {
    //also update the statusUpdateDate and statusUpdatedBy
    user.setStatus(status.getValue());
    User adminUser = GeneralUtils.getCurrentUserFromTLS();
    //if adminUser is null, user current user
    if (adminUser == null) {
      adminUser = user;
    }
    user.setStatusUpdateDate(new Date());
    user.setStatusUpdatedBy(adminUser.getUserId());
  }

  public List getUsersWithExpiredAccounts() {
    return userDao.getUsersWithExpiredAccounts();
  }
  
  
  
  

}
