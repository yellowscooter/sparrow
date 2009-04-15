package com.sparrow.web.catalog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.sparrow.domain.Author;
import com.sparrow.domain.Category;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.service.catalog.CategoryService;
import com.sparrow.service.catalog.HomeProductService;
import com.sparrow.service.catalog.ProductService;
import com.sparrow.web.WebConstants;

/**
 * Controller to display the product catalog.
 * @author manishk
 * @since 1.0
 */
public class BrowseCatalogController extends SimpleFormController {
  private static final String PRODUCT_LIST = "productList";
  private static final String CURRENT_CATEGORY_NAME = "currentCategoryName";
  
  private static final String HOME_PRODUCT_LIST = "homeProductList";
  private ProductService productService;
  private CategoryService categoryService;
  private HomeProductService homeProductService;
  
  public ProductService getProductService() {
    return productService;
  }

  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  public CategoryService getCategoryService() {
    return categoryService;
  }

  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  
  public HomeProductService getHomeProductService() {
    return homeProductService;
  }

  public void setHomeProductService(HomeProductService homeProductService) {
    this.homeProductService = homeProductService;
  }

  /**
   * Set the reference data to display the catalog. Sets the category list
   * and product list in session.
   */
  protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    Map refData = new HashMap();
    HttpSession session = request.getSession();
    
    //    if the category list is not present in session, set it.
    if (session.getAttribute(WebConstants.CATEGORY_LIST) == null) {
      //categories were modified to be of various types...LEFTNAV, AWARD etc
      //List categoryList = (List)categoryService.getTopCategories();
      List categoryList = (List)categoryService.getLeftNavTopCategories();
      session.setAttribute(WebConstants.CATEGORY_LIST, categoryList);  
    }
    int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
    String criteria = ServletRequestUtils.getStringParameter(request, "criteria");
    String author_id = ServletRequestUtils.getStringParameter(request, "author_id");
    String recentlyAdded = ServletRequestUtils.getStringParameter(request, "recentlyAdded");
    
    //will be true if search criteria fails validation by SearchCriteriaValidator
    //should not perform any search if search criteria validation has failed
    boolean hasErrors = errors.hasErrors();
    
    if (categoryId != -1) {   //get products for a category
      int page = ServletRequestUtils.getIntParameter(request, "page", 1);
      //just get products for a particular category
      PagedList productList = productService.getProductsPagedListByCategory(page, categoryId);
      request.setAttribute(PRODUCT_LIST, productList);
      //set the category name of category which user clicked...this will be displayed on top of page
      Category currentCategory = categoryService.getCategoryById(categoryId);
      request.setAttribute(CURRENT_CATEGORY_NAME, currentCategory.getName());
    } else if (!hasErrors && criteria != null) {   //user is paging on serch result and search criteria is valid
      int page = ServletRequestUtils.getIntParameter(request, "page", 1);
      SearchCriteria searchCriteria = new SearchCriteria(criteria);
      PagedList productList = productService.findActiveBooks(searchCriteria, page);
      request.setAttribute(PRODUCT_LIST, productList);
    } else if (author_id != null) {   //list all books by author
      int page = ServletRequestUtils.getIntParameter(request, "page", 1);
      try {
        Author author = productService.getAuthorById(Integer.parseInt(author_id));
        PagedList productList = productService.findActiveBooksByAuthor(author, page);
        request.setAttribute(PRODUCT_LIST, productList);  
        request.setAttribute("author", author);
        request.setAttribute("displayAuthorName", "true");
        //put the param to be displayed in page title
        request.setAttribute("dynamicPageTitle", author.getFullName() + " |");
        request.setAttribute("dynamicMetaDescription", "Books by author " + author.getFullName() + ".");
        request.setAttribute("dynamicMetaDescription1", "Book reviews and more on books by " + author.getFullName() + ".");
        request.setAttribute("dynamicMetaKeywords", author.getFullName() + ",");
        
        //get the requestURI to set in display tag
        String url = WebConstants.getAuthorPageURL(request, author);
        request.setAttribute("requestURI", url);
      } catch (NumberFormatException ne) {
        //just catch the exception if author id is not a valid number
        logger.error(ne);
      }
      
    } else if ("Y".equals(recentlyAdded)) {   //display recently added products
      //we can use the Home Product list to display the recently added products, 
      //since both are simple lists, not paged lists
      int page = ServletRequestUtils.getIntParameter(request, "page", 1);
      PagedList productList = productService.getRecentlyAddedProducts(page);
      request.setAttribute(PRODUCT_LIST, productList);
      request.setAttribute("displayRecentlyAddedProductList", "true");
    } else {    //get home product list
      //category id -1 means user is tryig to goto homepage
      //get products that are to be displayed on the home page
      List productList = homeProductService.getProductListForHomePage();
      request.getSession().setAttribute(HOME_PRODUCT_LIST, productList);
      request.setAttribute("displayHomeProductList", "true");
    }
    
    //fetch the random featured product
    List list = productService.getOneRandomAvailableBook();
    request.setAttribute("randomFeaturedBook", list.get(0));
    
    //since products will be displayed on Home tab, pre select it
    request.setAttribute("tab", "1");
    return new ModelMap("refData", refData);
  }
  
  

  @Override
  protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
    SearchCriteria criteria = (SearchCriteria)command; 
    
    int page = ServletRequestUtils.getIntParameter(request, "page", 1);
    PagedList bookList = productService.findActiveBooks(criteria, page);
    request.setAttribute(PRODUCT_LIST, bookList);
    
    //  fetch the random featured product
    List list = productService.getOneRandomAvailableBook();
    request.setAttribute("randomFeaturedBook", list.get(0));
    
    return new ModelAndView(getSuccessView(), errors.getModel());
  }

}
