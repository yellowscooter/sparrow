package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Product;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.ProductService;

/**
 * Controller to handle display of Product activation and activation.
 * @author manishk
 * @since 1.0
 */
public class ProductActivationController extends SimpleFormController {
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

  /**
   * Fetch the list of all Books and set as reference data.
   */
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    int productId = ServletRequestUtils.getIntParameter(request, "productId");
    Product product = productService.getProductById(productId);
    
    refData.put("product", product);
    
    return new ModelMap("refData", refData);
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
   ModelAndView modelAndView = new ModelAndView("/admin/catalog/activateProduct");
    int productId = ServletRequestUtils.getIntParameter(request, "productId");
    String submitAction = ServletRequestUtils.getStringParameter(request, "submitAction");
    
    if (submitAction == null) {
      Product product = productService.getProductById(productId);
      //by default, the product will not have a list of categories...get the categories
      List categories = (List)categoryService.getCategoriesByProduct(product);
      Set categorySet = new HashSet(categories);
      product.setCategories(categorySet);
      modelAndView.addObject("product", product);
    } else if ("activate".equals(submitAction)) {
      try {
        productService.activateProduct(productId);  
      } catch (ProductNotAssignedToCategoryException pne) {
        modelAndView.addObject("message", pne.getMessage());
        modelAndView.setViewName(getSuccessView());
        return modelAndView;
      }
      
      modelAndView.addObject("message", "Product activate successfully");    
      modelAndView.setViewName(getSuccessView());
    }
    
    // TODO Auto-generated method stub
    return modelAndView;
  }

  
  
  

  

}
