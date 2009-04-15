package com.sparrow.web.queue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.domain.Product;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.ProductRequestAlreadyExistsException;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

/**
 * Controller to add ProductRequest to queue.
 * @author manishk
 * @since 1.0
 */
public class AddToQueueController extends AbstractController {
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

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
    //setting response characteristics so its not cached.
    response.setContentType("text/plain;charset=utf-8");  
    
    response.setHeader("Cache-Control", "no-cache");  
    response.setDateHeader("Expires", 0);  
    response.setHeader("Pragma", "no-cache");  
    
    //ModelAndView modelAndView = new ModelAndView("forward:browseCatalog.htm");
    ModelAndView modelAndView = new ModelAndView("public/catalog/ajaxResponse");
    Map model = new HashMap();
    String message = "<div style=\"padding-top: 5px; padding-bottom: 5px;\"><img src=\"" + request.getContextPath()+ "/images/inQ.gif\" alt=\"Already In Queue\" title=\"Already In Queue\" ></div>";
    
    User currentUser = GeneralUtils.getCurrentUserFromTLS();
    
    int productId = ServletRequestUtils.getIntParameter(request, "productId");
    Product product = productService.getProductById(productId);
    
    try {
      currentUser.addProductRequest(product);  
    } catch (ProductRequestAlreadyExistsException pe) {
      //Product Request is already in queue. No need to save the queue to the database
      //just return without save
      //put the In Queue message
      model.put("message", message);
      modelAndView.addAllObjects(model);
      return modelAndView;
    }
    
    //put the message
    model.put("message", message);

    //save the updated queue to the database
    //userService.saveProductRequestListForUser(currentUser);
    userService.saveUser(currentUser);
    
    modelAndView.addAllObjects(model);
    return modelAndView;
  }

}
