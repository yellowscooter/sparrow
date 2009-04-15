package com.sparrow.web.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.DeliveryRequest;
import com.sparrow.domain.Product;
import com.sparrow.domain.User;
import com.sparrow.domain.UserNote;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.service.deliveryrequest.DeliveryRequestAlreadyExistsException;
import com.sparrow.service.deliveryrequest.DeliveryRequestService;
import com.sparrow.service.user.ProductRequestAlreadyExistsException;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.web.WebConstants;

public class UserDetailsController extends SimpleFormController {
  UserService userService;
  DeliveryRequestService deliveryRequestService;
  ProductService productService;
  
  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  public DeliveryRequestService getDeliveryRequestService() {
    return deliveryRequestService;
  }

  public void setDeliveryRequestService(DeliveryRequestService deliveryRequestService) {
    this.deliveryRequestService = deliveryRequestService;
  }
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ModelAndView modelAndView = new ModelAndView("/admin/user/userDetails");
    String submitAction = ServletRequestUtils.getStringParameter(request, "submitAction");
    int userId = ServletRequestUtils.getIntParameter(request, "userId");
    
    User user = userService.getUserById(userId);
    modelAndView.addObject("user", user);
//  set in request for display tag to pickup the notes list
    modelAndView.addObject("notesList", user.getUserNotes());
//  add the productRequests list for access by display tag
    modelAndView.addObject("productRequestsList", user.getProductRequests());
//  populate all products with product instances
    userService.getProductInstances(user.getProductRequests());
//  fetch the UserProducts list...this is the list of products currently with the user
    //List userProductsList = userService.getUserProductsByUser(user);
    Set userProductsList = user.getUserProducts();
//  add the userProductsList list for access by display tag
    modelAndView.addObject("userProductsList", userProductsList);
    
    //get and set the deliveryRequestsList for user
    List deliveryRequestsList = deliveryRequestService.getUserDeliveryRequestList(user);
    modelAndView.addObject("deliveryRequestsList", deliveryRequestsList);

    
    if (submitAction == null) {
            
      
      
    } else if ("addNote".equals(submitAction)) {
      String note = ServletRequestUtils.getRequiredStringParameter(request, "note");
      
      if (StringUtils.isNotBlank(note)) {
        //if note length is more than 4000 chars, truncate to 4000 since db column is varchar 4000
        if (note.length() > 4000) {
          note = note.substring(0, 3999);
        }
        UserNote userNote = new UserNote(new Date(), user.getUserId(), note, GeneralUtils.getCurrentUserFromTLS());
        user.getUserNotes().add(userNote);
        userService.saveUser(user);  
      }
            
    } else if ("newDeliveryRequest".equals(submitAction)) {
      DeliveryRequest deliveryRequest;
      
      try {
        deliveryRequest = deliveryRequestService.getInCompleteDeliveryRequestByUser(user);  
      } catch (DeliveryRequestAlreadyExistsException de) {
        //if delivery reqeust already exists...do nothing. Just display message to user
        //that we are working on it.
        modelAndView.addObject("message", "user.delivery.request.exists");
        //modelAndView.addAllObjects(model);
        return modelAndView; 
      }
      
      DeliveryRequest newDeliveryRequest = new DeliveryRequest(user, new Date());
      DeliveryRequest deliveryRequest2 = deliveryRequestService.saveDeliveryRequest(newDeliveryRequest);
      
      //validate if user has not used max allowed delivery requests per month under the plan
      //If yes, we need to tell the user that they will have to wait till next plan month for request to be completed
      if (deliveryRequestService.isUserInMonthlyDeliveryRequestLimitForPlan(user)) { //user still has available books
        modelAndView.addObject("message", "delivery.request.sent");
      } else {    //user is maxed out
        modelAndView.addObject("message", "delivery.request.sent.nextmonth");
      }
      
    } else if ("addToQueue".equals(submitAction)) {
      String productId = ServletRequestUtils.getStringParameter(request, "productId");
      Product product = null;
      if (StringUtils.isNumeric(productId) && StringUtils.isNotBlank(productId)) {
        product = productService.getProductById(Integer.parseInt(productId));
      }
      try {
        //null means product does not exist or invalid id was passed...productId was invalid...
        if (product != null) {
          user.addProductRequest(product);  
        }
          
      } catch (ProductRequestAlreadyExistsException pe) {
        //Product Request is already in queue. No need to save the queue to the database
        //just return without save
        //put the In Queue message
        modelAndView.addObject("messageAddToQ", "book.addedToQueue");
        return modelAndView;
      }
      //save the updated queue to the database
      //userService.saveProductRequestListForUser(currentUser);
      userService.saveUser(user);
      
      //since a new product has been added to the list, get all instances for all products again
      //not the smartest thing to do since we have already done this above, but works
      //populate all products with product instances
      userService.getProductInstances(user.getProductRequests());

    } else if ("addUpdateDelInstr".equals(submitAction)) {
      String deliveryInstructions = ServletRequestUtils.getRequiredStringParameter(request, "deliveryInstructions");
      if (deliveryInstructions.length() > 4000) {
        deliveryInstructions = deliveryInstructions.substring(0, 3999);
      }
      user.setDeliveryInstructions(deliveryInstructions);
      userService.saveUser(user);
    } else if ("removeFromQueue".equals(submitAction)) {
      int priority = ServletRequestUtils.getIntParameter(request, "priorityToRemove");
      //this is the priority that is displayed to the user, and starts from 1
      //the real priority numbers start from 0...so subtract 1
      priority--;
      userService.removeProductRequest(user, priority);
      //refresh the user object
      user = userService.getUserById(userId);
      modelAndView.addObject("user", user);
    }
    
    return modelAndView;
  }
  
  

}
