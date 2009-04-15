package com.sparrow.service.deliveryrequest;

import java.beans.PropertyEditorSupport;

import com.sparrow.domain.Author;
import com.sparrow.domain.DeliveryRequest;

/**
 * Sets a {@link DeliveryRequest} for deliveryRequest Id
 * @author manishk
 * @since 1.0
 */
public class DeliveryRequestEditor extends PropertyEditorSupport {
  private DeliveryRequestService deliveryRequestService;
  
  public DeliveryRequestEditor(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  
  /**
   * Sets the author as null if passed Id is null or -1 (In the view, this value can be used to
   * create a dummy "Please Select" option in the dropdown.) 
   */
  public void setAsText(String deliveryRequestId) throws IllegalArgumentException {
    if (deliveryRequestId == null || "-1".equals(deliveryRequestId)) {
      setValue(null);
    } else {
      DeliveryRequest deliveryRequest = (DeliveryRequest)deliveryRequestService.getDeliveryRequestById(Integer.parseInt(deliveryRequestId));
      setValue(deliveryRequest);
    }
  }
  
  public String getAsText() {
    DeliveryRequest value = (DeliveryRequest)getValue();
    return (value != null ? String.valueOf(value.getDeliveryRequestId()) : "");
  }


}
