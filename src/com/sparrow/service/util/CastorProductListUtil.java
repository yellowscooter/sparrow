package com.sparrow.service.util;

import java.io.FileReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

import com.sparrow.domain.BookInfo;


/**
 * Utility classs to get a list of Product from an xml file
 * using Castor.
 * @author manishk
 * @since 1.0
 */
public class CastorProductListUtil {
  private static final transient Log logger = LogFactory.getLog(CastorProductListUtil.class);
  
  private String appTempDir;
  private String productFileName;
  
  public String getAppTempDir() {
    return appTempDir;
  }

  public void setAppTempDir(String appTempDir) {
    this.appTempDir = appTempDir;
  }

  public String getProductFileName() {
    return productFileName;
  }

  public void setProductFileName(String productFileName) {
    this.productFileName = productFileName;
  }

  /**
   * Fetches a list of Products from xml file. (Note: The format is fixed) 
   * 
   * @return
   * @since 1.0
   */
  public List getProductList() {
    Mapping mapping = new Mapping();
    String mappingFileName = "/com/sparrow/domain/castor-product-collectorz-mapping.xml";
    List productList = null;

    try {
      // 1. Load the mapping information from classpath
      URL url = this.getClass().getResource(mappingFileName);
      
      mapping.loadMapping(url);

      // 2. Unmarshal the data
      Unmarshaller unmar = new Unmarshaller(mapping);
      unmar.setIgnoreExtraElements(true);
      unmar.setIgnoreExtraAttributes(true);
      unmar.setValidation(false);
      
      
      //BookInfo bookInfo = (BookInfo) unmar.unmarshal(new InputSource(new FileReader("C:/UserData/Personal/tempImages/test.xml")));
      BookInfo bookInfo = (BookInfo) unmar.unmarshal(new InputSource(new FileReader(getAppTempDir() + getProductFileName())));
      
      productList = bookInfo.getProductList();
      if (logger.isDebugEnabled()) {
        logger.debug("Number of Products found in the xml file: " + productList.size());
      }
    } catch (Exception e) {
      logger.error(e);
      throw new RuntimeException(e);
    }
    
    
    return productList;
  }
}
