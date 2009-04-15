package com.sparrow.dao.catalog.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.catalog.CategoryDao;
import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;
import com.sparrow.service.catalog.CategoryTypeEnum;

/**
 * The category DAO implementation class.
 * @author manishk
 * @since 1.0
 */
public class CategoryDAOImpl extends HibernateDaoSupport implements CategoryDao {

  public Category saveCategory(Category category) {
    //return (Category)this.getHibernateTemplate().merge(category);
    this.getHibernateTemplate().saveOrUpdate(category);
    return category;
  }
  
  public void deleteCategory(Category category) {
    this.getHibernateTemplate().delete(category);
  }
  
  public Collection getTopCategories() {
    return this.getHibernateTemplate().find("from Category where parent_id = " + Category.ROOT_CATEGORY_ID + " order by name");
  }
  
  public Collection getTopCategories(CategoryTypeEnum categoryType) {
    return this.getHibernateTemplate().findByNamedParam("from Category where category_type = :categoryType " +
                                      " and parent_id = " + Category.ROOT_CATEGORY_ID + " order by name", "categoryType", categoryType.getValue());
  }

  public Category getCategoryById(int id) {
    return (Category) this.getHibernateTemplate().get(Category.class, new Integer(id));
  }

  public ProductCategory saveProductCategory(ProductCategory productCategory) {
    //return (ProductCategory)this.getHibernateTemplate().merge(productCategory);
    this.getHibernateTemplate().saveOrUpdate(productCategory);
    return productCategory;
  }
  
  

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.CategoryDao#deleteProductCategory(com.sparrow.domain.ProductCategory)
   */
  public void deleteProductCategory(ProductCategory productCategory) {
    this.getHibernateTemplate().delete(productCategory);
    
  }

  /**
   * Fetch all the Categories to which a product belongs.
   * @param product
   * @return
   * @since 1.0
   */
  public Collection getCategoriesByProduct(Product product) {
    //List categories = null;
    return this.getHibernateTemplate().find("select cat from Category cat " +
                                            "join cat.productCategories prodCat" +
                                            " where prodCat.id.productId = ? ", product.getProductId());
    
    //return categories;
  }

  
  
  
  

}
