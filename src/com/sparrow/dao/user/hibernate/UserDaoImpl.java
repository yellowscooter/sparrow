package com.sparrow.dao.user.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.NoDataFoundException;
import com.sparrow.dao.catalog.ProductStatusEnum;
import com.sparrow.dao.user.UserDao;
import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.City;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.SubscriptionPlan;
import com.sparrow.domain.UserPlan;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserRole;
import com.sparrow.domain.User;
import com.sparrow.domain.UserSearch;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
  
  private static final Log logger = LogFactory.getLog(UserDaoImpl.class);
  private int maxAllowedProductsPerDeliveryRequest;  
  
  public int getMaxAllowedProductsPerDeliveryRequest() {
    return maxAllowedProductsPerDeliveryRequest;
  }

  public void setMaxAllowedProductsPerDeliveryRequest(int maxAllowedProductsPerDeliveryRequest) {
    this.maxAllowedProductsPerDeliveryRequest = maxAllowedProductsPerDeliveryRequest;
  }

  public User saveUser(User user) {
    this.getHibernateTemplate().saveOrUpdate(user);
    return user;
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getUserById(int)
   */
  public User getUserById(int userId) {
    return (User)this.getHibernateTemplate().get(User.class, userId);
  }


  public User getUserByUserName(final String username) throws NoDataFoundException {
    List user = this.getHibernateTemplate().find("from User where username = ?", username);
    
    if (user.size() == 0) {
      throw new NoDataFoundException("User not found with username:" + username);
    }
    if (user.size() > 1) {
      String err = "Cannot have two users with the same username:" + username;
      logger.fatal(err);
      throw new IllegalStateException(err);
    }
    
    return (User)user.get(0);
  }

  public UserRole saveRole(UserRole authority) {
    //return (UserRole)this.getHibernateTemplate().merge(authority);
    this.getHibernateTemplate().saveOrUpdate(authority);
    return authority;
  }

  public UserProduct saveUserProduct(UserProduct userProduct) {
    //return (UserProduct)this.getHibernateTemplate().merge(userProduct);
    this.getHibernateTemplate().saveOrUpdate(userProduct);
    return userProduct;
  }

  
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#userHasPendingReturnsFromPreviousDeliveryRequests(com.sparrow.domain.User, com.sparrow.domain.DeliveryRequest)
   */
  public boolean userHasPendingReturnsFromPreviousDeliveryRequests(final User user, DeliveryRequest deliveryRequest) {
    boolean hasPending = false;
//    List userProductList = this.getHibernateTemplate().findByNamedParam("from UserProduct where user_id = :userId and delivery_request_id != :deliveryRequestId"
//                                                                        , new String[] {"userId", "deliveryRequestId"}
//                                                                        , new String[] {String.valueOf(user.getUserId()), String.valueOf(deliveryRequest.getDeliveryRequestId())});
//    
    //List userProductList = this.getUserProductsByUser(user);
    Set userProductList = user.getUserProducts();
    
    SubscriptionPlan effectiveSubscriptionPlan = this.getUserEffectivePlan(user);
    //if subscription plan is null, fall back to the plan user has selected
    //effectiveSubscriptionPlan can still be NULL after this step since user may not have chosen a plan
    if (effectiveSubscriptionPlan == null) {
      effectiveSubscriptionPlan = user.getSubscriptionPlan();
    }
    
    int maxAllowedProductsWithUserAtOneTime = effectiveSubscriptionPlan.getMaxProductsWithUser();
    
    
    if (userProductList.size() >= maxAllowedProductsWithUserAtOneTime) {
      hasPending = true;
    }
    
    return hasPending;
  }

//  public ProductRequest saveProductRequest(ProductRequest productRequest) {
//    //return (ProductRequest)this.getHibernateTemplate().merge(productRequest);
//    this.getHibernateTemplate().saveOrUpdate(productRequest);
//    return productRequest;
//  }


  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getUserList(int, int)
   */
  public PagedList search(final int start, final int objectsPerPage, final SearchCriteria searchCriteria, final String status) {
    if (searchCriteria == null) {
      throw new IllegalArgumentException("Search criteria cannot be null");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
      logger.debug("Search criteria:" + searchCriteria.toString());
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        
        String searchKey = searchCriteria.getSearchKey();
        
        String decoratedCriteria = searchCriteria.getDecoratedCriteria();

        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(User.class)
                .createAlias("userRoles", "roles")
                .add(Restrictions.eq("roles.id.role", UserRole.ROLE_USER))
                .add(Restrictions.eq("status", status));
        if (decoratedCriteria != null) {
          //since userId is numeric, creating Integer object...otherwise class cast exception is thrown
          if (searchCriteria.getSearchKey().equals("userId")) {
            criteria.add(Restrictions.eq(searchKey, new Integer(searchCriteria.getCriteria())));
          } else {
            criteria.add(Restrictions.ilike(searchKey, decoratedCriteria));  
          }
          
        }

        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        pagedList.setFullListSize(fullListSize);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        
        criteria.addOrder(Order.desc("userId"))
                .setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });

    
   
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getCityList()
   */
  public List getCityList() {
    return this.getHibernateTemplate().find("from City order by city");
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getCityById(int)
   */
  public City getCityById(int cityId) {
    return (City)this.getHibernateTemplate().get(City.class, cityId);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#saveUserSearch(com.sparrow.domain.UserSearch)
   */
  public void saveUserSearch(UserSearch userSearch) {
    this.getHibernateTemplate().save(userSearch);
    
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#saveUserPlan(com.sparrow.domain.UserPlan)
   */
  public UserPlan saveUserPlan(UserPlan userPlan) {
    this.getHibernateTemplate().saveOrUpdate(userPlan);
    return userPlan;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getUserEffectivePlan(com.sparrow.domain.User)
   */
  public SubscriptionPlan getUserEffectivePlan(User user) {
    SubscriptionPlan subscriptionPlan = null;
    UserPlan userPlan = null;
    List userPlanList = this.getHibernateTemplate().findByNamedParam("from UserPlan where userId = :userId " +
                                                                    " and planStartDate <= :today and :today <= planEndDate"
        , new String[] {"userId", "today"}
        , new Object[] {new Integer(user.getUserId()), new Date()});
    
    
    //If there are more than one effective plans for a point in time, ie today, this is not valid..abort
    if (userPlanList.size() > 1) {
      logger.error("User has more than one effective plan. UserId=" + user.getUserId() + " , Plan count:" + userPlanList.size());
      throw new IllegalStateException("User has more than one effective plan. UserId=" + user.getUserId() + " , Plan count:" + userPlanList.size());
    }
    
    //if size is 1, return the plan...else will return null
    if (userPlanList.size() == 1) {
      userPlan = (UserPlan) userPlanList.get(0);
      subscriptionPlan = userPlan.getSubscriptionPlan();  
    }
    
    return subscriptionPlan;
  }

  public PagedList getUsersWithExpiredAccounts(final int start, final int objectsPerPage) {
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        
        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(User.class)
                .createAlias("userRoles", "roles")
                .add(Restrictions.eq("roles.id.role", UserRole.ROLE_USER))
                .add(Restrictions.eq("status", UserStatusEnum.ACTIVE.getValue()))
                .add(Restrictions.neProperty("startDate", "expirationDate"))
                .add(Restrictions.le("expirationDate", new Date()));

        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        pagedList.setFullListSize(fullListSize);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        
        criteria.addOrder(Order.desc("expirationDate"))
                .setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getUsersWithExpiredAccounts()
   */
  public List getUsersWithExpiredAccounts() {
    return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        List userList = new ArrayList();
        
        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(User.class)
                .createAlias("userRoles", "roles")
                .add(Restrictions.eq("roles.id.role", UserRole.ROLE_USER))
                .add(Restrictions.eq("status", UserStatusEnum.ACTIVE.getValue()))
                .add(Restrictions.neProperty("startDate", "expirationDate"))
                .add(Restrictions.le("expirationDate", new Date()));

        
        userList = criteria.list();
        
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + userList.size());
        }
        
        return userList;
      }
    });
    
    
  }
  
  

//  public List getUserPaymentsList(User user) {
//    return user.getPayments();
//  }
  
  
  
  
  
  
  
  

}
