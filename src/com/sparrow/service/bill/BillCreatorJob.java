package com.sparrow.service.bill;

import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sparrow.domain.Bill;
import com.sparrow.domain.User;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.MailUtil;

/**
 * Quartz job to create bills for user who have expired accounts
 * @author Manish Kumar
 * @since 1.0
 */
public class BillCreatorJob {
  
  private UserService userService;
  private BillService billService;
  private MailUtil mailUtil;

  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  public BillService getBillService() {
    return billService;
  }

  public void setBillService(BillService billService) {
    this.billService = billService;
  }
  
  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  /**
   * Creates bills for Users who have expired accounts but do not have a pending bill.
   * 
   * @since 1.0
   */
  public void createBills() {
    List usersWithExpiredAccounts = userService.getUsersWithExpiredAccounts();
    int countOfBillsCreated = 0;
    StringBuffer billsCreatedFor = new StringBuffer();
    Iterator itr = usersWithExpiredAccounts.iterator();
    while (itr.hasNext()) {
      User user = (User)itr.next();
      
      //if user does not have a pending bill, create one
      if (user.getPendingBill() == null) {
        Bill bill = billService.createUserBill(user);
        //new user bill has been created by the system
        //send out a notification email to the user for whom the bill was created
        mailUtil.sendUserBillCreationEmail(user, bill);
        countOfBillsCreated++;
        billsCreatedFor.append(countOfBillsCreated + ") UserId=" + user.getUserId() 
                              + ":Name=" + user.getFullname() + ":Username=" + user.getUsername() 
                              + ":Amount=" + bill.getAmount() + "  \n  ");
      }
    }
    //System.out.println("send out an email to admin that the job ran and created bills " + countOfBillsCreated);
    //send out an email to admin that the job ran and created bills
    mailUtil.sendMailToAdmin("Bill Creation Job Complete -- Bills created for " + countOfBillsCreated + " users", billsCreatedFor.toString());
  }

}
