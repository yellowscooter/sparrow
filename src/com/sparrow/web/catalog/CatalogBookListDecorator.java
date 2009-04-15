package com.sparrow.web.catalog;

import java.awt.PageAttributes;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.TableDecorator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sparrow.domain.Book;
import com.sparrow.domain.Category;
import com.sparrow.domain.Product;
import com.sparrow.domain.User;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.web.WebConstants;

/**
 * Decorator for the ProductList. This class is used by the 
 * displayTag to render decorated data.
 * 
 * @author manishk
 * @since 1.0
 */
public class CatalogBookListDecorator extends TableDecorator {
  
  
  public String getDisplay() {
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getPageContext().getServletContext());
    CategoryService categoryService = (CategoryService)ctx.getBean("categoryService");
    HttpServletRequest request = (HttpServletRequest)getPageContext().getRequest();
    Book book = (Book)getCurrentRowObject();
    String url = WebConstants.getProductDetailsPageURL(request
                                                        ,(HttpServletResponse)getPageContext().getResponse()
                                                        , book);
    String authorUrl = WebConstants.getAuthorPageURL(request
                                                     ,(HttpServletResponse)getPageContext().getResponse()
                                                     , book.getAuthor());
    //value of true indicates we are displaying the Author page
    String displayAuthorName = (String) request.getAttribute("displayAuthorName");

    StringBuffer display = new StringBuffer("<table  border='0'><tr>");
    //index starts from 0...so add 1 for display
    //display.append("<td valign=\"top\" width=\"5\" align=\"center\" >" + (getListIndex() + 1) + ".</td>");
    display.append("<td valign=\"top\" width=\"5\" align=\"center\" ></td>");
    display.append("<td valign=\"top\" width=\"60\" align=\"center\">");
    if (book.getImageSmallName() != null) {
      String altText = book.getName() + " by " + book.getAuthor().getFullName();
      display.append("<a href=\"" + url + "\"><img alt='" + altText + "' " +
                                              " title='" + altText + "' " +
                                              " src='" + request.getContextPath() +  "/images/productImages/" + book.getImageSmallName() + "' /></a>");  
    } else {
      display.append("<img src='" + request.getContextPath() + "/images/productImages/" + WebConstants.NO_PRODUCT_IMAGE + "' />");
    }
    display.append("</td>");
    display.append("<td valign=\"top\" width=\"455\" align=\"left\">");
    
    //database does allow books wihtout author information...so null check here
    display.append("<b><a  href=\"" + url + "\">" + book.getName()+ "</a>");
    //if true, i.e. displaying author page, do not make authorname a link to the author page.
    //this will lead to cyclic links and cause issues with search engine opt
    if ("true".equals(displayAuthorName)) {
      display.append("<i>" + (book.getAuthor() != null ? " by " + book.getAuthor().getFullName() : "") + "</i></b><br/>");
    } else {
      display.append("<i>" + (book.getAuthor() != null ? " by <a  href=\"" + authorUrl + "\">" + book.getAuthor().getFullName() + "</a>" : "") + "</i></b><br/>");
    }
    display.append(decoratedDescription(book) + "<br>");
    display.append("<b>Pages: </b>" + book.getNumOfPages()+ "<br>");
    //display.append("<b>Format: </b>" + book.getFormat() + "<br><br>");
    List awardsList = (List)categoryService.getAwardCategoriesForProduct(book);
    Iterator itr = awardsList.iterator();
    if (itr.hasNext()) {
      display.append("<b>Accolades</b><br>");
    }
    while (itr.hasNext()) {
      Category category = (Category)itr.next();
      display.append(category.getDescription()+ "<br>");
    }
    display.append("<br>");
    
    //User currentUser = GeneralUtils.getCurrentUserFromTLS();
    //the current logged in user is put in the request by the jsp (see header.jsp)
    User currentUser = (User)request.getAttribute("currentUser");
    
    String queueStatus = null;
    if (currentUser != null && currentUser.isProductAlreadyInQueue(book)) {
      queueStatus = "<img src=\"" + request.getContextPath() + "/images/inQ.gif\" alt=\"In your Bookshelf\" title=\"In your Bookshelf\" >";
    } else {
      queueStatus = "<div class=\"addToQ\"><img src=\"" + request.getContextPath()+ "/images/addToQ.gif\" alt=\"Add To Bookshelf\" title=\"Add To Bookshelf\" onclick=\"addToQ('" + getListIndex() + "','" + request.getContextPath() + "/ajaxaddtoqueue.htm?productId=" + book.getProductId() + "');\"></div>";
    }
    
    //display.append("<b><a href=\"addtoqueue.htm?productId=" + book.getProductId() + "\">" + "Add to Queue >>" + "</a></b>");
    //display.append("<div id='item" + getListIndex() + "'><input type='button' value=\"Test\" onclick=\"getHTML2('" + getListIndex() + "','ajaxaddtoqueue.htm?productId=" + book.getProductId() + "');\" /></div>");
    //display.append("<b><div id='item" + getListIndex() + "'><table onclick=\"getHTML2('" + getListIndex() + "','ajaxaddtoqueue.htm?productId=" + book.getProductId() + "');\"><tr><td>Add to Queue>></td></tr></table></div></b>");
    display.append("<div class=\"buttonImage\" id='item" + getListIndex() + "'>" + queueStatus + "</div>");
     
    display.append("</td>");
    display.append("</tr>" + 
                   "<tr><td valign=\"top\" colspan=\"3\" align=\"left\" >...........................................................................................................................</td></tr>" +
                   "</table>");
      
      
    return display.toString();
  }
  
  private String decoratedDescription(Book book) {
    
    String description = book.getDescription();
    String decString = book.getDescription();
    
    if (description.length() > WebConstants.DEFAULT_COUNT_OF_CHARS_IN_DESCRIPTION) {
      //we are trying to find the index of first space character after DEFAULT_COUNT_OF_CHARS_IN_DESCRIPTION characters.
      //We will display characters upto this space index in the UI....and hide the rest of the characters (which will
      //be displayed when user clicks on more link
      int index = description.indexOf(' ', WebConstants.DEFAULT_COUNT_OF_CHARS_IN_DESCRIPTION);

      //If there is no space after DEFAULT_COUNT_OF_CHARS_IN_DESCRIPTION chars in description, indexOf will return -1
      //Set the index as length of descriptoin to display the entire description.
      if (index == -1) {
        index = description.length();
      }
      
      String str1 = description.substring(0, index);
      String str2 = description.substring(index);
      int id = book.getProductId();
      decString = str1 + "<span id='desc" + id + "' style=\"display:none;\">" + str2 + "</span>"
      + "<span id='more" + id + "'>&nbsp;<a href=\"javascript:toggleLayerInline('desc" + id + "');javascript:toggleLayerInline('more" + id + "');javascript:toggleLayerInline('hide" + id + "');\">Read More...</a></span>"
      + "<span id='hide" + id + "' style=\"display:none;\">&nbsp;<a href=\"javascript:toggleLayerInline('desc" + id + "');javascript:toggleLayerInline('more" + id + "');javascript:toggleLayerInline('hide" + id + "');\">Hide</a></span>";
    }
    return decString;
  }


}
