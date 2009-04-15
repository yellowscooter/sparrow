package com.sparrow.dao.deliveryrequest;

import java.util.Date;
import java.util.List;

import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.User;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserProductHistory;

public interface DeliveryRequestDao {
  
  /**
   * Save new or update a delivery request.
   * @param deliveryRequest
   * @return
   * @since 1.0
   */
  public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest);
  
  /**
   * Get a list of all PENDING {@link DeliveryRequest}s for users where status is Active, 
   * meaning users who are active.
   * @return
   * @since 1.0
   */
  public List getPendingDeliveryRequestList();
  
  /**
   * Fetch a {@link DeliveryRequest} by its Id
   * @param delvieryRequestId
   * @return
   * @since 1.0
   */
  public DeliveryRequest getDeliveryRequestById(int delvieryRequestId);
  
  /**
   * Fetch delivery request for a user
   * @param user
   * @return {@link DeliveryRequest} if one exists in PENDING or INTRANSIT status,
   *         else returns null
   * @since 1.0
   */
  public DeliveryRequest getInCompleteDeliveryRequestByUser(User user);
  
  /**
   * Save new or update a {@link UserProductHistory}
   * @param userProductHistory
   * @return
   * @since 1.0
   */
  public UserProductHistory saveUserProductHistory(UserProductHistory userProductHistory);
  
  /**
   * Delete a {@link UserProduct}
   * 
   * @param userProduct
   * @since 1.0
   */
  public void deleteUserProduct(UserProduct userProduct);
  
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
   * Fetch a list of Delivery requests for User. Only fetches previous 20 delivery requests sorted by 
   * deliveryRequestId
   * @param user
   * @return
   * @since 1.0
   */
  public List getUserDeliveryRequestList(User user);
  
}
