package com.sparrow.service.catalog;

import java.util.List;

import com.sparrow.domain.HomeProduct;
import com.sparrow.domain.Product;

/**
 * Service for maintenance of List of Products to be displayed on the home page.
 * @author manishk
 * @since 1.0
 */
public interface HomeProductService {
  /**
   * Get a list of {@link Product} to be displayed on the home page
   * @return
   * @since 1.0
   */
  public List getProductListForHomePage();
  
  /**
   * Get a list of all {@link HomeProduct}
   * @return
   * @since 1.0
   */
  public List getAllHomeProducts();
  
  /**
   * Add a product with given Id to the home product list
   * @param productId
   * @since 1.0
   */
  public void addProductToHomeProductList(int productId);
  
  /**
   * Remove product with given product id from the home product list.
   * @param productId
   * @since 1.0
   */
  public void deleteProductFromHomeProductList(int productId);
  
  /**
   * Move {@link HomeProduct} one up in the list.
   * @param index
   * @since 1.0
   */
  public void moveHomeProductOneUp(int index);
  
  
  /**
   * Move {@link HomeProduct} on down in the list.
   * @param index
   * @since 1.0
   */
  public void moveHomeProductOneDown(int index);
  
  

}
