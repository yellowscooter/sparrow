package com.sparrow.service.catalog;

import java.util.Collection;

import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;


/**
 * The public interface of the Category Service
 * @author manishk
 * @since 1.0
 */
public interface CategoryService {
  /**
   * Save new or update a category.
   * @param category
   * @return
   * @since 1.0
   */
  public Category saveCategory(Category category);
  public void deleteCategory(Category category);
  public Category getCategoryById(int categoryId);

  /**
   * Get a list of all top level categories.
   * @return
   * @since 1.0
   */
  public Collection getTopCategories();
  
  /**
   * Fetches {@link Category} list of type {@link CategoryTypeEnum#LEFTNAV}
   * @return
   * @since 1.0
   */
  public Collection getLeftNavTopCategories();
  
  /**
   * Fetches {@link Category} list of type {@link CategoryTypeEnum#AWARD}
   * @return
   * @since 1.0
   */
  public Collection getAwardsTopCategories();
  
  /**
   * Save the relation between product and category.
   * @param productCategory
   * @return
   * @since 1.0
   */
  public ProductCategory saveProductCategory(ProductCategory productCategory);
  
  public void deleteProductCategory(ProductCategory productCategory);
  
  public Collection getCategoriesByProduct(Product product);
  
  /**
   * Extracts a list of {@link CategoryTypeEnum#AWARD} type categories from a list of categories of a {@link Product
   * @param process
   * @return
   * @since 1.0
   */
  public Collection getAwardCategoriesForProduct(Product product);

}
