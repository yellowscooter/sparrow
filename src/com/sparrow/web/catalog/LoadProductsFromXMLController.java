package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.service.catalog.ProductService;

public class LoadProductsFromXMLController extends AbstractController {
  private ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String upload = ServletRequestUtils.getStringParameter(request, "upload");
    
    
    if ("true".equals(upload)) {
      productService.saveProductsFromXML();
      Map model = new HashMap();
      model.put("message", "Products loaded successfully.");
      return new ModelAndView("/admin/success", model);
    }
    // TODO Auto-generated method stub
    return new ModelAndView("/admin/catalog/loadProductsFromXML");
  }

}
