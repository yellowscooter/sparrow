package com.sparrow.service.catalog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sparrow.dao.catalog.HomeProductDao;
import com.sparrow.domain.HomeProduct;
import com.sparrow.domain.Product;

public class HomeProductServiceImpl implements HomeProductService {
  private HomeProductDao homeProductDao;
  private ProductService productService;
  
  public HomeProductDao getHomeProductDao() {
    return homeProductDao;
  }
  public void setHomeProductDao(HomeProductDao homeProductDao) {
    this.homeProductDao = homeProductDao;
  }
  
  public ProductService getProductService() {
    return productService;
  }
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getAllHomeProducts()
   */
  public List getAllHomeProducts() {
    return (List)homeProductDao.getAllHomeProducts();
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductListForHomePage()
   */
  public List getProductListForHomePage() {
    List productList = new ArrayList();
    List homeProductList = this.getAllHomeProducts();
    Iterator itr = homeProductList.iterator();
    while (itr.hasNext()) {
      HomeProduct homeProduct = (HomeProduct) itr.next();
      productList.add(homeProduct.getProduct());
    }

    return productList;
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.HomeProductService#addProductToHomeProductList(int)
   */
  public void addProductToHomeProductList(int productId) {
    //first get the product
    Product product = productService.getProductById(productId);
    List productList = this.getProductListForHomePage();
    //only if the product is not in the list do we add it again
    if (product != null && !productList.contains(product)) {
      int listSize = productList.size();
      HomeProduct homeProduct = new HomeProduct(product, listSize);
      homeProductDao.saveHomeProduct(homeProduct);
    }
  }
  
  public void deleteProductFromHomeProductList(int productId) {
    HomeProduct homeProduct = homeProductDao.getHomeProductById(productId);
    homeProductDao.deleteHomeProduct(homeProduct);
    
    
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.HomeProductService#moveHomeProductOneUp(int)
   */
  public void moveHomeProductOneUp(int index) {
    List homeProductList = this.getAllHomeProducts();
    if (index > 0) {
      HomeProduct homeProduct = (HomeProduct)homeProductList.remove(index);
      homeProductList.add(index - 1, homeProduct);
    }
    saveHomeProductListMaintainOrder(homeProductList);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.HomeProductService#moveHomeProductOneDown(int)
   */
  public void moveHomeProductOneDown(int index) {
    List homeProductList = this.getAllHomeProducts();
    if (index < homeProductList.size() - 1) {
      HomeProduct homeProduct = (HomeProduct)homeProductList.remove(index);
      homeProductList.add(index + 1, homeProduct);
    }
    saveHomeProductListMaintainOrder(homeProductList);
  }
  
  private void saveHomeProductListMaintainOrder(List homeProductList) {
    Iterator itr = homeProductList.iterator();
    while (itr.hasNext()) {
      HomeProduct homeProduct = (HomeProduct)itr.next();
      homeProductDao.deleteHomeProduct(homeProduct);
    }
    
    itr = homeProductList.iterator();
    int i = 0;
    while (itr.hasNext()) {
      HomeProduct homeProduct = (HomeProduct)itr.next();
      homeProduct.setPriority(i);
      i++;
      homeProductDao.saveHomeProduct(homeProduct);
    }
  }

  
}
