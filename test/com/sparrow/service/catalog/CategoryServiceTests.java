package com.sparrow.service.catalog;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;
import com.sparrow.service.AbstractServiceTests;

/**
 * Unit tests for Product Category and related methods. Tests in this class 
 * rely on test data inserted using sql scripts.
 * @author manishk
 * @since 1.0
 */
public class CategoryServiceTests extends AbstractServiceTests {
  private CategoryService categoryService;
  private ProductService productSevice;
  
  
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  
  public void setProductSevice(ProductService productSevice) {
    this.productSevice = productSevice;
  }




  /**
   * Tests adding a new category, and making one category parent of another.
   * 
   * @since 1.0
   */
  public void testAddCategory() {
    Category topCategory = categoryService.getCategoryById(1);
    int count = jdbcTemplate.queryForInt("select count(0) from category");
    Category category = new Category ("Category Name", "Category Description", topCategory);
    categoryService.saveCategory(category);
    assertEquals("One category should be inserted" 
                , count + 1
                , jdbcTemplate.queryForInt("select count(0) from category"));
    
    Category category1 = new Category ("Category Name1", "Category Description", topCategory);
    categoryService.saveCategory(category1);
    
    Category category2 = new Category ("Category Name2", "Category Description", category1);
    categoryService.saveCategory(category2);
    
    assertEquals("Three categories should be inserted"
                  , count + 3
                  , jdbcTemplate.queryForInt("select count(0) from category"));
              
    assertEquals("Category1 is the parent of category2" 
                , category1.getCategoryId()
                , category2.getParentCategory().getCategoryId());
    
    //call if we want to commit
    //setComplete();
  }
  
  public void testGetCategoryById() {
    Category category = categoryService.getCategoryById(2);
    assertEquals("Could not get category"
                , 2
                , category.getCategoryId());
  }
  
  
  public void testDeleteCategory() {
    Category topCategory = categoryService.getCategoryById(1);
    //get top category count
   int count = jdbcTemplate.queryForInt("select count(0) from category where parent_id=1");
    Category category = addCategory("name1", "description", topCategory);
    assertNotSame("Category id should be > 0" 
                  , 0
                  , category.getCategoryId());
    categoryService.deleteCategory(category);
    List categoryList = (List)categoryService.getTopCategories();
    //setComplete();
    //the count of categories comes out to 1...somehow this test is not working
    assertEquals("The categories count should be 0"
                  , count
                  , categoryList.size());
  }
  
  public void testUpdateCategory() {
    Category topCategory = categoryService.getCategoryById(1);
    Category cat = addCategory("CatName", "description", topCategory);
    cat.setDescription("desc1");
    Category cat1 = categoryService.saveCategory(cat);
    
    assertEquals("Descriptioin should bel desc1"
                , "desc1"
                , cat1.getDescription());
  }
  
  public void testTopCategories() {
    List initTopsList = (List)categoryService.getTopCategories();
    Category topCategory = categoryService.getCategoryById(1);
    addCategory("name1", "description", topCategory);
    addCategory("name2", "description", topCategory);
    List tops = (List)categoryService.getTopCategories();
    assertEquals("Two top level categories expected", initTopsList.size() + 2, tops.size());
  }
  
  public void testGetLeftNavTopCategories() {
    int count = jdbcTemplate.queryForInt("select count(0) from category " +
                                "where parent_id=1 and category_type = 'LEFTNAV'");
    List categoryList = (List)categoryService.getLeftNavTopCategories();
    assertEquals("The leftnave category count does not match", count, categoryList.size());
  }
  
  public void testGetAwardsTopCategories() {
    int count = jdbcTemplate.queryForInt("select count(0) from category " +
                                "where parent_id=1 and category_type = 'AWARD'");
    List categoryList = (List)categoryService.getAwardsTopCategories();
    assertEquals("The leftnave category count does not match", count, categoryList.size());
  }
  
  public void testGetChildCategories() {
    int count = jdbcTemplate.queryForInt("select count(0) from category " +
                                          "where parent_id=1");
    Category topCategory = categoryService.getCategoryById(1);
    Set childCategories = topCategory.getChildCategories();
    assertEquals("Two child level categories expected"
                , count
                , childCategories.size());
  }
  
  public void testGetProductCategories() {
    int count = jdbcTemplate.queryForInt("select count(0) from product_category where category_id=2");
    Category topCategory = categoryService.getCategoryById(2);
    Set productCategories = topCategory.getProductCategories();
    assertEquals("Four productCategories expected"
                , count
                , productCategories.size());
  }
  
  public void testGetProductsForCategory() {
    Category topCategory = categoryService.getCategoryById(2);
    Set productCategories = topCategory.getProductCategories();
    Iterator itr = productCategories.iterator();
    while (itr.hasNext()) {
      ProductCategory prodCat = (ProductCategory) itr.next();
      Product prod = prodCat.getProduct();
      if (prod.getProductId() == 2) {
        assertEquals("Book name does not match"
                      , "The DaVinci Code"
                      , prod.getName());  
      }
      
    }
    
  }
  
  public void testAddProductCategory() {
    Category category = categoryService.getCategoryById(2);
//    int count = category.getProductCategories().size();
    int count = jdbcTemplate.queryForInt("select count(0) from product_category where category_id=2");
    Product product = productSevice.getProductById(1005);
    ProductCategory productCategory = new ProductCategory(category, product);
    categoryService.saveProductCategory(productCategory);
    category.getProductCategories().add(productCategory);
    Category cat = categoryService.saveCategory(category);
    category = categoryService.getCategoryById(cat.getCategoryId());
    
    assertEquals("The new product category mapping was not added", count + 1, category.getProductCategories().size());
  }
  
  public void testGetCategoriesByProduct() {
    int count = jdbcTemplate.queryForInt("select count(0) from product_category " +
        "where product_id=1000");
    Product product = productSevice.getProductById(1000);
    List categories = (List)categoryService.getCategoriesByProduct(product);
    assertEquals("Product should belong to one category", count, categories.size());
    assertEquals("Category name does not match", "Adventure", ((Category)categories.get(0)).getName());
    
  }
  
  public void testGetAwardCategoryForProduct() {
    Product product = productSevice.getProductById(1005);
    Collection awardCategories = categoryService.getAwardCategoriesForProduct(product);
    
    assertEquals("List should be empty", 0, awardCategories.size());
  }
  
  private Category addCategory(String name, String description, Category parentCategory) {
    Category category = new Category (name, description, parentCategory);
    return categoryService.saveCategory(category);
  }
  
  
  
  
}
