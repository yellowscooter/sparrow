package com.sparrow.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * All junit test cases should extend this class. It initializes the 
 * test harness by specifying the config locations.
 * @author manishk
 * @since 1.0
 */
public class AbstractServiceTests extends
    AbstractTransactionalDataSourceSpringContextTests {
  
  // specifies the Spring configuration to load for this test fixture
  protected String[] getConfigLocations() {
      return new String[] { "classpath:WEB-INF/applicationContext.xml"
                              ,"classpath:WEB-INF/dataAccessContext.xml"  };
  }

}
