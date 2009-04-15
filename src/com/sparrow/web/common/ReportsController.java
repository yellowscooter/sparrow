package com.sparrow.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.PagedList;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.user.UserService;

public class ReportsController extends SimpleFormController {
  UserService userService;
  ProductService productService;
  
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

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView modelAndView = new ModelAndView("/admin/common/reports");
    String reportCode = ServletRequestUtils.getRequiredStringParameter(request, "reportCode");
    modelAndView.addObject("reportCode", reportCode);
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    
    if ("expiredAccounts".equals(reportCode)) {      
      PagedList list = userService.getUsersWithExpiredAccounts(page);
      modelAndView.addObject("expiredAccountsUserList", list);
    } else if ("booksMostInDemand".equals(reportCode)) {
      PagedList list = productService.findBooksMostInDemand(page);
      modelAndView.addObject("booksMostInDemandList", list);
    } else if ("userSearches".equals(reportCode)) {
      PagedList list = userService.userSearch(page);
      modelAndView.addObject("userSearchesList", list);
    } else if ("userSearchesCount".equals(reportCode)) {
      PagedList list = userService.userSearchCount(page);
      modelAndView.addObject("userSearchesCountList", list);
    }
      
    return modelAndView;
  }
  
  

}
