package com.sparrow.web.catalog;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.Product;
import com.sparrow.web.WebConstants;

public class HomeProductListDecorator extends TableDecorator {
  public String getRemove()
  {
      Product product = (Product)getCurrentRowObject();
      
      return "<a href=\"homeproductlist.htm?action=" + WebConstants.REMOVE_FROM_QUEUE + "&productId=" + product.getProductId() + "\">Delete</a>" + 
      "&nbsp;<a href=\"homeproductlist.htm?action=" + WebConstants.MOVE_ONE_UP + "&index=" + getListIndex() + "\">Move One Up</a>" + 
      "&nbsp;<a href=\"homeproductlist.htm?action=" + WebConstants.MOVE_ONE_DOWN + "&index=" + getListIndex() + "\">Move One Down</a>";
  }

}
