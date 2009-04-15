package com.sparrow.service.catalog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sparrow.dao.catalog.ProductStatusEnum;
import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.Category;
import com.sparrow.domain.Company;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.service.AbstractServiceTests;
import com.sparrow.service.ServiceConstants;
import com.sparrow.service.user.UserService;

public class ProductServiceTests extends AbstractServiceTests {
  public ProductService productService;
  public CategoryService categoryService;
  public UserService userService;

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
  

  public UserService getUserService() {
    return userService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void testAddAuthor() {
    int count = jdbcTemplate.queryForInt("select count(0) from author");
    Author author1 = new Author("FirstName", "LastName");
    productService.saveAuthor(author1);
    assertEquals("Author should be inserted"
        ,count + 1 
        ,jdbcTemplate.queryForInt("select count(0) from author"));
    
  }
  
  public void testUpdateAuthor() {
    Author author1 = new Author("FirstName", "LastName");
    productService.saveAuthor(author1);
    author1.setLastName("lastNameX");
    Author author2 = productService.saveAuthor(author1);
    assertEquals("Last name should have been updated"
                  , "lastNameX"
                  , author2.getLastName());
    
  }
  
  public void testGetAllAuthors() {
    int count = jdbcTemplate.queryForInt("select count(0) from author");
    List authorList = (List)productService.getAllAuthors();
    assertEquals("Author count does not match"
                  , count
                  , authorList.size());
  }
  
  public void testGetAuthorById() {
    Author author = productService.getAuthorById(1000);
    assertEquals("Author should have been fetched"
                , 1000
                , author.getAuthorId());
    assertEquals("Correct Author should have been fetched"
        , "Dan"
        , author.getFirstName());
  }
  
  public void testAddCompany() {
    int count = jdbcTemplate.queryForInt("select count(0) from company");
    Company company = new Company("CompanyName");
    productService.saveCompany(company);
    assertEquals("One Company should be inserted"
                  ,count + 1
                  , jdbcTemplate.queryForInt("select count(0) from company"));
    
  }
  
  public void testAddBook() {
    int count = jdbcTemplate.queryForInt("select count(0) from product");
    Product prod = getBook("A");
    productService.saveAuthor(prod.getAuthor());
    productService.saveCompany(prod.getCompany());
    Product savedProduct = productService.saveProduct(prod);
    assertEquals("One Product should be inserted"
        ,count + 1
        , jdbcTemplate.queryForInt("select count(0) from product"));
    
    assertEquals("Num in stock should be 0"
                  ,0
                  , savedProduct.getNumInStock());
    
    assertEquals("Available in stock should be 0"
                  ,0
                  , savedProduct.getAvailableInStock().shortValue());
    //assertTrue("The Instance Id should be > 0", savedProduct.getProductInstanceId() > 0);

  }
  
  public void testAddBookCascade() {
    int count = jdbcTemplate.queryForInt("select count(0) from product");
    Product prod = getBook("A");
    Category category = categoryService.getCategoryById(1001);
    
    prod = productService.saveProduct(prod);
    assertEquals("One Product should be inserted"
        ,count + 1
        , jdbcTemplate.queryForInt("select count(0) from product"));
    
  }
  
//  public void testDeleteProduct() {
//    Product prod = getProduct("A");
//    Product product = productService.saveProduct(prod);
//    assertEquals("One Product should be inserted"
//        ,1
//        , jdbcTemplate.queryForInt("select count(0) from product"));
//    productService.deleteProduct(product);
//    assertEquals("Product count should be 0"
//        ,0
//        , jdbcTemplate.queryForInt("select count(0) from product"));
//    
//  }
  
  public void testGetProductById() {
    Product product = productService.getProductById(1000);
    assertNotNull(product);
    Author author = product.getAuthor();
    assertEquals("Author id is incorrect", 1000, author.getAuthorId());
  }
  
//  public void testGetAllBooks() {
//    int count = jdbcTemplate.queryForInt("select count(0) from product");
//    PagedList productsList = productService.getAllBooks(0);
//    assertEquals("Product count does not match"
//                  , count
//                  , productsList.getList().size());
//  }
  
//  public void testBooksByAuthor() {
//    int count = jdbcTemplate.queryForInt("select count(0) from product where author_id=1000");
//    Author author = productService.getAuthorById(1000);
//    List productsList = productService.getBooksByAuthor(author);
//    assertEquals("Product count does not match"
//                  , count
//                  , productsList.size());
//  }

  public void testFindActiveBooks() {
    SearchCriteria criteria = new SearchCriteria("code"); 
    //should find The DaVinci Code
    PagedList bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 1
                , bookList.getList().size());
    Product book = (Book)bookList.getList().get(0);
    assertEquals("Expected/found product names do not match"
                , "The DaVinci Code"
                , book.getName());
    
    //find books for author name incomplete
    criteria = new SearchCriteria("grisha");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 2
                , bookList.getList().size());
    //check for incomplete isbn
    criteria = new SearchCriteria("9781400");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 1
                , bookList.getList().size());
    
    //check for full author name
    criteria = new SearchCriteria("john grisham");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 2
                , bookList.getList().size());
    
    //generic search...
    criteria = new SearchCriteria("the");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 3
                , bookList.getList().size());
    
    //search description
    criteria = new SearchCriteria("descrip");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 5
                , bookList.getList().size());
    
    //  search for books in DRAFT state...nothing should be found
    criteria = new SearchCriteria("draft");
    bookList = productService.findActiveBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 0
                , bookList.getList().size());
    
    
  }
  
  public void testFindAllBooks() {
    SearchCriteria criteria = new SearchCriteria("draft"); 
    //should find The DaVinci Code
    PagedList bookList = productService.findAllBooks(criteria, 0);
    assertEquals("Product search count does not match"
                , 2
                , bookList.getList().size());
  }
  
  public void testSaveProductInstance() {
    Product product = productService.getProductById(1000);
    ProductInstance prodIns = new ProductInstance(product);
    prodIns = productService.saveProductInstance(prodIns);
    assertTrue("Instance should have been saved with instance id > 0", prodIns.getProductInstanceId() > 0);
  }
  
  public void testGetProductInstanceById() {
    ProductInstance prodIns = productService.getProductInstanceById(1000);
    assertEquals("correct product instance should be fetched", 1000, prodIns.getProductInstanceId());
  }
  
  public void testGetProductsForCategory() {
    int count = jdbcTemplate.queryForInt("select count(0) from product p, product_category pc" +
        " where pc.product_id = p.product_id" +
        " and pc.category_id = " + 2);
    
    Category category = categoryService.getCategoryById(2);
    List productList = productService.getProductsByCategory(category);  
    assertEquals("The number of products in category is incorrect"
                , count, productList.size());
  }
  
  public void testSaveProductList() {
    List productList = new ArrayList();
    productList.add(createBookWithISBN("book1", "9781111111111"));
    productList.add(createBookWithISBN("book2", "9782222222222"));
    productList.add(createBookWithISBN("book3", "9783333333333"));
    productList.add(createBookWithISBN("book4", "9784444444444"));
    productList.add(createBookWithISBN("book5", "9785555555555"));
    List errorProductList = productService.saveProductList(productList);
    assertEquals("error List size should be 0", 0, errorProductList.size() );
    
  }
//uncomment if you want to load product data from xml  
  public void testLoadProductsFromXML() throws Exception {
    productService.saveProductsFromXML();
    setComplete();
  }
  
  public void testGetBookByIsbn() throws Exception {
    Product book = productService.getBookByIsbn("9781400079179");
    assertNotNull("One Book should have been fetched", book);
    
    book = productService.getBookByIsbn("978140007917X");
    assertNull("Book should not be found with this isbn", book);
  }
  
  private static Product createBookWithISBN(String name, String isbn) {
    Book book = (Book)getBook(name);
    book.setIsbn(isbn);
    return book;
  }
  
  public static Product getBook(String name) {
    Product prod = new Book();
    prod.setName(name);
    prod.setAuthor(new Author("firstName"+name, "LastName"+name));
    prod.setDescription("This is a product" + name);
    prod.setLength(6.1f);
    prod.setWidth(4.2f);
    prod.setHeight(2f);
    //prod.setImageSmall("Y");
    prod.setCompany(new Company("TestCompany"+name));
    
    
    return prod;
  }
  
  public void testGetProductsPagedListByCategory() {
    PagedList productList = productService.getProductsPagedListByCategory(0, 2);
    //4 products should be fetched based on test data
    assertEquals("List size is not correct", 4, productList.getList().size());
    
//    productList = productService.getProductsPagedListByCategory(1, 2);
//    assertEquals("List size is not correct", 0, productList.getList().size());
  }
  
  public void testGetProductPagedList() {
    //null will return all products
    PagedList productList = productService.getProductPagedList(0, null, ProductStatusEnum.ALL);
    assertEquals("List size is not correct", 7, productList.getList().size());
    
    //search by name
    productList = productService.getProductPagedList(0, new SearchCriteria("name", "code"), ProductStatusEnum.ALL);
    assertEquals("List size is not correct", 1, productList.getList().size());
  }
  
//  public void testFindProductsByTitle() {
//    PagedList productList = productService.findProductByTitle(0, "da vinci");
//    assertEquals("List size is not correct", 1, productList.getList().size());
//  }
  
  public void testFindAuthorByBeginningOfFirstname() {
    int count = jdbcTemplate.queryForInt("select count(0) from author where lower(last_name) like 'b%'");
    List list = productService.findAuthorByBeginningOfLastname("B");
    
    assertEquals("Count of authors is not correct", count, list.size());
  }
  
  public void testFindBooksMostInDemand() {
    PagedList productList = productService.findBooksMostInDemand(1);
    
    assertTrue("Results fetched should be 50", productList.getList().size() == ServiceConstants.BOOKS_IN_DEMAND_PAGED_LIST_SIZE);
  }
  
  public void testFindBooksReadByOtherUsersWhoReadThisBook() {
    Book book = (Book)productService.getProductById(1001);
    List list = productService.findBooksReadByOtherUsersWhoReadThisBook(5, null, book);
  }
}