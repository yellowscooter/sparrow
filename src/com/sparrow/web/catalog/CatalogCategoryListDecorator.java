package com.sparrow.web.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.Category;

/**
 * Decorator for the CategoryList to be displayed in Product Catalog. 
 * This class is used by the displayTag to render decorated data.
 * 
 * @author manishk
 * @since 1.0
 */
public class CatalogCategoryListDecorator extends TableDecorator {

  public String getName() {
    HttpServletResponse httpServletResponse = (HttpServletResponse)getPageContext().getResponse();
    HttpServletRequest httpServletRequest = (HttpServletRequest)getPageContext().getRequest();
    
    Category category = (Category) getCurrentRowObject();
    String url = httpServletResponse.encodeURL(httpServletRequest.getContextPath() + "/browseCatalog.htm?categoryId=" + category.getCategoryId());

    String temp = "&nbsp;&nbsp;<a href=\"" + url + "\">" + category.getName() + "</a>"
        + "<br clear=\"all\">&nbsp;&nbsp;..............................";

    return temp;
  }
}
