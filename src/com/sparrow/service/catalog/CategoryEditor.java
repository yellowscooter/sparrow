package com.sparrow.service.catalog;

import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.sparrow.domain.Category;
import com.sparrow.domain.Company;

public class CategoryEditor extends PropertyEditorSupport {
  private CategoryService categoryService;
  
  /**
   * Constructor that takes in ProductServiceImpl as argument.
   * @param productService
   * @since 1.0
   */
  public CategoryEditor(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  
  /**
   * This method receives the user selected category ids as a comma seperated String.
   * It gets each id out of cateforyIds and fetches the Category instance for the Id.
   * The Set of such Category objects is then set as the Product.categories which can 
   * be saved after the Product is saved.
   */
  public void setAsText(String categoryIds) throws IllegalArgumentException {
    if (categoryIds == null) {
      setValue(null);
    }
    Set categoriesToSet = new HashSet();
    
    //split comma seperated string
    String[] splitCategoryIds = categoryIds.split(",");
    
    for (int i = 0; i < splitCategoryIds.length; i++) {
      String categoryId = splitCategoryIds[i];
      Category category = categoryService.getCategoryById(Integer.parseInt(categoryId));
      categoriesToSet.add(category);
    }
    setValue(categoriesToSet);
  }
  
  public String getAsText() {
    return null;
  }

}
