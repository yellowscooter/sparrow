package com.sparrow.dao.catalog;

import java.util.Collection;

import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;
import com.sparrow.service.catalog.CategoryTypeEnum;

/**
 * Interface for Category maintenance.
 * @author manishk
 * @since 1.0
 */
public interface CategoryDao {
  /**
   * Add a category.
   * @param category the new category instance
   * @since 1.0
   */
  public Category saveCategory(Category category);
  public void deleteCategory(Category category);
  public Category getCategoryById(int id);
  
  /**
   * Get a list of all top categories.
   * @return
   * @since 1.0
   */
  public Collection getTopCategories();
  
  
  /**
   * Get top level {@link Category}s of a particular category type 
   * @param categoryType
   * @return
   * @since 1.0
   */
  public Collection getTopCategories(CategoryTypeEnum categoryType);
  
  /**
   * Save the mapping between a Product and Category.
   * @param productCategory
   * @return
   * @since 1.0
   */
  public ProductCategory saveProductCategory(ProductCategory productCategory);
  
  /**
   * Delete a {@link ProductCategory}
   * @param productCategory
   * @since 1.0
   */
  public void deleteProductCategory(ProductCategory productCategory);
  
    
  public Collection getCategoriesByProduct(Product product);
  
  
  
}
