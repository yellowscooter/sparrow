package com.sparrow.service.catalog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sparrow.dao.catalog.CategoryDao;
import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;

/**
 * The category service implementation.
 * @author manishk
 * @since 1.0
 */
public class CategoryServiceImpl implements CategoryService {
  private CategoryDao categoryDao;
  
  public CategoryDao getCategoryDao() {
    return categoryDao;
  }

  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }
  
  
  /**
   * Add a new category.
   * @param category a new category instance
   * @since 1.0 
   */
  public Category saveCategory(Category category) {
    return categoryDao.saveCategory(category);
  }
  
  public void deleteCategory(Category category) {
    categoryDao.deleteCategory(category);
  }
  
  public Category updateCategory(Category category) {
    return categoryDao.saveCategory(category);
  }
  
  public Category getCategoryById(int categoryId) {
    return categoryDao.getCategoryById(categoryId);
  }

  public Collection getTopCategories() {
    return categoryDao.getTopCategories();
  }
  
  public Collection getAwardsTopCategories() {
    return categoryDao.getTopCategories(CategoryTypeEnum.AWARD);
  }

  public Collection getLeftNavTopCategories() {
    return categoryDao.getTopCategories(CategoryTypeEnum.LEFTNAV);
  }

  public ProductCategory saveProductCategory(ProductCategory productCategory) {
    return categoryDao.saveProductCategory(productCategory);
  }
  
  public void deleteProductCategory(ProductCategory productCategory) {
    categoryDao.deleteProductCategory(productCategory);
  }

  public Collection getCategoriesByProduct(Product product) {
    //return categoryDaoJdbcUtil.getCategoriesByProduct(product);
    return categoryDao.getCategoriesByProduct(product);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.CategoryService#getAwardCategoriesForProduct(com.sparrow.domain.Product)
   */
  public Collection getAwardCategoriesForProduct(Product product) {
    List awardCategories = new ArrayList();
    Set productCategories = (Set)product.getProductCategories();
    Iterator itr = productCategories.iterator();
    
    while (itr.hasNext()) {
      ProductCategory productCategory = (ProductCategory)itr.next();
      Category category = productCategory.getCategory();
      if (CategoryTypeEnum.AWARD.getValue().equals(category.getCategoryType())) {
        awardCategories.add(category);
      }
    }
    
    return awardCategories;
  }
  
  
  
  
  
  
}
