package com.sparrow.dao.catalog;

import java.util.Collection;

import com.sparrow.domain.HomeProduct;
import com.sparrow.domain.Product;

/**
 * DAO to maintain the list of Products to be displayed on the home page.
 * 
 * @author manishk
 * @since 1.0
 */
public interface HomeProductDao {
  
  /**
   * Get product Ids of {@link Product} s which are to be displayed on the home page. 
   * @return
   * @since 1.0
   */
  public Collection getAllHomeProducts();
  
  /**
   * Get a {@link HomeProduct} for a given Product.
   * @param productId
   * @return
   * @since 1.0
   */
  public HomeProduct getHomeProductById(int productId);
  
  /**
   * Add a product to the list of products designates as Home Products
   * @param homeProduct
   * @since 1.0
   */
  public void saveHomeProduct(HomeProduct homeProduct);
  
  /**
   * Delete a {@link HomeProduct}
   * @param homeProduct
   * @since 1.0
   */
  public void deleteHomeProduct(HomeProduct homeProduct);

}
