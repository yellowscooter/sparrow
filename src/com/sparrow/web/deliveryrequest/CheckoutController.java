package com.sparrow.web.deliveryrequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.dao.deliveryrequest.DeliveryRequestStatusEnum;
import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.CheckoutInfo;
import com.sparrow.domain.Company;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.AuthorEditor;
import com.sparrow.service.catalog.CategoryEditor;
import com.sparrow.service.catalog.CompanyEditor;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.deliveryrequest.DeliveryRequestEditor;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.deliveryrequest.MaximumAllowedCheckoutProductsExceeded;
import com.sparrow.service.deliveryrequest.ProductInstanceNotAvailableException;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;

/**
 * Controller class to checkout a book to a {@link User}. A {@link DeliveryRequest}
 * must be created before a book can be checked out and the {@link Book} should 
 * exist in the user {@link ProductRequest} list.
 * @author manishk
 * @since 1.0
 */
public class CheckoutController extends SimpleFormController {
  DeliveryRequestService deliveryRequestService;
  UserService userService;
  ProductService productService;
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  
//  /**
//   * Sets up a custom property editor for empty fields.
//   */
//  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
//    binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
//    binder.registerCustomEditor(CheckoutInfo.class, new DeliveryRequestEditor(deliveryRequestService));
//  }
  
  
  
  @Override
  protected Map referenceData(HttpServletRequest request) throws Exception {
    Map refData = new HashMap();
    int deliveryRequestId = ServletRequestUtils.getRequiredIntParameter(request, "deliveryRequestId");
    int productId = ServletRequestUtils.getRequiredIntParameter(request, "productId");

    // get the deliveryRequest object
    DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(deliveryRequestId);
    refData.put("deliveryRequest", deliveryRequest);

    //User user = userService.getUserById(deliveryRequest.getUserId());
    User user = deliveryRequest.getUser();
    refData.put("user", user);

    Product product = productService.getProductById(productId);

    refData.put("product", product);
    return refData;
  }
    
    

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    ModelMap model = new ModelMap();
    CheckoutInfo checkoutInfo = (CheckoutInfo)command;
    int instanceId = checkoutInfo.getInstanceId();
    int deliveryRequestId = checkoutInfo.getDeliveryRequestId();
    //  get the deliveryRequest object
    DeliveryRequest deliveryRequest = deliveryRequestService.getDeliveryRequestById(deliveryRequestId);
    //User deliveryRequestuser = userService.getUserById(deliveryRequest.getUserId());
    User deliveryRequestuser = deliveryRequest.getUser();
    int priority = checkoutInfo.getPriority();
    try {
      deliveryRequest = deliveryRequestService.checkoutProduct(instanceId, deliveryRequest, 
                                                                priority, deliveryRequestuser);
    } catch (ProductInstanceNotAvailableException e) {
      model.put("message", e.getMessage());
      model.put("deliveryRequestId", deliveryRequestId);
      return new ModelAndView("/admin/success", model);
    } catch (MaximumAllowedCheckoutProductsExceeded me) {
      model.put("message", me.getMessage());
      //model.put("deliveryRequestId", deliveryRequestId);
      return new ModelAndView("/admin/success", model);
    }
    
        
    if (deliveryRequest.getStatus().equals(DeliveryRequestStatusEnum.COMPLETE.getValue())) {
      //if the monthly limit of delivery requests is over for a user, and a delivery request has still
      //been processed for the user, then we should apply the delivery request against the next
      //membership month. adjustDRCompleteDateToNextMonth method call makes that adjustment
      if (deliveryRequestService.isUserExceededMonthlyDeliveryRequestLimitForPlan(deliveryRequestuser)) {
        deliveryRequestService.adjustDRCompleteDateToNextMonth(deliveryRequest);
      }
      
      model.put("message", "Delivery request in transit.");
      model.put("deliveryRequest", deliveryRequest);
      model.put("user", deliveryRequestuser);
      return new ModelAndView(getSuccessView(), model);
    } 
    
    //if all books for delivery request are not checked out,
    //go to checkout page
    model.put("deliveryRequestId", deliveryRequestId);
    return new ModelAndView("redirect:/privileged/admin/processdeliveryrequest.htm", model);
  }

  @Override
  protected Object formBackingObject(HttpServletRequest request) throws Exception {
    int deliveryRequestId = ServletRequestUtils.getRequiredIntParameter(request, "deliveryRequestId");
    int productId = ServletRequestUtils.getRequiredIntParameter(request, "productId");
    int priority = ServletRequestUtils.getRequiredIntParameter(request, "priority");
    
    CheckoutInfo checkoutInfo = new CheckoutInfo();
    checkoutInfo.setDeliveryRequestId(deliveryRequestId);
    checkoutInfo.setProductId(productId);
    checkoutInfo.setPriority(priority);
    
    return checkoutInfo;
  }


}
