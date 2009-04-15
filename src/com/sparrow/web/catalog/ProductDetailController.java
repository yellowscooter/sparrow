package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Product;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.web.WebConstants;

public class ProductDetailController extends SimpleFormController {
  ProductService productService;
  CategoryService categoryService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
  public CategoryService getCategoryService() {
    return categoryService;
  }

  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    ModelMap model = new ModelMap();
    int productId = ServletRequestUtils.getIntParameter(request, "productId", 0);
    
    HttpSession session = request.getSession();
    
    //    if the category list is not present in session, set it.
    if (session.getAttribute(WebConstants.CATEGORY_LIST) == null) {
      //categories were modified to be of various types...LEFTNAV, AWARD etc
      //List categoryList = (List)categoryService.getTopCategories();
      List categoryList = (List)categoryService.getLeftNavTopCategories();
      session.setAttribute(WebConstants.CATEGORY_LIST, categoryList);  
    }
    //if productId is 0, redirect back to home page of catalog
    
    //else
    Product product = productService.getProductById(productId);
    model.put("product", product);    
    List awardCategories = (List)categoryService.getAwardCategoriesForProduct(product);
    model.put("awardCategories", awardCategories);
    List categories = (List)categoryService.getCategoriesByProduct(product);
    model.put("tags", categories);
    
    //put the param to be displayed in page title
    model.put("dynamicPageTitle", product.getName() + " by " + product.getAuthor().getFullName() + " |");
    model.put("dynamicMetaDescription", product.getName() + " by " + product.getAuthor().getFullName() + ".");
    model.put("dynamicMetaDescription1", "Book review of " + product.getName() + " by " + product.getAuthor().getFullName() + ".");
    model.put("dynamicMetaKeywords", product.getName() + " by " + product.getAuthor().getFullName() + ",");
    

    //get users who read this also read books
    List alsoReadList = productService.findBooksReadByOtherUsersWhoReadThisBook(3
                                                                                , GeneralUtils.getCurrentLoggedInUserFromTLSWithoutReload()
                                                                                , product); 
    model.put("alsoReadList", alsoReadList);
    
    return model;
  }  

}
