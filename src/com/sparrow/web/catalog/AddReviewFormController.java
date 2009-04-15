package com.sparrow.web.catalog;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Product;
import com.sparrow.domain.ProductReview;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.web.WebConstants;

public class AddReviewFormController extends AbstractController {
  private ProductService productService;

  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  

  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    int productId = ServletRequestUtils.getRequiredIntParameter(request, "productId");
    String reviewTitle = ServletRequestUtils.getStringParameter(request, "reviewTitle");
    String reviewerName = ServletRequestUtils.getStringParameter(request, "reviewerName");
    String review = ServletRequestUtils.getStringParameter(request, "review");
    Product product = productService.getProductById(productId);
    String url = WebConstants.getProductDetailsPageURLWithoutContext(request, response, product);
    
    //do some validations
    //if review is blank, no point going forward
    if (StringUtils.isBlank(review)) {
      return new ModelAndView("redirect:" + url);
    } else if (StringUtils.isBlank(reviewerName)) {
      
    }
    //we want to preserve new lines that were entered in the text area
    //replace Carriage Return Line Feed with <br>, the html line break
    review = review.replaceAll("\r\n", "<br>");
    
    ProductReview productReview = new ProductReview(productId, reviewerName, reviewTitle, review);
    
    productService.addProductReview(productReview);
    
    
    
    return new ModelAndView("redirect:" + url);
  }

}
