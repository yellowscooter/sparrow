package com.sparrow.web.queue;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.ProductRequest;
import com.sparrow.web.WebConstants;

public class QueueDecorator extends TableDecorator {
  
  /**
   * In the database and application, priority is 0 based. But for display purpose,
   * it should start from one. This methods adds 1 to all priority numbers for display purpose.
   * 
   * @return
   * @since 1.0
   */
  public int getPriority() {
    //ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
    //int priority = productRequest.getPriority();
    int priority = getListIndex();
    return priority + 1;
    
  }

  public String getActions()
  {
      //we want to keep tab=2 highlighted...so adding to the url below
      ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
      StringBuffer stringBuffer = new StringBuffer("<table><tr>");      
      stringBuffer.append("<td><a href=\"queue.htm?tab=2&action=" + WebConstants.MOVE_TO_HEAD + "" +
            "&priority=" + getListIndex() + "\"><img src='images/arrow_first.gif' border='0' alt='Move to first' title='Move to first'></a></td>");
      stringBuffer.append("<td><a href=\"queue.htm?tab=2&action=" + WebConstants.MOVE_ONE_UP + "" +
            "&priority=" + getListIndex() + "\"><img src='images/arrow_up.gif' border='0' alt='Move one up' title='Move one up'></a></td>");
      stringBuffer.append("<td><a href=\"queue.htm?tab=2&action=" + WebConstants.MOVE_ONE_DOWN +
            "&priority=" + getListIndex() + "\"><img src='images/arrow_down.gif' border='0' alt='Move one down' title='Move one down'></a></td>");
      stringBuffer.append("<td><a href=\"queue.htm?tab=2&action=" + WebConstants.MOVE_TO_TAIL + "" +
            "&priority=" + getListIndex() + "\"><img src='images/arrow_last.gif' border='0' alt='Move to last' title='Move to last'></a></td>");
      stringBuffer.append("<td><a href=\"queue.htm?tab=2&action=" + WebConstants.REMOVE_FROM_QUEUE + "" +
          "&priority=" + getListIndex() + "\"><img src='images/delete.gif' border='0' alt='Remove from Bookshelf' title='Remove from Bookshelf'></a></td>");
      stringBuffer.append("</tr></table>");
      return stringBuffer.toString();
  }
  
  
  public String getRemove()
  {
      ProductRequest productRequest = (ProductRequest)getCurrentRowObject();
      
      return "<a href=\"queue.htm?action=" + WebConstants.REMOVE_FROM_QUEUE + "" +
          "&priority=" + getListIndex() + "\">Delete</a>";
  }

}
