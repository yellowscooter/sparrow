package com.sparrow.web.common;

import java.util.Iterator;
import java.util.List;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.BooksMostInDemandReportVO;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.User;

public class ReportsDecorator extends TableDecorator {
  
  public String getDetailsLink() {
    User user = (User)getCurrentRowObject();
    StringBuffer display = new StringBuffer(); 
    display.append("<input type='button' value='Details' onClick=\"window.open('userdetails.htm?userId=" + user.getUserId() + "', 'userDetails','width=950,height=675,toolbar=no,menubar=no,scrollbars=yes')\">");  
    
    return display.toString();
  }
  
  /**
   * Returns a string of available product instances for a product.
   * @return
   * @since 1.0
   */
  public String getProductInstances() {
    StringBuffer strInstances = new StringBuffer();
    BooksMostInDemandReportVO booksMostInDemandReportVO = (BooksMostInDemandReportVO)getCurrentRowObject();
    Product product = booksMostInDemandReportVO.getProduct();
    List productInstances = product.getProductInstances();
    Iterator itr = productInstances.iterator();
    
    while (itr.hasNext()) {
      ProductInstance productInstance = (ProductInstance)itr.next();
      strInstances.append(productInstance.getProductInstanceId() + " - " + productInstance.getStatus() + " | ");
    }
    
    
    return strInstances.toString();
  }
  
  public String getProductInstancesCount() {
    BooksMostInDemandReportVO booksMostInDemandReportVO = (BooksMostInDemandReportVO)getCurrentRowObject();
    Product product = booksMostInDemandReportVO.getProduct();
    List productInstances = product.getProductInstances();
    
    return String.valueOf(productInstances.size());
  }
  
  public String getProductInstancesCountRatio() {
    BooksMostInDemandReportVO booksMostInDemandReportVO = (BooksMostInDemandReportVO)getCurrentRowObject();
    Product product = booksMostInDemandReportVO.getProduct();
    List productInstances = product.getProductInstances();
    int numOfInstances = productInstances.size();
    int requiredByAvailableRatio = 0;
    if (numOfInstances != 0) {
      requiredByAvailableRatio = booksMostInDemandReportVO.getCount() / numOfInstances;  
    }
    
    return String.valueOf(requiredByAvailableRatio);
  }

}
