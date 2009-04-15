package com.sparrow.service.test;

import com.sparrow.domain.TestOrder;

public interface HibernateTest {

  public TestOrder saveTestOrder(TestOrder order);
  
  public TestOrder getTestOrder(int orderId);

}