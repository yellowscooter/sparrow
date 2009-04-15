package com.sparrow.service.deliveryrequest;

import java.util.Date;
import java.util.List;

import com.sparrow.dao.deliveryrequest.DeliveryRequestStatusEnum;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.User;

/**
 * Serivce interface to manage delivery requests
 * 
 * @author manishk
 * @since 1.0
 */
public interface DeliveryRequestService {
  /**
   * Save or update a {@link DeliveryRequest}
   * @param deliveryRequest
   * @return
   * @since 1.0
   */
  public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest);
  
  /**
   * Fetch the {@link DeliveryRequest} {@link List} for requests in 
   * {@link DeliveryRequestStatusEnum#PENDING} status.
   * @return
   * @since 1.0
   */
  public List getPendingDeliveryRequestList();
  
  /**
   * Fetch a {@link DeliveryRequest} by it Id
   * @param deliveryRequestId
   * @return
   * @since 1.0
   */
  public DeliveryRequest getDeliveryRequestById(int deliveryRequestId);
  
  /**
   * Fetch delivery request for a user
   * @param user
   * @return {@link DeliveryRequest} if one exists in PENDING or INTRANSIT status,
   *         else returns null
   * @throws DeliveryRequestAlreadyExistsException if there is an existing {@link DeliveryRequest} in PENDING or INTRANSIT state.
   * @since 1.0
   */
  public DeliveryRequest getInCompleteDeliveryRequestByUser(User user) throws DeliveryRequestAlreadyExistsException;
  
  /**
   * Checkout a {@link ProductInstance} for a User.
   * 
   * @param instanceId the instance id of {@link Product} being checked out
   * @param deliveryRequest the delviery request id being processed
   * @param user TODO
   * @param the priority of {@link ProductRequest} from user bookshelf being checked out.
   * @return TODO
   * @throws ProductInstanceNotAvailableException if no {@link ProductInstance} is available for checkout.
   * @throws MaximumAllowedCheckoutProductsExceeded if previously checked out books have not been returned.
   * @since 1.0
   */
  public DeliveryRequest checkoutProduct(int instanceId, DeliveryRequest deliveryRequest, int priority, User deliveryRequestUser)
                                throws ProductInstanceNotAvailableException, MaximumAllowedCheckoutProductsExceeded;
  
  /**
   * Return a {@link ProductInstance} and make it available for other {@link User}s
   * 
   * @param instanceId the instance Id of the product to be returned.
   * @param comment the comment entered by user who processed the return.
   * @since 1.0
   */
  public void returnProduct(int instanceId, String comment);
  
  /**
   * Every plan has a maximum number of Books allwed per month. This method returns true if user has not reached or exceeded that limit.
   * If the limit is reached or exceeded, returns false.
   * 
   * @return
   * @since 1.0
   */
  public boolean isUserInMonthlyDeliveryRequestLimitForPlan(User user);
  
  /**
   * Returns true if user has exceeded monthly delivery request limit for plan
   * @param user
   * @return
   * @since 1.0
   */
  public boolean isUserExceededMonthlyDeliveryRequestLimitForPlan(User user);
  
  /**
   * Fetches the {@link DeliveryRequest}s in {@link DeliveryRequestStatusEnum#COMPLETE} status for a User, where the delivery request date
   * is between the startDate and endDate.
   * This method can be used to determine how many requests have been completed for a user for a month. This will be useful in enforcing the max books/month limit.
   * 
   * @param user
   * @param startDate
   * @param endDate
   * @return
   * @since 1.0
   */
  public int getCompletedDeliveryRequestCountForUser(User user, Date startDate, Date endDate);
  
  /**
   * Loops over all products in {@link ProductRequest} list and populates the {@link ProductInstance}s for each {@link Product}.
   * @param productRequests
   * @since 1.0
   */
  public void getProductInstances(List productRequests);
  
  /**
   * Fetch a list of Delivery requests for User. Only fetches previous 20 delivery requests sorted by 
   * deliveryRequestId
   * @param user
   * @return
   * @since 1.0
   */
  public List getUserDeliveryRequestList(User user);
  
  /**
   * Updates the delivery request applied date to the start date of next membership month.
   * This method should be called if a delivery request is being processed for a user
   * even if the quota of max allowed books/month for user is over.
   * This method will update the {@link DeliveryRequest#setRequestAppliedDate(Date)} to the beginning of next membership month.
   * This way count of max books/month is maintained for next month too. 
   * @param deliveryRequest
   * @since 1.0
   */
  public void adjustDRCompleteDateToNextMonth(DeliveryRequest deliveryRequest);

}
