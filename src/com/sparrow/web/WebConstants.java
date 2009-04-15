package com.sparrow.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.sparrow.domain.Author;
import com.sparrow.domain.Product;
import com.sparrow.service.util.GeneralUtils;

/**
 * Web tier constants plus some utility methods.
 * @author manishk
 * @since 1.0
 */
public class WebConstants {
  /**
   * Name of image to be displayed when no Product image is found.
   */
  public static final String NO_PRODUCT_IMAGE = "noProductImage.gif";
  
  public static final String CATEGORY_LIST = "categoryList";
  
  //constant used to manage the queue
  public static final String MOVE_TO_HEAD = "first";
  public static final String MOVE_ONE_UP = "oneup";
  public static final String MOVE_ONE_DOWN = "onedown";
  public static final String MOVE_TO_TAIL = "last";
  public static final String REMOVE_FROM_QUEUE = "delete";
  
  public static final String IST_TIME_ZONE = "Asia/Calcutta";
  
  public static final String CC_PAYMENT_SUCCESS_MSG="Payment successfully processed";
  public static final String AMEX_CC_PAYMENT_SUCCESS_MSG="Batch payment sent for processing";
  
  public static final String CC_PAYMENT_FAILURE_MSG="Payment processing failed";
  
  /**
   * This is the number of characters that will be displayed in product descirption
   */
  public static final int DEFAULT_COUNT_OF_CHARS_IN_DESCRIPTION = 200;
  
  
  /**
   * Merchant ID assigned by CC Avenue...not to be changed.
   */
  public static final String CCAVENUE_MERACHANT_ID = "M_fobpg_8773";
  
  /*
   * Working Key assigned by CC Avenue...not to be changed unless new key is regerated
   */
  public static final String CCAVENUE_WORKING_KEY = "v76kp99ly6b2hyw4ue";
  
  /**
   * This is the url from which control will come back to from the payment gateway
   */
  public static final String CCAVENUE_REDIRECT_URL = "http://www.friendsofbooks.com/ccpaymentstatus.htm";
  
  /**
   * URL to forward to CC Avenue for payment processing
   */
  public static final String CCAVENUE_FORWARD_URL = "https://www.ccavenue.com/shopzone/cc_details.jsp";
  
  
  //search by values
  public static final String SEARCH_BY_LASTNAME = "lastname";
  public static final String SEARCH_BY_USERID = "userid";
  public static final String SEARCH_BY_USERNAME = "username";
  public static final String SEARCH_BY_PHONE = "phone";
  public static final String SEARCH_BY_MOBILEPHONE = "mobilePhone";

  public static String getProductDetailsPageURL(HttpServletRequest request, HttpServletResponse response, Product product) {
    StringBuffer url = new StringBuffer();
    String keywords = null;
    keywords = GeneralUtils.getKeywordsFromString(product.getName() + " " + product.getAuthor().getFullName());
    url.append(request.getContextPath());
    url.append("/book/");
    url.append(keywords);
    url.append("/" + product.getProductId());
    
    return response.encodeURL(url.toString());
  }
  
  /**
   * Returns the productPage url withing the prepended context path like /sparrow. Useful when called from controller since spring web will 
   * add context path while forwarding.
   * 
   * @param request
   * @param response
   * @param product
   * @return
   * @since 1.0
   */
  public static String getProductDetailsPageURLWithoutContext(HttpServletRequest request, HttpServletResponse response, Product product) {
    String url = getProductDetailsPageURL(request, response, product);
    String contextPath = request.getContextPath();
    return StringUtils.removeStart(url, contextPath);
  }

  /**
   * Returns the author page url with prepended context path like /sparrow. Useful when called from controller since spring web will 
   * add context path while forwarding. This one encodes URL 
   * @param request
   * @param author
   * @return
   * @since 1.0
   */
  public static String getAuthorPageURL(HttpServletRequest request, HttpServletResponse response, Author author) {
     return response.encodeURL(getAuthorPageURL(request, author));
  }
  
  /**
   * Returns the author page url with prepended context path like /sparrow. Useful when called from controller since spring web will 
   * add context path while forwarding. This one does not encode URL 
   * @param request
   * @param author
   * @return
   * @since 1.0
   */
  public static String getAuthorPageURL(HttpServletRequest request, Author author) {
    StringBuffer url = new StringBuffer();
    String keywords = null;
    keywords = GeneralUtils.getKeywordsFromString(author.getFullName());
    url.append(request.getContextPath());
    url.append("/author/");
    url.append(keywords);
    url.append("/" + author.getAuthorId());
    
    return url.toString();
  }
  
  public static String getBookTooltipHTML(Product featuredBook, String id_of_trigger_element, String id_of_tooltip_to_show_element) {
    String html = "<div id='" + id_of_tooltip_to_show_element + "' style=\"padding: 10px;\"> " +
                  "<div style=\"border: 1px; border-color: #FF9900; border-style: solid; text-align: left; background-color: white; \">" +
                  "<div style=\"background-color: #FF9900; padding: 5px;width: 250px; font-weight: bold;\">" + featuredBook.getName() +
                  "<br>by <i>" + featuredBook.getAuthor().getFullName() + "</i></div>" +
                  "  <div style=\"padding: 5px; width: 250px;\">   " + featuredBook.getDescription()  +
                  "  </div>  </div></div><script type=\"text/javascript\">  var my_tooltip = new Tooltip('" + id_of_trigger_element+  "', '" + id_of_tooltip_to_show_element + "') </script>";

    return html;
  }
  
}
