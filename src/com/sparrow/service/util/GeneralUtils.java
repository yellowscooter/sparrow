package com.sparrow.service.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.zip.Adler32;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ISOLatin1AccentFilter;
import org.apache.lucene.analysis.LengthFilter;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.springframework.beans.BeansException;

import com.sparrow.dao.user.hibernate.UserDaoImpl;
import com.sparrow.domain.Bill;
import com.sparrow.domain.Payment;
import com.sparrow.domain.User;
import com.sparrow.domain.UserRole;
import com.sparrow.service.common.WebApplicationContextHolder;
import com.sparrow.service.user.UserService;

/**
 * General Utility Methods
 * @author manishk
 * @since 1.0
 */
public class GeneralUtils {
  
  private static final Log logger = LogFactory.getLog(GeneralUtils.class);
  
  private static Analyzer analyzer = new StandardAnalyzer();
  
  private static final int MAX_TOKENS = 5;
  
  private static final String BLANK_KEYWORDS_REPLACE = "a";
  
  private static final UserService userService = (UserService)WebApplicationContextHolder.getBean("userService");
  
  /**
   * Gets the currently authenticated {@link User} from {@link ThreadLocal}, and get the userId from there.
   * The userId is then used used to get the userById...so this method reloads the user each time.
   * This should fix the StaleObjectExceptions that are caused if you cache the User object and the user
   * changes the object in the meantime.
   * Returns null if {@link User} has not been authenticated.
   * @return
   * @since 1.0
   */
  public static User getCurrentUserFromTLS() {
    User currentUser = getCurrentLoggedInUserFromTLSWithoutReload();
    User refreshedCurrentUser = null; 
        
    if (currentUser != null) {
      refreshedCurrentUser = userService.getUserById(currentUser.getUserId());  
        
    }
    
    return refreshedCurrentUser;
  }
  
  /**
   * Returns the currently authenticated {@link User} from {@link ThreadLocal}...so this method can 
   * possibly return a stale instance.
   * Should be used only to check who is logged in. To make any updates, use getCurrentUserFromTLS
   * @return
   * @since 1.0
   */
  public static User getCurrentLoggedInUserFromTLSWithoutReload() {
    User currentUser = null;
    Object obj = null;
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication auth = securityContext.getAuthentication();
    if (auth != null) {
      obj = auth.getPrincipal();
      if (obj instanceof User) {
        currentUser = (User)obj;;
      }
    }
    return currentUser;
  }
  
  
  /**
   * Performs a user authentication and login using Acegi. This 
   * method can be used to programatically login a user.
   * 
   * @param username the user to be authenticated
   * @param password the password of the user
   * @param providerManager the Acegi provider manager
   * @param detail any detail about user making the request
   * @since 1.0
   */
  public static void performUserLogin(String username, String password, ProviderManager providerManager, String detail) {
    
    SecurityContext context = SecurityContextHolder.getContext();
    try{
      //GrantedAuthority[] grantedAuthorities = new GrantedAuthority[] {new GrantedAuthorityImpl(UserRole.ROLE_USER)};

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
      authentication.setDetails(detail);

      Authentication authenticatedAuth = providerManager.doAuthentication(authentication);
      context.setAuthentication(authenticatedAuth);
    } catch (AuthenticationException e){
      context.setAuthentication(null);
      logger.error("Login failed after registration. This should never happen: ", e);
      throw e;
    } catch(BeansException e){
      context.setAuthentication(null);
      logger.error("Could not get bean from web application context: ", e);
      throw e;
    }
  }
  
  public static String verifyChecksum(String MerchantId , String OrderId, String Amount, String AuthDesc, String WorkingKey, String CheckSum) throws Exception  {
    String str = MerchantId+"|"+OrderId+"|"+Amount+"|"+AuthDesc+"|"+WorkingKey;
    
    Adler32  adl = new Adler32();                 
    adl.update(str.getBytes());
    long adler = adl.getValue();

    String newChecksum = String.valueOf(adl.getValue());
    return (newChecksum.equals(CheckSum)) ? "true" : "false";
    
  }


  public static String getChecksum(String MerchantId, String OrderId, String Amount, String redirectUrl, String WorkingKey) throws Exception {
    String str = MerchantId + "|" + OrderId + "|" + Amount + "|" + redirectUrl + "|" + WorkingKey;

    Adler32  adl = new Adler32();
    adl.update(str.getBytes());
    return String.valueOf(adl.getValue());
  }
  
  /**
   * CCAVenue requeires a unique transactionId (orderId) to be sent along with each transaction.
   * Generating transactionId as a concatenation of billId-paymentId
   * 
   * @param bill
   * @param payment
   * @return
   * @since 1.0
   */
  public static String generateTransactionId(Bill bill, Payment payment) {
    String transactionId = bill.getBillId() + "-" + payment.getPaymentId();
    return transactionId;
  }
  
  
  public static int getBillIdFromTransactionId(String transactionId) {
    String tokens[] = transactionId.split("-");
    int billId = Integer.parseInt(tokens[0]);
    return billId;
  }
  
  public static int getPaymentIdFromTransactionId(String transactionId) {
    String tokens[] = transactionId.split("-");
    int paymentId = Integer.parseInt(tokens[1]);
    return paymentId;
  }
  
  public static String getKeywordsFromString(String initialString) {
    StringBuffer keywords = new StringBuffer();
    analyzer = new StandardAnalyzer(); // or any other analyzer
    TokenStream ts = analyzer.tokenStream(null, new StringReader(initialString));
    
    //A filter that replaces accented characters in the ISO Latin 1 character set (ISO-8859-1) by their unaccented equivalent. 
    ts = new ISOLatin1AccentFilter(ts);
    //add length filter so filters a not less than 3 chars
    ts = new LengthFilter(ts, 3, 30);
    
    try {
      Token t = ts.next();
      int i = 1;
      
      while (t!=null && (i <= MAX_TOKENS)) {
        //System.out.println(("token: "+t));
        keywords.append(t.term());
        i++;
        t = ts.next();
        //if next term is not null, add a hyphen
        if (t!=null && (i <= MAX_TOKENS))
          keywords.append("-");
      }  
    } catch (IOException e) {
      logger.error("Error in getting keywords" + e);
      throw new RuntimeException("Error in getting keywords" + e); 
    }
    
    
    String keywordsString = keywords.toString();
    
    if (StringUtils.isBlank(keywordsString)) {
      keywordsString = BLANK_KEYWORDS_REPLACE;
    }
    
    keywordsString  = StringUtils.remove(keywordsString, '\'');
    return keywordsString;
  }


}