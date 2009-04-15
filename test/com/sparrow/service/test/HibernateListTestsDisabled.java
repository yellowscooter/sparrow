package com.sparrow.service.test;

import com.sparrow.domain.Product;
import com.sparrow.domain.TestLineItem;
import com.sparrow.domain.TestOrder;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.catalog.ProductService;

/**
 * This is just a test class for test services to do prototyping.
 * 
 * not a real service test
 * @author manishk
 * @since 1.0
 */
public class HibernateListTestsDisabled extends AbstractServiceTests {
  HibernateTest hibernateTestService;
  ProductService productService;
  
  public HibernateTest getHibernateTestService() {
    return hibernateTestService;
  }

  public void setHibernateTestService(HibernateTest hibernateTestService) {
    this.hibernateTestService = hibernateTestService;
  }
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  public void testSaveList () {
    TestOrder order = new TestOrder();
    order.setOrderDesc("testOrder");
    TestLineItem lineItem = new TestLineItem();
    lineItem.setLineItemDesc("test line item1");
    order.getLineItems().add(lineItem);
    lineItem = new TestLineItem();
    lineItem.setLineItemDesc("test line item2");
    order.getLineItems().add(lineItem);
    
    lineItem = new TestLineItem();
    lineItem.setLineItemDesc("test line item3");
    order.getLineItems().add(lineItem);
    
    
    hibernateTestService.saveTestOrder(order);
    order.getLineItems().remove(1);
    hibernateTestService.saveTestOrder(order);
    
    setComplete();
  }
  
  public void testSaveListWithProduct() {
    Product product = productService.getProductById(1000);
    TestOrder order = new TestOrder();
    order.setOrderDesc("testOrder");
    
    TestLineItem lineItem = new TestLineItem();
    lineItem.setLineItemDesc("test line item1");
    lineItem.setProduct(product);
    
    order.getLineItems().add(lineItem);
    
    
    lineItem = new TestLineItem();
    lineItem.setLineItemDesc("test line item2");
    lineItem.setProduct(product);
    
    order.getLineItems().add(lineItem);
    
    hibernateTestService.saveTestOrder(order);
    setComplete();
    
  }
  
//  public void testLoadTestOrder() {
//    Product product = productService.getProductById(1000);
//    //11 is a hardocded value...will fail if not in database
//    TestOrder order = hibernateTestService.getTestOrder(11);
//    assertEquals(3, order.getLineItems().size());
//    
//    TestLineItem lineItem = new TestLineItem();
//    lineItem.setLineItemDesc("test line item3");
//    lineItem.setProduct(product);
//    
//    order.getLineItems().add(lineItem);
//    
//    hibernateTestService.saveTestOrder(order);
//    setComplete();
//    
//  }
  
}
