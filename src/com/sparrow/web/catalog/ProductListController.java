package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.PagedList;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.ProductService;

/**
 * Controller to execute search and display the Product list.
 * @author manishk
 * @since 1.0
 */
public class ProductListController extends SimpleFormController {
  private static final String PRODUCT_LIST = "adminProductList";
  ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
  /**
   * Fetch the list of all Books and set as reference data.
   */
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    String criteria = ServletRequestUtils.getStringParameter(request, "criteria");
    SearchCriteria searchCriteria = new SearchCriteria(criteria);
    PagedList bookList = productService.findAllBooks(searchCriteria, page);
    productService.getProductInstances(bookList);
    refData.put("bookList", bookList);
    request.setAttribute(PRODUCT_LIST, bookList);
    
    return new ModelMap("refData", refData);
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    SearchCriteria criteria = (SearchCriteria)command; 
    //need to replace String with Criteria in this method call
    //List bookList = productService.findAllBooks(criteria);
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    PagedList bookList = productService.findAllBooks(criteria, page);
    productService.getProductInstances(bookList);
    request.setAttribute(PRODUCT_LIST, bookList);
    
    return new ModelAndView(getSuccessView(), errors.getModel());
  }
  
  
  
  

}
