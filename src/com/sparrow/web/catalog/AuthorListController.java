package com.sparrow.web.catalog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.web.WebConstants;

public class AuthorListController extends SimpleFormController {
  private static final String AUTHOR_LIST = "authorList";
  private ProductService productService;
  private CategoryService categoryService;
  
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
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
    //call superclass method first...it will create a new form backing command object 
    //if this is a new request. We need command object for search.
    //If its a form submission, then default implementation will do nothing.
    ModelAndView modelAndView = super.handleRequestInternal(request, response);

    //set the category list if it is not in session...since the jsp displayes the categories
    HttpSession session = request.getSession();
    //  if the category list is not present in session, set it.
    if (session.getAttribute(WebConstants.CATEGORY_LIST) == null) {
      //categories were modified to be of various types...LEFTNAV, AWARD etc
      //List categoryList = (List)categoryService.getTopCategories();
      List categoryList = (List)categoryService.getLeftNavTopCategories();
      session.setAttribute(WebConstants.CATEGORY_LIST, categoryList);  
    }
    String criteria = ServletRequestUtils.getStringParameter(request, "criteria", "A");
    List authorList = productService.findAuthorByBeginningOfLastname(criteria);
    
    request.getSession().setAttribute(AUTHOR_LIST, authorList);
    
    //return new ModelAndView(this.getSuccessView());
    return modelAndView;
  }

}
