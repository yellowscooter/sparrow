package com.sparrow.web.deliveryrequest;

import org.displaytag.decorator.TableDecorator;

import com.sparrow.domain.DeliveryRequest;

/**
 * Decorator for {@link DeliveryRequest} List
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestListDecorator extends TableDecorator {
  public String getLink() {
    String display = null;
    DeliveryRequest deliveryRequest = (DeliveryRequest)getCurrentRowObject();
    
    display = "<a href=\"processdeliveryrequest.htm?deliveryRequestId=" + deliveryRequest.getDeliveryRequestId() + "\">Process DR</a>";
    
    return display;
  }
}
