package com.sparrow.web.bill;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.sparrow.domain.Bill;
import com.sparrow.domain.User;
import com.sparrow.service.bill.BillService;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.MailUtil;

/**
 * Controller to manually create a {@link Bill}
 * @author Manish Kumar
 * @since 1.0
 */
public class CreateUserBillController extends AbstractController {
  private UserService userService;
  private BillService billService;
  MailUtil mailUtil;
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

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
                                              throws Exception {
    int userId = ServletRequestUtils.getRequiredIntParameter(request, "userId");
    String submitAction = ServletRequestUtils.getStringParameter(request, "submitAction", null);
    User user = userService.getUserById(userId);
    ModelMap model = new ModelMap();
    
    if ("createBill".equals(submitAction)) {
      Bill bill = billService.createUserBill(user);
      
      //new user bill has been created manually by Admin
      //send out a notification email to the user for whom the bill was created
      mailUtil.sendUserBillCreationEmail(user, bill);
      
      model.put("message", "User bill created");
      return new ModelAndView("admin/success", model);
    } else {
      request.setAttribute("user", user);
      return new ModelAndView("admin/bill/createUserBill");
    }

  }
  
  

}
