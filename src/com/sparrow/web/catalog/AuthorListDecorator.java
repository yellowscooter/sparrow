package com.sparrow.web.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.Author;
import com.sparrow.web.WebConstants;

public class AuthorListDecorator extends TableDecorator {
  
  public String getDisplay() {
    String display = null;
    Author author = (Author)getCurrentRowObject();
    String url = WebConstants.getAuthorPageURL((HttpServletRequest)getPageContext().getRequest()
                                                , (HttpServletResponse)getPageContext().getResponse()
                                                , author);
    //display = "<a href=\"browseCatalog.htm?author_id=" + author.getAuthorId() + "\">" + author.getLastName() + ", " + author.getFirstName()  + "</a>";
    display = "<a href=\"" + url + "\">" + author.getLastName() + ", " + author.getFirstName()  + "</a>";
    
    return display;
  }
}
