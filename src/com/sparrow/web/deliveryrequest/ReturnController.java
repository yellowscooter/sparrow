package com.sparrow.web.deliveryrequest;

import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ReturnInfo;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.deliveryrequest.ReturnValidator;

/**
 * Controller class to handle {@link ProductInstance} returns.
 * @author manishk
 * @since 1.0
 */
public class ReturnController extends AbstractWizardFormController {
  private DeliveryRequestService deliveryRequestService;
  private ProductService productService;
  
  public ReturnController() {
    setCommandName("returnInfo");
    setCommandClass(ReturnInfo.class);
    setPages(new String[] {"admin/deliveryrequest/return", "admin/deliveryrequest/returnConfirm"});
  }
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Override
  protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
    ReturnInfo returnInfo = (ReturnInfo)command;
    Map model = new HashMap();
    if (page == 1) {
      ProductInstance productInstance = productService.getProductInstanceById(returnInfo.getProductInstanceId());
      Product product = productInstance.getProduct();
      model.put("product", product);
      //model.put("user", productInstance.getUserProduct().getUserByUserId());
    }
    return model;
  }

  @Override
  protected void validatePage(Object command, Errors errors, int page) {
    ReturnInfo returnInfo = (ReturnInfo)command;
    ReturnValidator validator = (ReturnValidator) getValidator();
    validator.validate(command, errors);
  }
  
  

  @Override
  protected int getTargetPage(HttpServletRequest request, Object command, Errors errors, int currentPage) {
    int targetPage = 0;
    if (currentPage == 0) {
      targetPage = 1;
    }
    return targetPage;
  }

  @Override
  protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    ModelMap model = new ModelMap();
    ReturnInfo returnInfo = (ReturnInfo)command;
    deliveryRequestService.returnProduct(returnInfo.getProductInstanceId(), returnInfo.getComment());
    model.put("message", "Product Instance returned successfully.");  
    return new ModelAndView("admin/success", model);
  }

  @Override
  protected ModelAndView processCancel(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    return new ModelAndView("forward:/privileged/admin/return.htm");
  }
  
  
  
  

//  @Override
//  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
//    ModelMap model = new ModelMap();
//    ReturnInfo returnInfo = (ReturnInfo)command;
//    deliveryRequestService.returnProduct(returnInfo.getProductInstanceId(), returnInfo.getComment());
//    model.put("message", "Product Instance returned successfully.");  
//    return new ModelAndView(getSuccessView(), model);
//  }
}
