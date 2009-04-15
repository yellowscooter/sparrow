package com.sparrow.service.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sparrow.domain.Bill;
import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.EmailMessage;
import com.sparrow.domain.Payment;
import com.sparrow.domain.PaymentInfo;
import com.sparrow.domain.User;
import com.sparrow.service.common.DateUtils;

/**
 * Utility to send emails.
 * @author manishk
 * @since 1.0
 */
public class MailUtil {
  private static transient Log logger = LogFactory.getLog(MailUtil.class);
  private JavaMailSender mailSender;
  private VelocityEngine velocityEngine;
  private String adminEmailAddress;
  private String newRegistrationEmailAddress;  
  private String pickupEmailAddress;
  private String secondaryAdminEmailAddress;
  private String customerServiceEmailAddress;
  private ResourceBundleMessageSource messageSource;
  
  public ResourceBundleMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public void setMailSender(JavaMailSender mailSender) {
     this.mailSender = mailSender;
  }

  public void setVelocityEngine(VelocityEngine velocityEngine) {
     this.velocityEngine = velocityEngine;
  }
  
  public String getAdminEmailAddress() {
    return adminEmailAddress;
  }

  public void setAdminEmailAddress(String adminEmailAddress) {
    this.adminEmailAddress = adminEmailAddress;
  }
  
  public String getPickupEmailAddress() {
    return pickupEmailAddress;
  }

  public void setPickupEmailAddress(String pickupEmailAddress) {
    this.pickupEmailAddress = pickupEmailAddress;
  }

  public String getNewRegistrationEmailAddress() {
    return newRegistrationEmailAddress;
  }

  public void setNewRegistrationEmailAddress(String newRegistrationEmailAddress) {
    this.newRegistrationEmailAddress = newRegistrationEmailAddress;
  }

  public String getSecondaryAdminEmailAddress() {
    return secondaryAdminEmailAddress;
  }

  public void setSecondaryAdminEmailAddress(String secondaryAdminEmailAddress) {
    this.secondaryAdminEmailAddress = secondaryAdminEmailAddress;
  }

  public String getCustomerServiceEmailAddress() {
    return customerServiceEmailAddress;
  }

  public void setCustomerServiceEmailAddress(String customerServiceEmailAddress) {
    this.customerServiceEmailAddress = customerServiceEmailAddress;
  }

  //needs to be implemented
  public static void sendMail() {
    //does nothing for now...
  }
  
  /**
   * Sends out an email based on the velocity template passed. The model and template are dependent on each other and
   * must match.
   * @param fromEmailAddress usually the admin
   * @param senderTitle Name or title of sender...shows in Sender field
   * @param toEmailAddress 
   * @param ccEmailAddress
   * @param bccEmailAddress
   * @param subject the subject of the email
   * @param velocityTemplate the tempate to generate email contenet
   * @param model model to use in velocity template
   * @since 1.0
   */
  public void sendMail(String fromEmailAddress
                        , String senderTitle
                        , String toEmailAddress
                        , String ccEmailAddress
                        , String bccEmailAddress
                        , String subject
                        , String velocityTemplate
                        , Map model) {
    if (fromEmailAddress == null || toEmailAddress == null || velocityTemplate == null) {
      throw new IllegalArgumentException("fromEmailAddress, toEmailAddress, velocityTemplate cannot be null:"
          + fromEmailAddress + "," + toEmailAddress + "," + velocityTemplate);
    }
    
    String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplate, model);
    if (logger.isDebugEnabled()) {
      logger.debug("Mail message after merging with velocity template is: " + text);
    }
    sendMail(adminEmailAddress, senderTitle, toEmailAddress, ccEmailAddress, bccEmailAddress, subject, text);
    
  }
  
  /**
   * Sends out an email message based on given data. This method does not throw an exception if it occurs during email sending.
   * It logs the error and returns.
   * 
   * @param fromEmailAddress usually the admin
   * @param senderTitle Name or title of sender...shows in Sender field
   * @param toEmailAddress 
   * @param ccEmailAddress
   * @param bccEmailAddress
   * @param subject the subject of the email
   * @param messageText the content of the email
   * @since 1.0
   */
  public void sendMail(String fromEmailAddress, String senderTitle, String toEmailAddress, String ccEmailAddress,
      String bccEmailAddress, String subject, String messageText) {
    if (fromEmailAddress == null || toEmailAddress == null ) {
      throw new IllegalArgumentException("fromEmailAddress, toEmailAddress"
          + fromEmailAddress + "," + toEmailAddress);
    }
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
      message.setFrom(fromEmailAddress, senderTitle);
      message.setTo(toEmailAddress);
      if (ccEmailAddress != null) {
        message.setCc(ccEmailAddress);
      }
      if (bccEmailAddress != null) {
        message.setBcc(bccEmailAddress);
      }
      message.setSubject(subject);

      message.setText(messageText, true);
      this.mailSender.send(mimeMessage);
    } catch (MessagingException me) {
      // just log the message...do not do anything.
      logger.error("There has been an exception in sending out confirmation email to user:" + toEmailAddress, me);
    } catch (UnsupportedEncodingException e) {
      logger.error("There has been an exception in sending out confirmation email to user:" + toEmailAddress, e);
    } catch (Exception e) {
      logger.error("There has been an exception in sending out confirmation email to user:" + toEmailAddress, e);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Email sent out successfully to " + toEmailAddress);
    }
  }
  
  public void sendMail(EmailMessage emailMessage) {
    sendMail(emailMessage.getFromEmailId(), emailMessage.getSenderTitle(), emailMessage.getToEmailId()
            , emailMessage.getCcEmailId(), emailMessage.getBccEmailId(), emailMessage.getSubject(), emailMessage.getMessage());
  }
  
  /**
   * Sends an email to customer service. Reads the email address from properties file and sets the toEmailId. Also sends bcc email to secondary admin address.
   * 
   * @param emailMessage
   * @since 1.0
   */
  public void sendEmailToCustomerService(EmailMessage emailMessage) {
    emailMessage.setToEmailId(this.getCustomerServiceEmailAddress());
    emailMessage.setBccEmailId(this.getSecondaryAdminEmailAddress());
    sendMail(emailMessage);
  }
  
  /**
   * Send out confirmation email to User and also bcc admin
   * @param user
   * @since 1.0
   */
  public void sendUserRegistrationConfirmationEmail(final User user) {
    Map model = new HashMap();
    model.put("user", user);
    String subject = messageSource.getMessage("user.registration.subject", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    //bcc to newRegistrationEmailAddress
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, newRegistrationEmailAddress, subject, "registration-confirmation.vm", model);
  }

  public void sendEPaymentSuccessConfirmationEmail(User user, Payment payment) {
    Map model = new HashMap();
    model.put("user", user);
    model.put("payment", payment);
    String subject = messageSource.getMessage("user.epayment.success", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, adminEmailAddress, subject, "cc_payment_success.vm", model);
  }
  
  public void sendEPaymentFailureEmail(User user, Payment payment) {
    Map model = new HashMap();
    model.put("user", user);
    model.put("payment", payment);
    String subject = messageSource.getMessage("user.epayment.failure", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, adminEmailAddress, subject, "cc_payment_failure.vm", model);
  }
  
  public void sendDeliveryRequestConfirmationEmail(User user, DeliveryRequest deliveryRequest) {
    Map model = new HashMap();
    model.put("user", user);
    model.put("deliveryRequest", deliveryRequest);
    
    String subject = messageSource.getMessage("delivery.request.success.subject", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, pickupEmailAddress, subject, "delivery_request_confirmation.vm", model);
  }
  
  public void sendMailToAdmin(String subject, String messageText) {
    sendMail(adminEmailAddress, "SYSTEM", adminEmailAddress, secondaryAdminEmailAddress, null, subject, messageText);
  }
  
  public void sendUserBillCreationEmail(User user, Bill bill) {
    Map model = new HashMap();
    model.put("user", user);
    model.put("bill", bill);
    DateFormat formatter = DateUtils.getMediumDateformatter();
    String displayBillDate = formatter.format(bill.getBillDate());
    model.put("displayBillDate", displayBillDate);
    
    String subject = messageSource.getMessage("user.new.bill.subject", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, adminEmailAddress, subject, "new_bill.vm", model);
  }
  
  public void sendInactiveUserLoginEmail(User user) {
    sendMailToAdmin("Inactive user has logged in", "Inactive user userId=" + user.getUserId() + " Username=" + user.getUsername() + " has logged in.");
  }
  
  public void sendSuspendedUserLoginEmail(User user) {
    sendMailToAdmin("Suspended user has logged in", "Suspended user userId=" + user.getUserId() + " Username=" + user.getUsername() + " has logged in.");
  }
  
  public void sendManualPaymentEmail(User user, PaymentInfo paymentInfo) {
    Map model = new HashMap();
    model.put("user", user);
    model.put("paymentInfo", paymentInfo);
    
    String subject = messageSource.getMessage("user.manual.payment.subject", null, null);
    String senderTitle = messageSource.getMessage("admin.sender.title", null, null);
    sendMail(adminEmailAddress, senderTitle, user.getUsername(), null, adminEmailAddress, subject, "manual_payment.vm", model);
  }
  

  

}
