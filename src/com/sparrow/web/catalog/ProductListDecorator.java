package com.sparrow.web.catalog;

import java.util.Iterator;
import java.util.List;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.dao.catalog.ProductStatusEnum;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductRequest;
import com.sparrow.service.catalog.ProductInstanceStatusEnum;

/**
 * Decorator to display the Product details in Product Catalog.
 * 
 * @author manishk
 * @since 1.0
 */
public class ProductListDecorator extends TableDecorator {

  public String getLink1()
  {
      Product product = (Product)getCurrentRowObject();
      StringBuffer display = new StringBuffer(); 
      display.append("<input type='button' value='Edit' onClick=\"window.open('editbook.htm?productId=" + product.getProductId() + "', 'editProduct','width=850,height=675,toolbar=no,menubar=no,scrollbars=yes')\">" +
          "&nbsp;<input type='button' value='Add Instance' onClick=\"window.open('addproductinstance.htm?productId=" + product.getProductId() + "', 'addInstance','width=600,height=400,toolbar=no,menubar=no,scrollbars=yes')\">");  
      //if the product is not active, show the activate button.
      if (!product.isActive()) {
        display.append("&nbsp;<input type='button' value='Activate' onClick=\"window.open('activateproduct.htm?productId=" + product.getProductId() + "', 'activate','width=600,height=600,toolbar=no,menubar=no,scrollbars=yes')\">");
      }
      
      
      return display.toString();
//      return "<a href=\"editbook.htm?productId=" + product.getProductId() + "\">" + "Edit" + "</a>" +
//              "&nbsp;<a href=\"addproductinstance.htm?productId=" + product.getProductId() + "\">" + "Add Instance" + "</a>";
  }
  
  /**
   * Returns a string of available product instances for a product.
   * @return
   * @since 1.0
   */
  public String getProductInstances() {
    StringBuffer strInstances = new StringBuffer();
    Product product = (Product)getCurrentRowObject();
    List productInstances = product.getProductInstances();
    Iterator itr = productInstances.iterator();
    
    while (itr.hasNext()) {
      ProductInstance productInstance = (ProductInstance)itr.next();
      strInstances.append(productInstance.getProductInstanceId() + " - " + productInstance.getStatus() + " | ");
    }
    
    
    return strInstances.toString();
  }
  
}
