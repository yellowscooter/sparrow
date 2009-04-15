package com.sparrow.service.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.sparrow.dao.catalog.ProductDao;
import com.sparrow.dao.catalog.ProductStatusEnum;
import com.sparrow.dao.user.UserDao;
import com.sparrow.dao.util.UtilDao;
import com.sparrow.domain.Author;
import com.sparrow.domain.Book;
import com.sparrow.domain.BooksMostInDemandReportVO;
import com.sparrow.domain.Category;
import com.sparrow.domain.Company;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.ProductRequest;
import com.sparrow.domain.ProductReview;
import com.sparrow.domain.ProductSearch;
import com.sparrow.domain.SearchCriteria;
import com.sparrow.domain.Product;
import com.sparrow.domain.ProductCategory;
import com.sparrow.domain.ProductInstance;
import com.sparrow.domain.User;
import com.sparrow.domain.UserSearch;
import com.sparrow.service.ServiceConstants;
import com.sparrow.service.user.UserService;
import com.sparrow.service.util.CastorProductListUtil;
import com.sparrow.service.util.GeneralUtils;
import com.sparrow.service.util.MailUtil;
import com.sparrow.web.catalog.ProductNotAssignedToCategoryException;

/**
 * The product service implementation class.
 * @author manishk
 * @since 1.0
 */
public class ProductServiceImpl implements ProductService {
  private static final transient Log logger = LogFactory.getLog(ProductServiceImpl.class);
  
  private ProductDao productDao;
  private String imagesDir;
  private String appTempDir;
  private String absoluteContextPath;
  private CategoryService categoryService;
  private CastorProductListUtil castorProductListUtil;
  private int productListPageSize;
  private UserDao userDao;
  private UtilDao utilDao;
  private MailUtil mailUtil;
  
  public ProductDao getProductDao() {
    return productDao;
  }

  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }
  
  public String getImagesDir() {
    return imagesDir;
  }

  public void setImagesDir(String imagesDir) {
    this.imagesDir = imagesDir;
  }

  public String getAppTempDir() {
    return appTempDir;
  }

  public void setAppTempDir(String appTempDir) {
    this.appTempDir = appTempDir;
  }

  public String getAbsoluteContextPath() {
    return absoluteContextPath;
  }

  public void setAbsoluteContextPath(String absoluteContextPath) {
    this.absoluteContextPath = absoluteContextPath;
  }

  public CategoryService getCategoryService() {
    return categoryService;
  }

  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  
  public CastorProductListUtil getCastorProductListUtil() {
    return castorProductListUtil;
  }

  public void setCastorProductListUtil(CastorProductListUtil castorProductListUtil) {
    this.castorProductListUtil = castorProductListUtil;
  }
  
  public int getProductListPageSize() {
    return productListPageSize;
  }

  public void setProductListPageSize(int productListPageSize) {
    this.productListPageSize = productListPageSize;
  }
  
  public UserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public UtilDao getUtilDao() {
    return utilDao;
  }

  public void setUtilDao(UtilDao utilDao) {
    this.utilDao = utilDao;
  }

  public MailUtil getMailUtil() {
    return mailUtil;
  }

  public void setMailUtil(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveAuthor(com.sparrow.domain.Author)
   */
  public Author saveAuthor(Author author) {
    author.setFullName(author.getFirstName() + Author.AUTHOR_NAME_SEPERATOR + author.getLastName());
    return productDao.saveAuthor(author);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getAllAuthors()
   */
  public List getAllAuthors() {
    return (List)productDao.getAllAuthors();
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getAuthorById(int)
   */
  public Author getAuthorById(int id) {
    return productDao.getAuthorById(id);
  }
  
  public List findAuthorByBeginningOfLastname(String crtieria) {
    SearchCriteria searchCriteria = new SearchCriteria("lastName", crtieria + "%");
    return productDao.searchAuthor(searchCriteria);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#authorExists(com.sparrow.domain.Author)
   */
  public boolean authorExists(Author author) {
    boolean exists = false;
    Author existingAuthor = productDao.getAuthorByFullName(author.getFirstName() + Author.AUTHOR_NAME_SEPERATOR + author.getLastName());
    if (existingAuthor != null) {
      exists = true;
    }
    return exists;
  }

  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveCompany(com.sparrow.domain.Company)
   */
  public Company saveCompany(Company company) {
    return productDao.saveCompany(company);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getCompanyById(int)
   */
  public Company getCompanyById(int id) {
    return productDao.getCompanyById(id);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getAllCompanies()
   */
  public List getAllCompanies() {
    return (List)productDao.getAllCompanies();
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveProduct(com.sparrow.domain.Product)
   */
  public Product saveProduct(Product product) {
    
    //If its a new product, save it as draft so it can be activated later.
    if (product.isNew()) {
      product.setStatus(ProductStatusEnum.DRAFT.toString());
    }
    
    
    Product savedProduct = productDao.saveProduct(product);
    
    //saved product, not save data in product search to help in searches
    Book book = (Book) product;
    if (product.getProductSearch() == null) {
      
      ProductSearch productSearch = new ProductSearch(book.getProductId()
                                                      , book.getName()
                                                      , book.getDescription()
                                                      , book.getIsbn()
                                                      , book.getAuthor().getFullName());
      product.setProductSearch(productSearch);
    } else {
      //product id will never change...so not here
      product.getProductSearch().setProductName(book.getName());
      product.getProductSearch().setProductDesc(book.getDescription());
      product.getProductSearch().setProductIsbn(book.getIsbn());
      product.getProductSearch().setAuthorFullName(book.getAuthor().getFullName());
    }
    
    return savedProduct;
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveProductWithImages(com.sparrow.domain.Product, java.lang.String)
   */
  public Product saveProductWithImages(Product product, String absoluteContextPath) {
    MultipartFile smallImage = product.getSmallImage();
    String smallImageType = StringUtils.substringAfterLast(smallImage.getOriginalFilename(), ".");
    
    MultipartFile largeImage = product.getLargeImage();
    String largeImageType = StringUtils.substringAfterLast(largeImage.getOriginalFilename(), ".");
    
    
    Product savedProduct = saveProduct(product);
    if (logger.isDebugEnabled()) {
      logger.debug("New Product Saved...now saving images");
      System.out.println("New Product Saved...now saving images");
    }
    //save the small image
    
    //for some reason, even when a image is not loaded, image is not null.
    //only way i could find to tell if image is loaded is to check the filename.
    //if image is not loaded, filename is ""
    if (smallImage != null && !"".equals(smallImage.getOriginalFilename())) {
      String smallImageName = savedProduct.getProductId() + ServiceConstants.SMALL_IMAGE_SUFFIX + smallImageType;
      File file = new File(absoluteContextPath + File.separator 
                          + this.getImagesDir() + File.separator 
                          + smallImageName);
      
      saveImageToFileSystem(smallImage, file);
      //savedProduct.setSmallImage(smallImage);
      savedProduct.setImageSmallName(smallImageName);
      if (logger.isDebugEnabled()) {
        logger.debug("Saved image" + file.getAbsolutePath());
      }
    }
    
    //save the large Image
    if (largeImage != null && !"".equals(largeImage.getOriginalFilename())) {
      String largeImageName = savedProduct.getProductId() + ServiceConstants.LARGE_IMAGE_SUFFIX + largeImageType; 
      File file = new File(absoluteContextPath + File.separator 
                          + this.getImagesDir() + File.separator 
                          + largeImageName);
      saveImageToFileSystem(largeImage, file);
      //savedProduct.setLargeImage(largeImage);
      savedProduct.setImageLargeName(largeImageName);
      if (logger.isDebugEnabled()) {
        logger.debug("Saved image" + file.getAbsolutePath());
      }
    }
    
    //save again to save image names if images were uploaded.
    saveProduct(savedProduct);
    
    //add saved product to categories selected by user.
    if (product.getCategories() != null) {
      addProductToCategories(savedProduct, product.getCategories());  
    }
    
    return savedProduct;
  }
  
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#deleteProduct(com.sparrow.domain.Product)
   */
  public void deleteProduct(Product product) {
    productDao.deleteProduct(product);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductById(int)
   */
  public Product getProductById(int id) {
    return productDao.getProductById(id);
  }
  
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#findBooks(java.lang.String)
   */
  public PagedList findActiveBooks(SearchCriteria criteria, int pageNumber) {
    //save the user entered search
    this.saveUserSearch(criteria);
    return this.getProductPagedList(pageNumber, criteria, ProductStatusEnum.ACTIVE);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#findAllBooks(java.lang.String)
   */
  public PagedList findAllBooks(SearchCriteria criteria, int pageNumber) {
    return this.getProductPagedList(pageNumber, criteria, ProductStatusEnum.ALL);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#findProductByTitle(int, java.lang.String)
   */
//  public PagedList findProductByTitle(int pageNumber, String searchCriteria) {
//    return this.getProductPagedList(pageNumber, new SearchCriteria("name", searchCriteria));
//  }
  

  public PagedList getProductPagedList(int pageNumber, SearchCriteria searchCriteria, ProductStatusEnum productStatusEnum) {
    int objectsPerPage = this.getProductListPageSize();
    //if null, create a new empty criteria
    if (searchCriteria == null) {
      searchCriteria = new SearchCriteria();
    }
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)*objectsPerPage;
    PagedList pagedList = productDao.search(start, objectsPerPage, searchCriteria, productStatusEnum);
    //PagedList pagedList = productDao.search(start, objectsPerPage, searchCriteria);
    pagedList.setObjectsPerPage(objectsPerPage);
    pagedList.setPageNumber(pageNumber);
    
    return pagedList;
  }

  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveProductInstance(com.sparrow.domain.ProductInstance)
   */
  public ProductInstance saveProductInstance(ProductInstance productInstance) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Adding instance for product: " + productInstance.getProduct().getName());
      System.out.println("Adding instance for product: " + productInstance.getProduct().getName());
    }
    if (productInstance.isNew()) {
      Product product = productInstance.getProduct();
      //increase the count of num in stock and available in stock by 1
      product.setNumInStock(product.getNumInStock() + 1);
      product.setAvailableInStock(new Short((short)(product.getAvailableInStock().intValue() + 1)));
      productInstance.setStatus(ProductInstanceStatusEnum.AVAILABLE.getValue());
    }
    
    ProductInstance savedProdInstance = productDao.saveProductInstance(productInstance);
    
    return savedProdInstance;
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#saveProductInstance(com.sparrow.domain.Product)
   */
  public ProductInstance saveProductInstance(Product product) {
    return saveProductInstance(new ProductInstance(product));
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductInstanceById(int)
   */
  public ProductInstance getProductInstanceById(int id) {
    return productDao.getProductInstanceById(id);
  }
  
//  /**
//   * Get a List of all books by Author, sorted by book name.
//   * @param author
//   * @return
//   * @since 1.0
//   */
//  public List getBooksByAuthor(Author author) {
//    return (List)productDao.getProductsByAuthor(author, ProductType.BOOK);
//  }
  
  private void saveImageToFileSystem(MultipartFile imageFile, File file) {
    try {
      imageFile.transferTo(file);  
    } catch (IOException ioe) {
      logger.error("Exception in saving file" + file.getAbsolutePath(), ioe);
      throw new RuntimeException("Exception in saving file" + file.getAbsolutePath(), ioe);
    }

  }
  
  /**
   * Given a Product and Set of Category, saves the ProductCategory associations.
   * It also delete the {@link ProductCategory}s that are no longer selected.
   * 
   * @param product
   * @param categories Set of Category
   * @since 1.0
   */
  private void addProductToCategories(Product product, Set categories) {
    //get the primary category id for this product and set the primary category
    int primaryCategoryId = product.getPrimaryCategoryId();
    Category primaryCategory = categoryService.getCategoryById(primaryCategoryId);
    
    //temp is the current list of product Categories
    Set temp = new HashSet();
    //save product categories
    Iterator itr = categories.iterator();
    while (itr.hasNext()) {
      Category category = (Category)itr.next();
      ProductCategory productCategory = new ProductCategory(category, product);
      if (category.equals(primaryCategory)) {
        productCategory.setIsPrimary("Y");
      }
      categoryService.saveProductCategory(productCategory);
      temp.add(productCategory);
    }
    
    //now delete product categories that are no longer selected
    Set productCategories = product.getProductCategories();

    Iterator itr0 = productCategories.iterator();
    while (itr0.hasNext()) {
      ProductCategory productCategory = (ProductCategory) itr0.next();
      //if a product category is not in the current list of ProductCategories, then delete it.
      if (!temp.contains(productCategory)) {
        categoryService.deleteProductCategory(productCategory);  
      }
      
    }
    
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductsByCategory(com.sparrow.domain.Category)
   */
  public List getProductsByCategory(Category category) {
    return (List)productDao.getProductsByCategory(category);
  }
  
  /* (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductsByCategory(int)
   */
  public List getProductsByCategory(int categoryId) {
    return (List)productDao.getProductsByCategory(categoryId);
  }
  
  public PagedList getRecentlyAddedProducts(int pageNumber) {
    int objectsPerPage = this.getProductListPageSize();
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)*objectsPerPage;
    PagedList productList = productDao.getRecentlyAddedProducts(start, objectsPerPage);
    productList.setObjectsPerPage(objectsPerPage);
    productList.setPageNumber(pageNumber);
    
    return productList;
    
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductsPagedListByCategory(int, int)
   */
  public PagedList getProductsPagedListByCategory(int pageNumber, int categoryId) {
    int objectsPerPage = this.getProductListPageSize();
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)*objectsPerPage;
    PagedList productList = productDao.getProductsPagedListByCategory(start, objectsPerPage, categoryId);
    productList.setObjectsPerPage(objectsPerPage);
    productList.setPageNumber(pageNumber);
    
    return productList;

  }

  
 /*
  * (non-Javadoc)
  * @see com.sparrow.service.catalog.ProductService#saveProductList(java.util.List)
  */
  public List saveProductList(List productList) {
    List<String> errorProductList = new ArrayList<String>();
    Product savedProduct = null;
    
    Iterator itr = productList.iterator();
    while (itr.hasNext()) {
      Book product = (Book)itr.next();
      logger.info("About to save or update name:" + product.getName() + " ISBN:" + product.getIsbn());
      String isbn = product.getIsbn();
      //If Product Does not exist by ISBN, then create a new Product
      if (!checkByIsbnIfBookExists(isbn)) {
        logger.info("Book does not exist, adding a new Product");
        if (product.getDescription() != null && product.getDescription().length() > 4000) {
          product.setDescription(product.getDescription().substring(0, 4000));
          errorProductList.add(" : " + product.getIsbn() + " : " + product.getName() + " : " + "Product added but Description was truncated to 4000 chars");
        }
        initializeProduct(product);
        try {
          savedProduct = this.saveProduct(product);
          
          //if image exists for the product, then load it.
          loadProductImageFromTempDir(product, savedProduct);
          
        } catch (Exception e) {
          errorProductList.add(" : " + product.getIsbn() + " : " + product.getName() + " : " + "Product addition failed. Please see log file");
          logger.error(" : " + product.getIsbn() + " : " + product.getName() + " : " + "Product addition failed", e);
        }
      } else { //product already exists
        errorProductList.add(" : " + product.getIsbn() + " : " + product.getName() + " : " + "Product already exists");
      }
    }
    
    return errorProductList;
  }

  public void saveProductsFromXML() throws Exception {
    List productList = castorProductListUtil.getProductList();
    if (logger.isInfoEnabled()) {
      logger.info("Will save the product list now. Product Count=" + productList.size());
    }
    logger.info("\nAbout to load products list:" + productList.size());
    List<String> errorProductList = saveProductList(productList);
    logger.info("\nFollowing products could not be loaded: Count=" + errorProductList.size());
    StringBuffer stringBuf = new StringBuffer();
    for (String s: errorProductList) {
      logger.info(s);
      stringBuf.append(s + "\n");
      
    }
    logger.info("Buffer" + stringBuf.toString());

    //send email here to user who uploaded the list
    MailUtil.sendMail();
    
  }
  
  

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getBookByIsbn(java.lang.String)
   */
  public Product getBookByIsbn(String isbn) {
    return productDao.getBookByIsbn(isbn);
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#activateProduct(int)
   */
  public Product activateProduct(int productId) throws ProductNotAssignedToCategoryException {
    Product product = getProductById(productId);
    return activateProduct(product);
  }

  /**
   * 
   */
  public Product activateProduct(Product product) throws ProductNotAssignedToCategoryException {
    //check if product belongs to atleast one category...if not, thorw exception
    List categories = (List)categoryService.getCategoriesByProduct(product);
    if (categories.size() == 0) {
      throw new ProductNotAssignedToCategoryException("Product not assigned to any category. Product Id=" + product.getProductId());
    }
    product.setStatus(ProductStatusEnum.ACTIVE.toString());
    product.setActivationDate(new Date());
    product.setActivatedBy(GeneralUtils.getCurrentUserFromTLS());
    return saveProduct(product);
  }

  private boolean checkByIsbnIfBookExists(String isbn) {
    boolean exists = false;
    Product product = this.getBookByIsbn(isbn);
    if (product != null) {
      exists = true;
    }
    return exists;
  }
  
  private Product initializeProduct(Product product) {
    String space = " "; 
    //check author 
    List<String> castorAuthorList = product.getCastorAuthorList();
    if (castorAuthorList != null) {
      //We only get the first name from the list
      String fullName = castorAuthorList.get(0);
      Author existingAuthor = productDao.getAuthorByFullName(fullName);
      if (existingAuthor == null) {
        String lastName = null;
        String firstName = null;
        if (fullName.contains(space)) {
          lastName = fullName.substring(fullName.lastIndexOf(space) + 1);
          firstName = fullName.substring(0, fullName.lastIndexOf(space));  
        } else {
          firstName = fullName;
          lastName = fullName;
        }
        Author author = new Author(firstName, lastName);
        author.setFullName(fullName);
        product.setAuthor(author);
      } else {
        product.setAuthor(existingAuthor);
      }
    }
    
    return product;
  }
  
  /**
   * Loads the product image from the application temp dir to the productImages dir if it exists.
   * It then updates the image name and saves the product.
   * @param product
   * @param savedProduct
   * @since 1.0
   */
  private void loadProductImageFromTempDir(Product product, Product savedProduct) {
    String fullImageName = product.getCastorSmallImageName();
    if (product.getCastorSmallImageName() != null) {
      String imageName = fullImageName.substring(fullImageName.lastIndexOf("\\") + 1);
      File imageToLoad = new File(appTempDir + imageName);
      if (imageToLoad.exists()) {
        String smallImageType = StringUtils.substringAfterLast(imageName, ".");
        String smallImageName = savedProduct.getProductId() + ServiceConstants.SMALL_IMAGE_SUFFIX + smallImageType;
        File destFile = new File(absoluteContextPath 
                            + this.getImagesDir() + File.separator 
                            + smallImageName);
        boolean status = imageToLoad.renameTo(destFile);
        if (status) {
          savedProduct.setImageSmallName(smallImageName);
          this.saveProduct(savedProduct);
        }
      }
    }
  }

  private void saveUserSearch(SearchCriteria searchCriteria) {
    Integer userId = null;
    User user = GeneralUtils.getCurrentUserFromTLS();
    if (user != null) {
      userId = user.getUserId();
    }
    UserSearch userSearch = new UserSearch(searchCriteria.getCriteria(), new Date(), userId);
    userDao.saveUserSearch(userSearch);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductInstances(com.sparrow.domain.Product)
   */
  public void getProductInstances(Product product) {
    productDao.getProductInstances(product);
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getProductInstances(java.util.List)
   */
  public void getProductInstances(PagedList productList) {
    
    Iterator itr = productList.getList().iterator();
    
    while (itr.hasNext()) {
      Product product = (Product)itr.next();
      this.getProductInstances(product);
    }
    
  }

  public PagedList findActiveBooksByAuthor(Author author, int pageNumber) {
    int objectsPerPage = this.getProductListPageSize();
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)*objectsPerPage;
    //PagedList pagedList = productDao.search(start, objectsPerPage, searchCriteria, ProductStatusEnum.ACTIVE);
    PagedList pagedList = productDao.findActiveBooksByAuthor(start, objectsPerPage, author, ProductStatusEnum.ACTIVE);
    pagedList.setObjectsPerPage(objectsPerPage);
    pagedList.setPageNumber(pageNumber);
    
    return pagedList;    
    
  }

  public PagedList findBooksMostInDemand(int pageNumber) {
    //not sure why value of 250 is not getting assigned to objectsPerPage
    //it still gets a value of 50...so commenting out and using the constant directly
    //int objectsPerPage = ServiceConstants.BOOKS_IN_DEMAND_PAGED_LIST_SIZE;
    
    //start index for a page by subtracting 1
    //e.g. to get page 1, start index will be 0*objectsPerPage, 
    //to get page 2, start index will be 1*objectsPerPage
    int start = (pageNumber - 1)* ServiceConstants.BOOKS_IN_DEMAND_PAGED_LIST_SIZE;
    PagedList productsList = utilDao.findBooksMostInDemand(start, ServiceConstants.BOOKS_IN_DEMAND_PAGED_LIST_SIZE);
    productsList.setObjectsPerPage(ServiceConstants.BOOKS_IN_DEMAND_PAGED_LIST_SIZE);
    productsList.setPageNumber(pageNumber);
    
    //initialize Product with product objects and product instances
    Iterator itr = productsList.getList().iterator();
    while (itr.hasNext()) {
      BooksMostInDemandReportVO booksMostInDemandReportVO = (BooksMostInDemandReportVO)itr.next();
      int productId = booksMostInDemandReportVO.getProductId();
      Product product = this.getProductById(productId);
      this.getProductInstances(product);
      booksMostInDemandReportVO.setProduct(product);
    }
    
    return productsList;
  }

  public void addProductReview(ProductReview productReview) {
    int productId = productReview.getProductId();
    
    if (productId == 0) {
      throw new IllegalArgumentException("productId must be set to save a revew...productId is " + productId);
    }
    
    Product product = getProductById(productId);
    //set review date
    Date date = new Date();
    productReview.setReviewDate(date);
    
    //set review user if available
    User currentUser = GeneralUtils.getCurrentUserFromTLS();
    if (StringUtils.isBlank(productReview.getReviewerName())) {
      if (currentUser != null) {
        productReview.setReviewerName(currentUser.getFullname());
      } else { //set if user is not logged in and has not given reviewername
        productReview.setReviewerName("anonymous");
      }
    }
    
    product.getProductReviews().add(productReview);
    
    //send an email to admin to review comment
    this.getMailUtil().sendMailToAdmin("Review Added: " + product.getName() + " by " + product.getAuthor().getFullName()
                                      , productReview.toString());
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.service.catalog.ProductService#getOneRandomAvailableBook()
   */
  public List getOneRandomAvailableBook() {
    return productDao.findRandomAvailableBooks(1, GeneralUtils.getCurrentLoggedInUserFromTLSWithoutReload());
  }
  
  public List findBooksReadByOtherUsersWhoReadThisBook(final int numOfBooks, final User user, final Product book)  {
    
    return productDao.findBooksReadByOtherUsersWhoReadThisBook(numOfBooks, user, book);
  }
  
  
  
  
}
