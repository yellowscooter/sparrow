package com.sparrow.dao.deliveryrequest.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.catalog.hibernate.ProductDaoImpl;
import com.sparrow.dao.deliveryrequest.DeliveryRequestDao;
import com.sparrow.dao.deliveryrequest.DeliveryRequestStatusEnum;
import com.sparrow.dao.user.UserStatusEnum;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.User;
import com.sparrow.domain.UserProduct;
import com.sparrow.domain.UserProductHistory;


public class DeliveryRequestDaoImpl extends HibernateDaoSupport implements DeliveryRequestDao {
  private static final Log logger = LogFactory.getLog(DeliveryRequestDaoImpl.class);
  private static final int DELIVERY_REQUEST_LIST_SIZE = 20;
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#saveDeliveryRequest(com.sparrow.domain.DeliveryRequest)
   */
  public DeliveryRequest saveDeliveryRequest(DeliveryRequest deliveryRequest) {
    //return (DeliveryRequest)this.getHibernateTemplate().merge(deliveryRequest);
    this.getHibernateTemplate().saveOrUpdate(deliveryRequest);
    return deliveryRequest;
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#getPendingDeliveryRequestList()
   */
  public List getPendingDeliveryRequestList() {
    return (List)this.getHibernateTemplate()
                          .findByNamedParam("select deliveryRequest from DeliveryRequest deliveryRequest " +
                              " join deliveryRequest.user user " +
                              " where deliveryRequest.status = :status " +
                              " and user.status = '" + UserStatusEnum.ACTIVE.getValue() + "'"  +
                              " order by deliveryRequest.deliveryRequestId desc"
                          , "status"
                          , DeliveryRequestStatusEnum.PENDING.getValue());
  }

  public DeliveryRequest getDeliveryRequestById(int delvieryRequestId) {
    return (DeliveryRequest)this.getHibernateTemplate().get(DeliveryRequest.class, delvieryRequestId);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#getInCompleteDeliveryRequestByUser(com.sparrow.domain.User)
   */
  public DeliveryRequest getInCompleteDeliveryRequestByUser(User user) {
    DeliveryRequest deliveryRequest = null;
    List deliveryRequests = (List)this.getHibernateTemplate().findByNamedParam("from DeliveryRequest where status IN (:pending, :intransit)" +
                                                                              " and user.userId = :userId "
                                                                              , new String[] {"pending", "intransit", "userId"}
                                                                              , new Object[] {DeliveryRequestStatusEnum.PENDING.toString()
                                                                                              , DeliveryRequestStatusEnum.INTRANSIT.toString()
                                                                                              , user.getUserId()});
    if (deliveryRequests.size() > 1) {
      String msg = "User with username =" + user.getFullname() + " has multiple incomplete delivery requests";
      logger.error(msg);
      throw new IllegalStateException(msg);
    } else if (deliveryRequests.size() == 1) {
      deliveryRequest = (DeliveryRequest)deliveryRequests.get(0);
    }

    return deliveryRequest;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#saveUserProductHistory(com.sparrow.domain.UserProductHistory)
   */
  public UserProductHistory saveUserProductHistory(UserProductHistory userProductHistory) {
    //return (UserProductHistory)this.getHibernateTemplate().merge(userProductHistory);
    this.getHibernateTemplate().saveOrUpdate(userProductHistory);
    return userProductHistory;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#deleteUserProduct(com.sparrow.domain.UserProduct)
   */
  public void deleteUserProduct(UserProduct userProduct) {
    this.getHibernateTemplate().delete(userProduct);
    
  }

  public int getCompletedDeliveryRequestCountForUser(User user, Date startDate, Date endDate) {
    List deliveryRequests = this.getHibernateTemplate().findByNamedParam("from DeliveryRequest where user.userId = :userId and status = :status " +
                                                                          " and :startDate <= requestAppliedDate and requestAppliedDate <= :endDate"
        , new String[] {"userId", "status", "startDate", "endDate"}
        , new Object[] {user.getUserId(), DeliveryRequestStatusEnum.COMPLETE.toString(), startDate, endDate});
    
    
    return deliveryRequests.size();
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.deliveryrequest.DeliveryRequestDao#getUserDeliveryRequestList(com.sparrow.domain.User)
   */
  public List getUserDeliveryRequestList(User user) {
    List deliveryRequestsList = (List)this.getHibernateTemplate().findByNamedParam("from DeliveryRequest where " +
                                                                              " user.userId = :userId Order By deliveryRequestId desc LIMIT " + DELIVERY_REQUEST_LIST_SIZE
                                                                              , new String[] {"userId"}
                                                                              , new Object[] {user.getUserId()});
    return deliveryRequestsList;
  }
  
  
  
  
  
  
  
  

  
}
