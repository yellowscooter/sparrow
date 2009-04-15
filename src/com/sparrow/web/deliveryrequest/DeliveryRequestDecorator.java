package com.sparrow.web.deliveryrequest;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.ProductInstanceStatusEnum;
import com.sparrow.web.WebConstants;

/**
 * Decorator used to display User Bookshelf...its used in multiple jsp's to display the bookshelf.
 * @author Manish Kumar
 * @since 1.0
 */
public class DeliveryRequestDecorator extends TableDecorator {
  
  /**
   * In the database and application, priority is 0 based. But for display purpose,
   * it should start from one. This methods adds 1 to all priority numbers for display purpose.
   * 
   * @return
   * @since 1.0
   */
  public int getPriority() {
    //ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
    //int priority = productRequest.getPriority();
    int priority = getListIndex();
    return priority + 1;
    
  }
  
  /**
   * Returns a string of available product instances for a product.
   * @return
   * @since 1.0
   */
  public String getAvailableProductInstances() {
    StringBuffer strInstances = new StringBuffer();
    ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
    List productInstances = productRequest.getProduct().getProductInstances();
    Iterator itr = productInstances.iterator();
    
    while (itr.hasNext()) {
      ProductInstance productInstance = (ProductInstance)itr.next();
      if (productInstance.getStatus().equals(ProductInstanceStatusEnum.AVAILABLE.getValue())) {
        strInstances.append(productInstance.getProductInstanceId() + " - " + productInstance.getStatus() + " | ");
      }
      
    }
    
    
    return strInstances.toString();
  }
  
  public String getLink() {
    String display = null;
    PageContext pageContext = this.getPageContext();
    int priority = getListIndex();
    //get object from request
    DeliveryRequest deliveryRequest = (DeliveryRequest)pageContext.getRequest().getAttribute("deliveryRequest");
    
    ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
    display = "<a href=\"checkout.htm?deliveryRequestId=" + deliveryRequest.getDeliveryRequestId() + "&productId=" + productRequest.getProduct().getProductId()
                + "&priority=" + priority + "\">Checkout</a>";
    
    return display;
  }
  
 
}
