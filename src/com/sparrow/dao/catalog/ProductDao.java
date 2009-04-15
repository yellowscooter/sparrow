package com.sparrow.dao.catalog;

import java.util.Collection;
import java.util.List;

import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.Category;
import com.sparrow.domain.Company;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.ProductType;
import com.sparrow.domain.User;

/**
 * Interface for Product and related entity maintenance/retreival.
 * @author manishk
 * @since 1.0
 */
public interface ProductDao {
  /**
   * Add new/update a Author.
   * @param author a author instance.
   * @return the new or updated author
   */
  public Author saveAuthor(Author author);
  
  /**
   * Fetch all Authors.
   * @return a Collection of Author objects
   * @since 1.0
   */
  public Collection getAllAuthors();
  
  /**
   * Get Author by Id if it exists, else returns null.
   * @param id
   * @return
   * @since 1.0
   */
  public Author getAuthorById(int id);
  
  /**
   * Fetch an Author by his or her full name. Returns null if author with given full name is not found.
   * 
   * @param fullName
   * @return
   * @throws IllegalStateException if more than one {@link Author} is found with a given fullName
   * @since 1.0
   */
  public Author getAuthorByFullName(String fullName);
  
  /**
   * Searches for {@link Author}s based on given search criteria.
   * This method does not do any wildcard decoration, so the criteria should contain the wildcard character.
   * Result list is sorted by lastName, firstName
   * @param searchCriteria
   * @return
   * @since 1.0
   */
  public List searchAuthor(SearchCriteria searchCriteria);
  
  /**
   * Add new or update a Company.
   * @param company a company instance
   * @return the new or updated company
   * @since 1.0
   */
  public Company saveCompany(Company author);
  
  /**
   * Get company by Id
   * @param id
   * @return
   * @since 1.0
   */
  public Company getCompanyById(int id);
  
  /**
   * Fetch all Companies.
   * @return a Collection of Company objects
   * @since 1.0
   */
  public Collection getAllCompanies();
  
  /**
   * Add new or update a Product.
   * @param product instance
   * @return the new or updated product
   * @since 1.0
   */
  public Product saveProduct(Product product);
  
  /**
   * Delete a product.
   * @param product
   * @since 1.0
   */
  public void deleteProduct(Product product);
  
  /**
   * Fetch a Product given a product Id
   * @param id
   * @return the Product
   * @since 1.0
   */
  public Product getProductById(int id);
  
  /**
   * Fetch all Products.
   * @return
   * @since 1.0
   */
  public Collection getAllBooks();
  
  /**
   * Performs a search based on a given criteria. This search impl does not take search key into consideration.
   *  
   * Current implementation does a wildcard case insensitive search in the following order
   * Product name, author full name, author last name, author first name, isbn, description.
   * 
   * @param start the start index from which to fetch the results
   * @param objectsPerPage number of records to be fetched
   * @param criteria whole or part of book name, isbn or author name
   * @param status Status of product to search, e.g. end user is only interested in active Products
   * 
   * @return List of books matching the criteria
   * @since 1.0
   */
  public PagedList search(final int start, final int objectsPerPage, final SearchCriteria criteria, final ProductStatusEnum status);
  
  /**
   * Save a new ProductInstance
   * @param productInstance
   * @return the new ProductInstance
   * @since 1.0
   */
  public ProductInstance saveProductInstance(ProductInstance productInstance);
  
  /**
   * Get a product instance by its id
   * @param id
   * @return
   * @since 1.0
   */
  public ProductInstance getProductInstanceById(int id);
  
//  /**
//   * Get all products by an Author sorted by name.
//   * @param author
//   * @return
//   * @since 1.0
//   */
//  public Collection getProductsByAuthor(Author author, ProductType productType);
  
  /**
   * Get all products for a Category
   * @param category
   * @return a Collection of Products
   * @since 1.0
   */
  public Collection getProductsByCategory(Category category);
  
  /**
   * Get all products for a Category
   * @param categoryId
   * @return
   * @since 1.0
   */
  public Collection getProductsByCategory(int categoryId);
  
  /**
   * Return products that were recently added to the catalog
   * @return
   * @since 1.0
   */
  public PagedList getRecentlyAddedProducts(final int start, final int objectsPerPage);
  
  /**
   * Fetch a Product by its ISBN
   * @param isbn
   * @return {@link Book} if found, else returns null
   * @since 1.0
   */
  public Product getBookByIsbn(String isbn);
  
  /**
   * Fetch a {@link PagedList} list of {@link Product}s
   *  
   * @param start the index from where to start
   * @param length the number of records to be returned, starting from start index
   * @param categoryId id of category whose products are to be fetched 

   * @return {@link PagedList} of products of the given cateogry
   * @since 1.0
   */
  public PagedList getProductsPagedListByCategory(int start, int length, int categoryId);
  
  /**
   * Fetch all {@link ProductInstance}s for a {@link Product} and initialize the {@link Product#setProductInstances(List)} list
   * @param product
   * @since 1.0
   */
  public void getProductInstances(Product product);
  
//  /**
//   * 
//   * @param start
//   * @param objectsPerPage
//   * @param searchCriteria
//   * @return
//   * @since 1.0
//   */
//  public PagedList search(final int start, final int objectsPerPage, final SearchCriteria searchCriteria);
  /**
   * Fetch all {@link Book}s by a given {@link Author}
   * @param author
   * @since 1.0
   */
  public PagedList findActiveBooksByAuthor(int start, int objectsPerPage, Author author, ProductStatusEnum status);
  
  /**
   * Find a random set of Active books that are comfortably available (available_in_stock > 1)
   * @param numOfBooks Limit of how many records to return
   * @param user user for whome we are fetching the list
   * @return
   * @since 1.0
   */
  public List findRandomAvailableBooks(int numOfBooks, User user);
  
  public List findBooksReadByOtherUsersWhoReadThisBook(final int numOfBooks, final User user, final Product book);
}
