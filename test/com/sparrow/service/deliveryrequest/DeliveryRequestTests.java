package com.sparrow.service.deliveryrequest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.sparrow.dao.deliveryrequest.DeliveryRequestStatusEnum;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.User;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.catalog.ProductInstanceStatusEnum;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.UserService;

public class DeliveryRequestTests extends AbstractServiceTests {
  private DeliveryRequestService deliveryRequestService;
  private UserService userService;
  private ProductService productService;

  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(
      DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
//fails in sending email
//  public void testSaveDeliveryRequest() throws Exception {
//    User user = (User)userService.getUserByUserName("user2");
//    //int count = jdbcTemplate.queryForInt("select count(0) from delivery_request where user_id = " + user.getUserId());
//    DeliveryRequest deliveryRequest = new DeliveryRequest(user.getUserId(), new Date());
//    DeliveryRequest deliveryRequest1 = deliveryRequestService.saveDeliveryRequest(deliveryRequest);
//    assertTrue("Id should have been assigned to delivery request on save", deliveryRequest1.getDeliveryRequestId() > 0);
//    //setComplete();
//  }
  
  public void testGetPendingDeliveryRequestList() {
    List list = deliveryRequestService.getPendingDeliveryRequestList();
    
    assertNotNull("Pending Delivery Request list should not be null", list);
  }
  
  public void testGetDeliveryRequestById() {
    DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(1);
    
    assertEquals("One delivery reuqest should be fetched", 1, deliveryRequest.getDeliveryRequestId());
    
    
  }
//fails in sending email  
//  public void testCheckoutProduct() throws Exception {
//    DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(1);
//    int userId = deliveryRequest.getUserId();
//    User user = userService.getUserById(userId);
//    int bookshelfSize = user.getProductRequests().size();
//    deliveryRequestService.checkoutProduct(1001, deliveryRequest, 0, user);
//    
//    user = userService.getUserById(userId);
//    deliveryRequest = deliveryRequestService.getDeliveryRequestById(1);
//    assertEquals("The number of books in bookshelf should be reduced by 1", bookshelfSize - 1 , user.getProductRequests().size());
//    
//    assertEquals("The delivery request status should be INTRANSIT", DeliveryRequestStatusEnum.COMPLETE.toString() , deliveryRequest.getStatus());
//    
//  }
  
  public void testReturnProduct() {
    ProductInstance productInstance = productService.getProductInstanceById(1000);
    assertEquals("Initial status of product instance should be checkedout"
                  , ProductInstanceStatusEnum.CHECKEDOUT.getValue()
                  , productInstance.getStatus());
    
    deliveryRequestService.returnProduct(1000, "return");
    
    ProductInstance productInstanceReturned = productService.getProductInstanceById(1000);
    assertEquals("Status of returned product instance should be A-Available."
                , ProductInstanceStatusEnum.AVAILABLE.getValue()
                , productInstance.getStatus());
    
    //userProduct should be null
    assertNull("UserProduct should be null since instance is available", productInstanceReturned.getUserProduct());
  }
  
//  public void testGetIncompleteDeliveryRequestByUser() throws Exception {
//    User user = userService.getUserById(1001);
//    DeliveryRequest deliveryRequest = deliveryRequestService.getInCompleteDeliveryRequestByUser(user);
//    assertNotNull("Delivery Request should have been found", deliveryRequest);
//  }
  
  public void testGetCompletedDeliveryRequestCountForUser() {
    User user = userService.getUserById(1001);
    Date endDate = new Date();
    GregorianCalendar startDateCal = new GregorianCalendar();
    startDateCal.setTime(endDate);
    startDateCal.add(Calendar.MONTH, -1);
    Date startDate = startDateCal.getTime();
    int count = deliveryRequestService.getCompletedDeliveryRequestCountForUser(user, startDate, endDate);
    assertEquals("Count should be 0"
        , 0
        , count);
  }
  
  public void testGetUserDeliveryRequestList() {
    User user = userService.getUserById(1001);
    List deliveryRequestList = deliveryRequestService.getUserDeliveryRequestList(user);
    
    assertTrue("The Delivery request list size should be > 0", deliveryRequestList.size() > 0 );
  }
  

}
