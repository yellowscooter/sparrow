package com.sparrow.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sparrow.service.catalog.CategoryTypeEnum;

/**
 * The category to which a product belongs. 
 * This class has a bidirectional relationsip to child categories, 
 * i.e. a parent can navigate to all its childern, and a child
 * can navigate to its parent. 
 * 
 * @author manishk
 * @since 1.0
 * @see ProductCategory
 * @see Product
 */
public class Category implements Serializable {
  /**
   * The id of Root category is 1. This is loaded as seed data, 
   * so is constant and should not be changed.
   */
  public static final int ROOT_CATEGORY_ID = 1;
  
  private int categoryId;

  private String name;

  private String description;
  
  /**
   * The parent category of this category.
   */
  private Category parentCategory;

  /**
   * Set of all ProductCategories that belong to this category.
   */
  private Set productCategories = new HashSet(0);

  /**
   * Child categories of this parent category.
   */
  private Set childCategories = new HashSet(0);
  
  private String categoryType;

  public Category() {
  }

  public Category(String name, String description, Category parentCategory) {
    this.description = description;
    this.name = name;
    this.parentCategory = parentCategory;
  }

  public Category(Category parentCategory, String name,
      String description, Set productCategories, Set childCategories) {
    this.parentCategory = parentCategory;
    this.name = name;
    this.description = description;
    this.productCategories = productCategories;
    this.childCategories = childCategories;
  }

  public int getCategoryId() {
    return this.categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public Category getParentCategory() {
    return this.parentCategory;
  }

  public void setParentCategory(Category parentCategory) {
    this.parentCategory = parentCategory;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set getProductCategories() {
    return this.productCategories;
  }

  public void setProductCategories(Set productCategories) {
    this.productCategories = productCategories;
  }

  public Set getChildCategories() {
    return this.childCategories;
  }

  public void setChildCategories(Set childCategories) {
    this.childCategories = childCategories;
  }
  
  public String getCategoryType() {
    return categoryType;
  }

  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + categoryId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Category other = (Category) obj;
    if (categoryId != other.categoryId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return String.valueOf(categoryId);
  }
  
  

  

  

}
