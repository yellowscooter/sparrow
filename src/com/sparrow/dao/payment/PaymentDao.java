package com.sparrow.dao.payment;


import com.sparrow.domain.Payment;

public interface PaymentDao {
  
  /**
   * Save a new or update an existing payment.
   * 
   * @param payment
   * @return
   * @since 1.0
   */
  public Payment savePayment(Payment payment);
  
  /**
   * Get a payment by its Id
   * @param paymentId
   * @return
   * @since 1.0
   */
  public Payment getPaymentById(Integer paymentId);

}
