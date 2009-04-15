package com.sparrow.dao.catalog.hibernate;

import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.catalog.HomeProductDao;
import com.sparrow.domain.HomeProduct;

public class HomeProductDaoImpl extends HibernateDaoSupport implements HomeProductDao {

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.HomeProductDao#getAllHomeProducts()
   */
  public Collection getAllHomeProducts() {
    return this.getHibernateTemplate().find("from HomeProduct order by priority");
  }
  
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.HomeProductDao#getHomeProductById(int)
   */
  public HomeProduct getHomeProductById(int productId) {
    return (HomeProduct)this.getHibernateTemplate().get(HomeProduct.class, new Integer(productId));
  }



  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.HomeProductDao#addProductToHomeProductList(com.sparrow.domain.HomeProduct)
   */
  public void saveHomeProduct(HomeProduct homeProduct) {
    //for some reason merge does not work...maybe since the object
    //has <generator class="foreign" >
    //this.getHibernateTemplate().merge(homeProduct);
    this.getHibernateTemplate().save(homeProduct);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.HomeProductDao#deleteProduct(com.sparrow.domain.HomeProduct)
   */
  public void deleteHomeProduct(HomeProduct homeProduct) {
    this.getHibernateTemplate().delete(homeProduct);
  }
  
  
  
}
