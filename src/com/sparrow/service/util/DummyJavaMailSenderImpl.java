package com.sparrow.service.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * Dummy implementation class to be used for development when mailhost is not available.
 * @author manishk
 * @since 1.0
 */
public class DummyJavaMailSenderImpl extends JavaMailSenderImpl implements JavaMailSender {
  private String host;
  
  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void send(MimeMessage mimeMessage) throws MailException {
    // TODO Auto-generated method stub

  }

  public void send(MimeMessage[] mimeMessages) throws MailException {
    // TODO Auto-generated method stub

  }

  public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
    // TODO Auto-generated method stub

  }

  public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {
    // TODO Auto-generated method stub

  }

  public void send(SimpleMailMessage simpleMessage) throws MailException {
    // TODO Auto-generated method stub

  }

  public void send(SimpleMailMessage[] simpleMessages) throws MailException {
    // TODO Auto-generated method stub

  }

}
