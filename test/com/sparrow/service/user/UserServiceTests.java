package com.sparrow.service.user;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.Address;
import com.sparrow.domain.Bill;
import com.sparrow.domain.CheckPayment;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.Payment;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.User;
import com.sparrow.domain.UserNote;
import com.sparrow.domain.UserPlan;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.bill.BillStatusEnum;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.subscription.SubscriptionPlanService;

public class UserServiceTests extends AbstractServiceTests {
  private UserService userService;
  private ProductService productService;
  private SubscriptionPlanService subscriptionPlanService;
  
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
  
  public SubscriptionPlanService getSubscriptionPlanService() {
    return subscriptionPlanService;
  }

  public void setSubscriptionPlanService(SubscriptionPlanService subscriptionPlanService) {
    this.subscriptionPlanService = subscriptionPlanService;
  }

  public void testAddUser() {
    int count = jdbcTemplate.queryForInt("select count(0) from user");
    User user = new User("username", "password", "firstName", "lastName", "Y");
    Address address = new Address("1456 San CArlos Ave", "Apt 302", "San Carlos", "CA", "94070");
    user.setShippingAddress(address);
    user.setBillingAddress(address);
    userService.saveUser(user);
    assertEquals("User should be inserted"
        ,count + 1 
        ,jdbcTemplate.queryForInt("select count(0) from user"));
    
    // id 1 is 3 months plan
    userService.saveUserSubscriptionPlan(user, 1);
    
    assertEquals("User should have one bill created", 1, user.getBills().size());
    Iterator itr = (Iterator)user.getBills().iterator();
    
    Bill bill = (Bill)itr.next();
    assertEquals("Bill status should be pending", BillStatusEnum.PENDING.getValue(), bill.getStatus());
    
    
    
    //setComplete();

  }
  
  /**
   * Test to add a User and Authority.
   * 
   * @since 1.0
   */
  //this test fails since add user tries to send email
//  public void testAddUserAndAuthroity() {
//    int count = jdbcTemplate.queryForInt("select count(0) from user");
//    //32 char password...to test if encrypted value is less than of 50
//    String password = "passwordpasswordpasswordpassword";
//    User user = new User("username", password, "firstName", "lastName", "Y");
//    user = userService.saveUserAsNormalUser(user);
//    
//    assertEquals("User should be inserted"
//        ,count + 1 
//        ,jdbcTemplate.queryForInt("select count(0) from user"));
//    
//    //test if password has been digested
//    assertNotSame("Digest should be different from original password", password, user.getPassword());
//    //setComplete();
//
//  }
  
  
  public void testUpdateUser() {
    User user = new User("username", "password", "firstName", "lastName", "Y");
    Address address = new Address("1456 San CArlos Ave", "Apt 302", "San Carlos", "CA", "94070");
    user.setShippingAddress(address);
    userService.saveUser(user);
    user.setLastname("lastNameX");
    user.getShippingAddress().setState("TX");
    
    
    
    
    User user2 = userService.saveUser(user);
    
    assertEquals("Last name should have been updated"
        , "lastNameX"
        , user2.getLastname());
    assertEquals("State should have been updated"
    , "TX"
    , user2.getShippingAddress().getState());
    
    //  add a not to the user.
    UserNote userNote = new UserNote(new Date(), user2.getUserId(), "This is a test",  user2);
    user.getUserNotes().add(userNote);
    User user3 = userService.saveUser(user);
    
    
    
  }
  
  /**
   * Test to get a user by username and also check if all 
   * Authorities for are fetched.
   * @throws Exception
   * @since 1.0
   */
  public void testGetUserByUserName() throws Exception {
//    User user = new User("username", "password", "firstName", "lastName", "aa@bb.com", 'Y', null);
//    userService.saveUser(user);
//    User user2 = userService.getUserByUserName(user.getUsername());
//    assertTrue("Same user should be fetched", user.equals(user2));
    
    //user1 is added using test data
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    assertEquals("User should have been fetched", "user@friendsofbooks.com", user.getUsername());
    Set auth = user.getUserRoles();
    assertEquals("User should belong to one role", 1, auth.size());
    
    //just to check if it gets user from cache...caching is not working currently 
    user = userService.getUserByUserName("user@friendsofbooks.com");
    
    List userRequsets = user.getProductRequests();
    assertEquals("One request should be in queue"
                  , jdbcTemplate.queryForInt("select count(0) from Product_Request where user_id = 1001")
                  , userRequsets.size());
    
    assertNotNull(user.getShippingAddress());
    
    //check if the correct City was fetched
    assertEquals("User's city was not correct"
        , "Delhi"
        , user.getCity().getCity());
    
    assertEquals("User should have one bill", 1, user.getBills().size());
    
    Bill pendingBill = user.getPendingBill();
    assertNotNull("pending Bill should not be null", pendingBill);
    
    assertEquals("Bill should be penidng", BillStatusEnum.PENDING.getValue(), pendingBill.getStatus());
    
  }
  
//Need to fix this after Product instance changes are complete
//  public void testSaveUserProduct() throws Exception {
//    User user = userService.getUserByUserName("user1");
//    int count = jdbcTemplate.queryForInt("select count(0) from user_product where user_id =" + user.getUserId());
//    //id 1000 is created using test data
//    Product product = productService.getProductById(1000);
//    
//    UserProduct userProduct = new UserProduct(user, product);
//    
//    userService.saveUserProduct(userProduct);
//    assertEquals("UserProduct should be inserted"
//        ,count + 1 
//        ,jdbcTemplate.queryForInt("select count(0) from user_product where user_id =" + user.getUserId()));
//    
//      
//  }
  
  public void testGetUserProductsByUser() throws Exception {
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    Set userProducts = user.getUserProducts();
    assertEquals("One User Product should  be found"
                  ,1
                  ,userProducts.size());
  }
  
  public void testAddProductRequest() throws Exception {
    int count = jdbcTemplate.queryForInt("select count(0) from product_request where user_id = 1001");
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    Product product = productService.getProductById(1000);
    
    userService.addProductRequest(user, product);
    
    User user1 = userService.getUserByUserName("user@friendsofbooks.com");
    assertEquals("Correct count of product requests should be found"
        ,count + 1
        ,user1.getProductRequests().size());
    
    ProductRequest lastAddedProduct = (ProductRequest)user1.getProductRequests().get(user1.getProductRequests().size() - 1 );
    assertEquals("Correct product not found"
                ,1000
                ,lastAddedProduct.getProduct().getProductId());
    
    //setComplete();
  }
  
  public void testGetProductRequests() throws Exception {
    int count = jdbcTemplate.queryForInt("select count(0) from product_request where user_id = 1001");
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    assertEquals("Correct count of product requests should be found"
        ,count
        ,user.getProductRequests().size());
    
    assertEquals("Correct count of product requests not found", count, user.getProductRequests().size());
    
    
  }
  
  public void testRemoveProductRequest() throws Exception {
    int count = jdbcTemplate.queryForInt("select count(0) from product_request where user_id = 1001");
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    userService.removeProductRequest(user, 1);
    assertEquals("One item should have been removed from the list"
                ,count - 1
                ,user.getProductRequests().size());
    
    
   
    
  }
  
  public void testProductRequestListMoveUp() throws Exception {
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    
    List productRequests = user.getProductRequests();
    ProductRequest productRequest1 = (ProductRequest)productRequests.get(3);
    //user.moveProductRequestOneUp(3);
    userService.moveProductRequestOneUp(user, 3);
    ProductRequest productRequest2 = (ProductRequest)productRequests.get(2);
    assertTrue("Initial object at index 3 should now be at 2", productRequest1.equals(productRequest2));
    
  }
  
  public void testProductRequestListMoveDown() throws Exception {
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    
    List productRequests = user.getProductRequests();
    ProductRequest productRequest1 = (ProductRequest)productRequests.get(2);
    //user.moveProductRequestOneDown(2);
    userService.moveProductRequestOneDown(user, 2);
    ProductRequest productRequest2 = (ProductRequest)productRequests.get(3);
    assertTrue("Initial object at index 3 should now be at 2", productRequest1.equals(productRequest2));
    
  }
  
  public void testProductRequestListMoveToHead() throws Exception {
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    
    List productRequests = user.getProductRequests();
    ProductRequest productRequest1 = (ProductRequest)productRequests.get(3);
    //user.moveProductRequestToHead(3);
    userService.moveProductRequestToHead(user, 3);
    ProductRequest productRequest2 = (ProductRequest)productRequests.get(0);
    assertTrue("Initial object at index 3 should now be at 2", productRequest1.equals(productRequest2));
    
  }
  
  public void testProductRequestListMoveToTail() throws Exception {
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    
    List productRequests = user.getProductRequests();
    ProductRequest productRequest1 = (ProductRequest)productRequests.get(2);
    //user.moveProductRequestToTail(2);
    userService.moveProductRequestToTail(user, 2);
    ProductRequest productRequest2 = (ProductRequest)productRequests.get(productRequests.size() - 1);
    assertTrue("Initial object at index 3 should now be at 2", productRequest1.equals(productRequest2));
    
  }
  
  public void testGetCityList() {
    int count = jdbcTemplate.queryForInt("select count(0) from city");
    List cityList = userService.getCityList();
    assertEquals("Correct count of cities not fetched", count, cityList.size());
    
  }
  
  public void testGetUserSubscriptionPlan() throws Exception {
    //fetch users subscription plan
    User user = userService.getUserByUserName("user@friendsofbooks.com");
    SubscriptionPlan subscriptionPlan = user.getSubscriptionPlan();
    assertEquals("Correct subscription plan id not retrieved", 3, subscriptionPlan.getPlanId());
    
  }
  
  public void testGetUserList() {
    int count = jdbcTemplate.queryForInt("select count(0) from user, user_role where " +
        "user.user_id = user_role.user_id and user_role.role='ROLE_USER' ");
    //pass null searchCriteria...see if it works
    PagedList pagedList = userService.getUserList(1, null, UserStatusEnum.ACTIVE);
    //if total count is less than page size, then just compare against count
    if (count > userService.getUserListPageSize()) {
      assertEquals("Pagesize not correct", userService.getUserListPageSize(), pagedList.getList().size());
    } else {
      assertEquals("correct recordcount not fetched", count, pagedList.getList().size());
    }
    
    assertEquals("Full list size not correct", count, pagedList.getFullListSize());
    
    pagedList = userService.getUserList(100, null, UserStatusEnum.ACTIVE);
    assertEquals("Paged list size not correct for page=100", 0, pagedList.getList().size());
    
    //paging starts from 1...check behavior for page=0
    //paging tag treats this as page 1 and get the data
    pagedList = userService.getUserList(0, null, UserStatusEnum.ACTIVE);
    if (count > userService.getUserListPageSize()) {
      assertEquals("Paged list size not correct for page=0", userService.getUserListPageSize(), pagedList.getList().size());
    } else {
      assertEquals("Paged list size not correct for page=0", count, pagedList.getList().size());
    }
    
    
    count = jdbcTemplate.queryForInt("select count(0) from user, user_role where " +
    "user.user_id = user_role.user_id and user.lastname like '%lastname%' and user_role.role='ROLE_USER' ");
    //test search
    pagedList = userService.searchByLastName(1, "lastname", UserStatusEnum.ACTIVE);
    assertEquals("Paged list size not correct for search", count, pagedList.getList().size());
    
  }
  
  public void testGetUserBills() {
    User user = userService.getUserById(1001);
    Set paymentsList = user.getBills();
    
    //this user should have 1 bill
    assertEquals("Correct count of bills not fetched", 1, paymentsList.size());
    
  }
  
  
  
  
//  public void testSaveAndVerifyUser() throws Exception {
//    User user = userService.getUserByUserName("user2");
//    userService.saveAndVerifyUser(user.getUserId());
//  }
  
  public void testGetMembershipExpirationWarningCompareDate() {
    Date date = new Date();
    Date compareDate = userService.getMembershipExpirationWarningCompareDate();
    long milliSecToAdd = userService.getMembershipExpirationThreshold() * 60*60*24*1000;
    assertTrue("Threshold data comparasion failed", (date.getTime() + milliSecToAdd) <= compareDate.getTime());
  }
  
  public void testSaveUserPlan() {
    //User user = userService.getUserById(1001);
    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionPlanById(1);
    Date startDate = new Date();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(startDate);
    calendar.add(Calendar.MONTH, 1);
    Date endDate = calendar.getTime();
    UserPlan userPlan = new UserPlan(1001, subscriptionPlan, startDate, endDate);
    UserPlan savedUserPlan = userService.saveUserPlan(userPlan);
    assertTrue("User Plan id should be greater than 0", savedUserPlan.getUserPlanId() > 0);
  }
  
  public void testGetUserEffectivePlan() {
    SubscriptionPlan subscriptionPlan = subscriptionPlanService.getSubscriptionPlanById(1);
    Date startDate = new Date();
    GregorianCalendar calendar = new GregorianCalendar();
    calendar.setTime(startDate);
    calendar.add(Calendar.MONTH, 1);
    Date endDate = calendar.getTime();
    UserPlan userPlan = new UserPlan(1001, subscriptionPlan, startDate, endDate);
    UserPlan savedUserPlan = userService.saveUserPlan(userPlan);
    
    User user = userService.getUserById(1001);
    
    SubscriptionPlan effectivePlan = userService.getUserEffectivePlan(user);
    assertTrue("Effective Plan id should be 1", effectivePlan.getPlanId() == 1);
  }
  
  
  public void testGetUsersWithExpiredAccounts() {
    PagedList userList = userService.getUsersWithExpiredAccounts(1);
    assertTrue("Expired accounts list should have a few Users", userList.getList().size() > 0);
  }
  
  public void testUserSearch() {
    PagedList searchList = userService.userSearch(1);
    assertTrue("Expired accounts list should have a few Users", searchList.getList().size() > 0);
  }
  
  public void testUserSearchCount() {
    PagedList searchList = userService.userSearchCount(1);
    assertTrue("Expired accounts list should have a few Users", searchList.getList().size() > 0);
  }
  
  

}
