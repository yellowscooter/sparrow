package com.sparrow.service.catalog;

import java.util.List;

import com.sparrow.domain.HomeProduct;
import com.sparrow.service.AbstractServiceTests;

/**
 * Junit tests for {@link HomeProductService}
 * @author manishk
 * @since 1.0
 */
public class HomeProductServiceTests extends AbstractServiceTests {
  private HomeProductService homeProductService;
  
  public HomeProductService getHomeProductService() {
    return homeProductService;
  }

  public void setHomeProductService(HomeProductService homeProductService) {
    this.homeProductService = homeProductService;
  }


  public void testGetAllHomeProducts() {
    int count = jdbcTemplate.queryForInt("select count(0) from home_product");
    List homeProductsList = homeProductService.getAllHomeProducts();
    assertEquals("The number of home products is incorrect", count,
        homeProductsList.size());
  }
  
  public void testAddProductToHomeProductList() {
    int count = jdbcTemplate.queryForInt("select count(0) from home_product");
    homeProductService.addProductToHomeProductList(1004);
    List homeProductsList = homeProductService.getAllHomeProducts();
    assertEquals("The size of Home Product list is incorrect", count + 1, homeProductsList.size());
    
    //this product already exists in the list...call should not throw an exception
    homeProductService.addProductToHomeProductList(1001);
  }
  
  public void testDeleteProductFromHomeProductList() {
    homeProductService.deleteProductFromHomeProductList(1001);
  }
  
  public void testMoveHomeProductOneUp() {
    homeProductService.moveHomeProductOneUp(1);
    List homeProductsList = homeProductService.getAllHomeProducts();
    HomeProduct homeProduct = (HomeProduct)homeProductsList.get(0);
    assertEquals("Correct prouct id not found", 1002, homeProduct.getProductId());
  }
  
  public void testMoveHomeProductOneDown() {
    homeProductService.moveHomeProductOneDown(1);
    List homeProductsList = homeProductService.getAllHomeProducts();
    HomeProduct homeProduct = (HomeProduct)homeProductsList.get(2);
    assertEquals("Correct prouct id not found", 1002, homeProduct.getProductId());
  }
  
  
}
