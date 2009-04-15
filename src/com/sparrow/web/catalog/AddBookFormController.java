package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.Company;
import com.sparrow.domain.Product;
import com.sparrow.service.catalog.AuthorEditor;
import com.sparrow.service.catalog.CategoryEditor;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.CompanyEditor;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.UserServiceImpl;

public class AddBookFormController extends SimpleFormController {
  private static final transient Log logger = LogFactory.getLog(AddBookFormController.class); 
  
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
   * Sets up a custom property editor for empty fields.
   */
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
    binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    binder.registerCustomEditor(Author.class, new AuthorEditor(productService));
    binder.registerCustomEditor(Company.class, new CompanyEditor(productService));
    binder.registerCustomEditor(Set.class, "categories", new CategoryEditor(categoryService));
  }
  
  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    List authors = productService.getAllAuthors();
    List companyList = productService.getAllCompanies();
    List categories = (List)categoryService.getTopCategories();
    refData.put("authorsList", authors);
    refData.put("companyList", companyList);
    refData.put("categories", categories);
    
    return new ModelMap("refData", refData);
  }


  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    Resource resource = ctx.getResource("");
    
    Product product = (Book) command;
    Product savedProduct = productService.saveProductWithImages(product, resource.getFile().getAbsolutePath());
    Map model = errors.getModel();
    model.put("message", "Product Created.");
    
    return new ModelAndView(getSuccessView(), model);
  }
  
  
  

}
