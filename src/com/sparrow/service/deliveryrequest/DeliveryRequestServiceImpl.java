package com.sparrow.service.deliveryrequest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sparrow.dao.deliveryrequest.DeliveryRequestDao;
import com.sparrow.dao.deliveryrequest.DeliveryRequestStatusEnum;
import com.sparrow.dao.deliveryrequest.hibernate.DeliveryRequestDaoImpl;
import com.sparrow.dao.user.UserDao;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserProductHistory;
import com.sparrow.service.catalog.ProductInstanceStatusEnum;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.common.DateUtils;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.service.util.MailUtil;

/**
 * {@link DeliveryRequestService} implementation class.
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestServiceImpl implements DeliveryRequestService {
  private static final Log logger = LogFactory.getLog(DeliveryRequestServiceImpl.class);
  private DeliveryRequestDao deliveryRequestDao;
  private ProductService productService;
  private UserDao userDao;
  private MailUtil mailUtil;
  private int maxAllowedProductsPerDeliveryRequest;  
  
  public DeliveryRequestDao getDeliveryRequestDao() {
    return deliveryRequestDao;
  }

  public void setDeliveryRequestDao(DeliveryRequestDao deliveryRequestDao) {
    this.deliveryRequestDao = deliveryRequestDao;
  }
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
  public UserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public int getMaxAllowedProductsPerDeliveryRequest() {
    return maxAllowedProductsPerDeliveryRequest;
  }

  public void setMaxAllowedProductsPerDeliveryRequest(int maxAllowedProductsPerDeliveryRequest) {
    this.maxAllowedProductsPerDeliveryRequest = maxAllowedProductsPerDeliveryRequest;
  }
  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#saveDeliveryRequest(com.sparrow.domain.DeliveryRequest)
   */
  public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest) {
    if (deliveryRequest.isNew()) {
      deliveryRequest.setStatus(DeliveryRequestStatusEnum.PENDING.getValue());
    }
    DeliveryRequest deliveryRequest2 = deliveryRequestDao.saveDeliveryRequest(deliveryRequest);
    

    return deliveryRequest2;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getPendingDeliveryRequestList()
   */
  public List getPendingDeliveryRequestList() {
    return deliveryRequestDao.getPendingDeliveryRequestList();
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getDeliveryRequestById(int)
   */
  public DeliveryRequest getDeliveryRequestById(int deliveryRequestId) {
    return deliveryRequestDao.getDeliveryRequestById(deliveryRequestId);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getInCompleteDeliveryRequestByUser(com.sparrow.domain.User)
   */
  public DeliveryRequest getInCompleteDeliveryRequestByUser(User user) throws DeliveryRequestAlreadyExistsException {
    DeliveryRequest deliveryRequest = deliveryRequestDao.getInCompleteDeliveryRequestByUser(user);
    if (deliveryRequest != null) {
      String msg = "Delivery request already exists for user with username = " + user.getUsername();
      logger.error(msg);
      throw new DeliveryRequestAlreadyExistsException(msg);
    }
    return deliveryRequest;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#checkoutProduct(int, int, int)
   */
  public DeliveryRequest checkoutProduct(int instanceId, DeliveryRequest deliveryRequest, int priority, User deliveryRequestUser) 
                                    throws ProductInstanceNotAvailableException,
                                           MaximumAllowedCheckoutProductsExceeded {
    ProductInstance productInstance = productService.getProductInstanceById(instanceId);
    Date date = new Date();
    
    //if users currently checked out products against a previous delivery request have not been checked in, 
    //do not allow checkout against this delivery request.
    //List userProductsList = userDao.getUserProductsByUser(deliveryRequestUser);
    //if (userProductsList.size() >= maxAllowedProductsPerDeliveryRequest) {
    if (userDao.userHasPendingReturnsFromPreviousDeliveryRequests(deliveryRequestUser, deliveryRequest)) {
      String msg = "User with username:" + deliveryRequestUser.getUsername() + " already has max allowed products checked out." 
                    + ". Check If users currently checked out products against a previous delivery request have been checked in. ";
      logger.error(msg);
      throw new MaximumAllowedCheckoutProductsExceeded(msg);
    }
    
    //if we have already checked out max allowed products, we should not be here.
    if (deliveryRequest.getUserProductSet().size() >= maxAllowedProductsPerDeliveryRequest) {
      String msg = "Delivery Request with id = " + deliveryRequest + " has " + deliveryRequest.getUserProductSet().size() + " products. This is " +
          "more than the maximum allowable of " + maxAllowedProductsPerDeliveryRequest;
      logger.error(msg);
      throw new IllegalStateException(msg);
    }

    //if delivery request is complete, update its status
    //read property for max books user can get...
    //check how many books have been checked out against a dr...if max, update status.
    //User deliveryRequestUser = userDao.getUserById(deliveryRequest.getUserId());
    
    UserProduct userProduct = new UserProduct();
    userProduct.setProductInstance(productInstance);
    userProduct.setUserByUserId(deliveryRequestUser);
    userProduct.setCheckoutDate(date);
    userProduct.setUserByCheckoutUserId(GeneralUtils.getCurrentUserFromTLS());
    userProduct.setDeliveryRequest(deliveryRequest);
    
    deliveryRequest.getUserProductSet().add(userProduct);
    
    //remove the product from product_request (bookshelf)
    deliveryRequestUser.removeProductRequest(priority);
    
    //update the status if all products for a deliver request have been checked out
    if (deliveryRequest.getUserProductSet().size() == maxAllowedProductsPerDeliveryRequest) {
      deliveryRequest.setStatus(DeliveryRequestStatusEnum.COMPLETE.toString());
      deliveryRequest.setRequestCompleteDate(date);
      deliveryRequest.setRequestAppliedDate(date);
    }
    
    //decrease the Available in Stock by 1
    //this count will be increased when a book is returned.
    Product product = productInstance.getProduct();
    short availableInStock = product.getAvailableInStock();
    //if no instance is available and a checkout is being done, throw an exception.
    if (availableInStock == 0) {
      String msg = "No Product Instance is available for checkout for productId=" + product.getProductId()
                                  + ", product=" + product.getName();
      logger.error(msg);
      throw new ProductInstanceNotAvailableException(msg);
    }
    product.setAvailableInStock(--availableInStock);
    
    //update the status of the instance
    productInstance.setStatus(ProductInstanceStatusEnum.CHECKEDOUT.getValue());
    
    //save delivery request...hibernate should do this automatically...but its not...not sure why?
    this.saveDeliveryRequest(deliveryRequest);
    
    //save the updated user
    userDao.saveUser(deliveryRequestUser);
    
    return deliveryRequest;
    
  }

  public void adjustDRCompleteDateToNextMonth(DeliveryRequest deliveryRequest) {
    deliveryRequest.setRequestAppliedDate(DateUtils.addDays(deliveryRequest.getUser().getUserMembershipMonthEndDate(new Date()), 1));
    this.saveDeliveryRequest(deliveryRequest);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#returnProduct(int)
   */
  public void returnProduct(int instanceId, String comment) {
    ProductInstance productInstance = productService.getProductInstanceById(instanceId);
    
    //check if the product instance is in checked out state...if not throw exception
    if (!productInstance.getStatus().equals(ProductInstanceStatusEnum.CHECKEDOUT.getValue())) {
      String msg = "ProductInstance you are trying to return is not checked out. Priduct Instance Id=" + productInstance.getProductInstanceId();
      logger.error(msg);
      throw new IllegalStateException(msg);
    }
    
    UserProduct userProduct = productInstance.getUserProduct();
    UserProductHistory userProductHistory = new UserProductHistory(userProduct);
    //set the user who is processing the return
    userProductHistory.setUserByReceiveUserId(GeneralUtils.getCurrentUserFromTLS());
    //set the return time
    userProductHistory.setReceiveDate(new Date());
    //set receive comments
    userProductHistory.setReceiveComment(comment);
    //save UserProductHistory
    deliveryRequestDao.saveUserProductHistory(userProductHistory);
    
    //increase the available in stock by 1
    Product product = productInstance.getProduct();
    short availableInStock = product.getAvailableInStock();
    product.setAvailableInStock(++availableInStock);
    
    //set UserProduct of product instance to null.
    //hibernate will delete the UserProduct
    productInstance.setUserProduct(null);
    deliveryRequestDao.deleteUserProduct(userProduct);
    
    //update the status of returned productInstance
    productInstance.setStatus(ProductInstanceStatusEnum.AVAILABLE.getValue());
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#isUserInMonthlyDeliveryRequestLimitForPlan(com.sparrow.domain.User)
   */
  public boolean isUserInMonthlyDeliveryRequestLimitForPlan(User user) {
    //user has not paid any bill so far, so user has not receive books
    //return true
    if (user.getLastPaidBill() == null ) {
      return true;
    }
    
    //getting the last bill paid plan will not work, since user may have paid a bill immediately after a plan change.
    //If the new plan is effective in future, we want to get the plan that is effective today, and that is still the old plan
    //SubscriptionPlan subscriptionPlan = user.getLastPaidBill().getSubscriptionPlan();
    
    //get the plan that is effective as of today
    SubscriptionPlan subscriptionPlan = userDao.getUserEffectivePlan(user);
    
    //if subscription plan is null, means we do not have any effective plan in effect as of today
    //this means that bill has not been paid for current membership period, since user_plan info is only inserted
    //when bill is paid by the user.
    //isUserInMonthlyDeliveryRequestLimitForPlan is only meaningful if user has paid bill for current membership month
    //and we are sending out a second set of books to the user...so just return true here
    if (subscriptionPlan == null) {
      return true;
    }
    
    //since effective subscription plan is available, do the check
    int maxAllowedDeliveryRequestsPerMonth = subscriptionPlan.getMaxRentalsPerMonth() / this.getMaxAllowedProductsPerDeliveryRequest();
    //Date membershipExpirationDate = user.getExpirationDate();
    Date todaysDate = new Date();
    
    Date membershipMonthStartDate = user.getUserMembershipMonthStartDate(todaysDate);
    
    Date membershipMonthEndDate = user.getUserMembershipMonthEndDate(todaysDate);
    
    boolean isInLimit = false;
    int deliveryRequestsCompletedThisMonth = getCompletedDeliveryRequestCountForUser(user, membershipMonthStartDate, membershipMonthEndDate);
    if (deliveryRequestsCompletedThisMonth < maxAllowedDeliveryRequestsPerMonth) {
      isInLimit = true;
    }
    
    
    return isInLimit;
  }
  
  
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#isUserExceededMonthlyDeliveryRequestLimitForPlan(com.sparrow.domain.User)
   */
  public boolean isUserExceededMonthlyDeliveryRequestLimitForPlan(User user) {
    //get the plan that is effective as of today
    SubscriptionPlan subscriptionPlan = userDao.getUserEffectivePlan(user);
    
    //if subscription plan is null, means we do not have any effective plan in effect as of today
    //this means that bill has not been paid for current membership period, since user_plan info is only inserted
    //when bill is paid by the user.
    //since we have no plan info, we cannot tell if limit has been exceeded...just return false
    if (subscriptionPlan == null) {
      return false;
    }
    
    //since effective subscription plan is available, do the check
    int maxAllowedDeliveryRequestsPerMonth = subscriptionPlan.getMaxRentalsPerMonth() / this.getMaxAllowedProductsPerDeliveryRequest();

    Date todaysDate = new Date();
    
    Date membershipMonthStartDate = user.getUserMembershipMonthStartDate(todaysDate);
    
    Date membershipMonthEndDate = user.getUserMembershipMonthEndDate(todaysDate);
    
    boolean isExceededLimit = false;
    int deliveryRequestsCompletedThisMonth = getCompletedDeliveryRequestCountForUser(user, membershipMonthStartDate, membershipMonthEndDate);
    if (deliveryRequestsCompletedThisMonth > maxAllowedDeliveryRequestsPerMonth) {
      isExceededLimit = true;
    }
    
    
    return isExceededLimit;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getCompletedDeliveryRequestCountForUser(com.sparrow.domain.User, java.util.Date, java.util.Date)
   */
  public int getCompletedDeliveryRequestCountForUser(User user, Date startDate, Date endDate) {
    return deliveryRequestDao.getCompletedDeliveryRequestCountForUser(user, startDate, endDate);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getProductInstances(java.util.List)
   */
  public void getProductInstances(List productRequests) {
    Iterator itr = productRequests.iterator();
    
    while (itr.hasNext()) {
      ProductRequest productRequest = (ProductRequest)itr.next();
      productService.getProductInstances(productRequest.getProduct());
    }
    
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.deliveryrequest.DeliveryRequestService#getUserDeliveryRequestList(com.sparrow.domain.User)
   */
  public List getUserDeliveryRequestList(User user) {
    return deliveryRequestDao.getUserDeliveryRequestList(user);
  }
  
  

}
