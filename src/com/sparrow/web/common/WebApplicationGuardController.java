package com.sparrow.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.GuardKeyHolder;

/**
 * Checks if the user has give the key...if not asks user for the key.
 * On submit, checks if key is valid and puts it in session, if not, go back to 
 * ask for key.
 * 
 * @author manishk
 * @since 1.0
 * @see WebApplicationGuardFilter
 * @see GuardKeyHolder
 */
public class WebApplicationGuardController extends SimpleFormController {
  //this key value is read from property file
  private String guardKey;
  
  public String getGuardKey() {
    return guardKey;
  }
  public void setGuardKey(String guardKey) {
    this.guardKey = guardKey;
  }


  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    GuardKeyHolder holder = (GuardKeyHolder) command;
    if (this.getGuardKey().equals(holder.getKey())) {
      //valid key...set in session and then go to success view
      request.getSession().setAttribute(WebApplicationGuardFilter.KEY_HOLDER, holder);
    } else {
      //invalid key...gp back to the key entry jsp
      return new ModelAndView(getFormView(), errors.getModel());
    }
    
    return new ModelAndView(getSuccessView(), errors.getModel());
  }
  
  
}
