package com.sparrow.web.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.service.catalog.HomeProductService;
import com.sparrow.web.WebConstants;

public class HomeProductListMaintController extends AbstractController {
  private HomeProductService homeProductService;
  
  public HomeProductService getHomeProductService() {
    return homeProductService;
  }
  public void setHomeProductService(HomeProductService homeProductService) {
    this.homeProductService = homeProductService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    String action = ServletRequestUtils.getStringParameter(request, "action");
    int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
    int index = ServletRequestUtils.getIntParameter(request, "index", -1);
    if (action == null) {
      //do nothing for now
    } else if (action.equals("addToHomeProductList")) {
      homeProductService.addProductToHomeProductList(productId);
    } else if (action.equals(WebConstants.REMOVE_FROM_QUEUE)) {
      homeProductService.deleteProductFromHomeProductList(productId);
    } else if (action.equals(WebConstants.MOVE_ONE_UP)) {
      homeProductService.moveHomeProductOneUp(index);
    } else if (action.equals(WebConstants.MOVE_ONE_DOWN)) {
      homeProductService.moveHomeProductOneDown(index);
    }
    //displayTag will get the list from request
    request.setAttribute("adminHomeProductList", homeProductService.getProductListForHomePage());
    
    return new ModelAndView("admin/catalog/homeProductList");
  }

}
