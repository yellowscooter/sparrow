package com.sparrow.service.bill;

/**
 * This exception is thorwn when the system detects that a user has multiple PEDNDING bills.
 * There should only be one pending bill at a given time.
 * 
 * @author Manish Kumar
 * @since 1.0
 */
public class MultiplePendingBillsException extends RuntimeException {

  public MultiplePendingBillsException() {
    super();
    // TODO Auto-generated constructor stub
  }

  public MultiplePendingBillsException(String arg0, Throwable arg1) {
    super(arg0, arg1);
    // TODO Auto-generated constructor stub
  }

  public MultiplePendingBillsException(String arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }

  public MultiplePendingBillsException(Throwable arg0) {
    super(arg0);
    // TODO Auto-generated constructor stub
  }
  

}
