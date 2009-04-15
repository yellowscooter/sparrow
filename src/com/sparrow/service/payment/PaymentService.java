package com.sparrow.service.payment;

import com.sparrow.domain.Payment;

/**
 * The Payment service.
 * 
 * @author manishk
 * @since 1.0
 */
public interface PaymentService {
  
  
  
  /**
   * Save new or update an existing payment.
   * 
   * @param payment
   * @return
   * @since 1.0
   */
  public Payment savePayment(Payment payment);
  
  /**
   * Update and verify Payment details.
   * @param payment
   * @return
   * @since 1.0
   */
  public Payment updateAndVerifyPaymentDetails(Payment payment);
  
  /**
   * Get a payment by its Id
   * @param paymentId
   * @return
   * @since 1.0
   */
  public Payment getPaymentById(int paymentId);
  

  

}
