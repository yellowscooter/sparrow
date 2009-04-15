package com.sparrow.service.catalog;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.sparrow.dao.catalog.ProductStatusEnum;
import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.Category;
import com.sparrow.domain.Company;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.ProductReview;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.HomeProduct;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.User;
import com.sparrow.web.catalog.ProductNotAssignedToCategoryException;

public interface ProductService {

  /**
   * Add new or update a author
   * @param author a Author instance
   * @return the new or updated Author
   * @since 1.0
   */
  public Author saveAuthor(Author author);

  /**
   * Get a List of all Authors, sorted by lastName, firstName.
   * @return
   * @since 1.0
   */
  public List getAllAuthors();

  /**
   * Get author by Id if it exists, else null.
   * @param id
   * @return
   * @since 1.0
   */
  public Author getAuthorById(int id);
  
  public List findAuthorByBeginningOfLastname(String criteria);
  
  /**
   * Reuturns true if the given author already exists in the system.
   * @param author
   * @return
   * @since 1.0
   */
  public boolean authorExists(Author author);

  /**
   * Add new or update a company.
   * @param company a company instance
   * @return the new or updated Company
   * @since 1.0
   */
  public Company saveCompany(Company company);

  /**
   * Get a Company by its Id.
   * @param id
   * @return
   * @since 1.0
   */
  public Company getCompanyById(int id);

  /**
   * Get a List of all Companies, sorted by companyName.
   * @return
   * @since 1.0
   */
  public List getAllCompanies();

  /**
   * Add new or update a product.
   * 
   * @param product instance
   * @return a new or updated product
   * @since 1.0
   */
  public Product saveProduct(Product product);

  /**
   * Saves a Product and Product images if they exist. It also saves the 
   * ProductCategories that this Product is assigned to.
   * 
   * @param product with optional images and Categories
   * @return
   * @since 1.0
   */
  public Product saveProductWithImages(Product product,
      String absoluteContextPath);

  /**
   * Delete a product.
   * @param product
   * @since 1.0
   */
  public void deleteProduct(Product product);

  /**
   * Fetch a product by it Id.
   * @param id
   * @return
   * @since 1.0
   */
  public Product getProductById(int id);


  /**
   * Fetch a list of Books that are in ACTIVE status and match a given criteria.
   * @param criteria
   * @param pageNumber the page of data to be fetched
   * @return
   * @since 1.0
   */
  public PagedList findActiveBooks(SearchCriteria criteria, int pageNumber);
  
  /**
   * Find a list of Books that match a given criteria
   * @param criteria
   * @param pageNumber the page of data to be fetched
   * @return
   * @since 1.0
   */
  public PagedList findAllBooks(SearchCriteria criteria, int pageNumber);
  
  
  /**
   * Fetches a {@link PagedList} of {@link Product}s based on the given criteria and pageNumber. It only fetches one page
   * at a time.
   * 
   * @param pageNumber
   * @param searchCriteria
   * @param productStatusEnum
   * @return
   * @since 1.0
   */
  public PagedList getProductPagedList(int pageNumber, SearchCriteria searchCriteria, ProductStatusEnum productStatusEnum);
  
//  /**
//   * Fetch a list of Books that match a given {@link SearchCriteria}
//   * @param criteria
//   * @return
//   * @since 1.0
//   */
//  public List findBooks(SearchCriteria criteria);

  /**
   * Saves a ProductInstance
   * @param productInstance
   * @return
   * @since 1.0
   */
  public ProductInstance saveProductInstance(ProductInstance productInstance);

  /**
   * Convenience method to create a Product Instance
   * @param product
   * @return
   * @since 1.0
   */
  public ProductInstance saveProductInstance(Product product);

  public ProductInstance getProductInstanceById(int id);

  /**
   * Returns a List of all Product for a Category.
   * @param category
   * @return
   * @since 1.0
   */
  public List getProductsByCategory(Category category);

  /**
   * Returns a List of all Product for a category Id.
   * @param categoryId
   * @return
   * @since 1.0
   */
  public List getProductsByCategory(int categoryId);
  
  public PagedList getRecentlyAddedProducts(int pageNumber);
  /**
   * Save a List of {@link Product}. Returns a {@link List} of {@link String}
   * with ISBN, Product Name, Reason for Not Saving for Products that could not
   * be saved. Various reasons why a product could not be saved are
   * 1) Product already exists
   * 2) Description too long
   * 3) Any other exception
   * A a product cannot be saved, this impl moves on to the next product after adding an entry in the list.
   * 
   * @param productList
   * @return
   * @since 1.0
   */
  public List saveProductList(List productList);
  
  /**
   * Save products from xml file.
   * 
   * @throws Exception
   * @since 1.0
   */
  public void saveProductsFromXML() throws Exception;
  
  /**
   * Fetches a {@link Book} by its ISBN
   * @return
   * @since 1.0
   */
  public Product getBookByIsbn(String isbn);
  
  /**
   * Activate a product from DRAFT status to ACTIVE
   * @param product
   * @return
   * @throws ProductNotAssignedToCategoryException TODO
   * @since 1.0
   */
  public Product activateProduct(Product product) throws ProductNotAssignedToCategoryException;
  
  /**
   * Activate a {@link Product} from DRAFT status to ACTIVE
   * @param productId
   * @return
   * @throws ProductNotAssignedToCategoryException TODO
   * @since 1.0
   */
  public Product activateProduct(int productId) throws ProductNotAssignedToCategoryException;
  
  /**
   * Fetch a {@link PagedList} list of {@link Product}s for a given category
   * @param pageNumber
   * @param categoryId
   * @return
   * @since 1.0
   */
  public PagedList getProductsPagedListByCategory(int pageNumber, int categoryId);
  
  /**
   * Fetches product instances and initializes the {@link Product#setProductInstances(List)} list. 
   * We are doing this load as needed and not on every product load for performance reasons.
   * @param product
   * @since 1.0
   */
  public void getProductInstances(Product product);

//  /**
//   * Search for {@link Product}s by title
//   * @param pageNumber
//   * @param searchCriteria
//   * @return
//   * @since 1.0
//   */
//  public PagedList findProductByTitle(int pageNumber, String searchCriteria);
  /**
   * Fetch instances for all products in the product list
   */
  public void getProductInstances(PagedList productList);
  
  /**
   * Fetch all {@link Book}s by a given {@link Author}
   * @param author
   * @since 1.0
   */
  public PagedList findActiveBooksByAuthor(Author author, int pageNumber);
  
  /**
   * Fetches a List of books that have been added by Users to their Bookself in descending order order of most popular.
   * 
   * @param start
   * @return
   * @since 1.0
   */
  public PagedList findBooksMostInDemand(int start);
  
  /**
   * Save a {@link ProductReview} for the {@link Product} whose productId is
   * passed in as productId of {@link ProductReview} parameter
   * @param productReview productId must be populated
   * @since 1.0
   */
  public void addProductReview(ProductReview productReview);
  
  /**
   * Fetches one random book from list of active books
   * 
   * @since 1.0
   */
  public List getOneRandomAvailableBook();

  public List findBooksReadByOtherUsersWhoReadThisBook(final int numOfBooks, final User user, final Product book);
}