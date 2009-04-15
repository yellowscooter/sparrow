package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.Resource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.Company;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.AuthorEditor;
import com.sparrow.service.catalog.CategoryEditor;
import com.sparrow.service.catalog.CompanyEditor;
import com.sparrow.service.catalog.ProductEditor;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.catalog.UserEditor;
import com.sparrow.service.user.UserService;
//is incomplete
/**
 * Controller class to add Product instance.
 * @author manishk
 * @since 1.0
 */
public class AddProductInstanceFormController extends SimpleFormController {
  
  ProductService productService;
  UserService userService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * Sets up a custom property editor for empty fields.
   */
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
    binder.registerCustomEditor(User.class, new UserEditor(userService));
    binder.registerCustomEditor(Product.class, new ProductEditor(productService));
  }
  
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    Product prod = productService.getProductById(ServletRequestUtils.getRequiredIntParameter(request, "productId"));
    refData.put("product", prod);
    
    return new ModelMap("refData", refData);
  }
  
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    
    ProductInstance productInstance = (ProductInstance) command;
    productInstance = productService.saveProductInstance(productInstance);
    Map model = errors.getModel();
    model.put("message", "Product Instance Created. The new Product Instance Id is: *** " + productInstance.getProductInstanceId() 
              + " ***. Please note this instance id and mark it on the product you have just added. This is the unique identifier " +
                  "of the product in the system.");
    
    return new ModelAndView(getSuccessView(), model);
  }
  
  
  
  

}
