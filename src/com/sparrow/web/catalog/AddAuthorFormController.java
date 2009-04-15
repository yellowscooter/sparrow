package com.sparrow.web.catalog;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Author;
import com.sparrow.service.catalog.ProductService;

public class AddAuthorFormController extends SimpleFormController {
  private ProductService productService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    Author author = (Author) command;
    productService.saveAuthor(author);
    Map model = errors.getModel();
    model.put("message", "Author Created.");
    
    return new ModelAndView(getSuccessView(), model);
  }
}
