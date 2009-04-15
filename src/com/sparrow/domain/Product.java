package com.sparrow.domain;

// Generated May 11, 2007 4:25:10 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.sparrow.dao.catalog.ProductStatusEnum;

/**
 * Product domain object.
 * @author manishk
 * @since 1.0
 */
public abstract class Product implements java.io.Serializable {
  
  private int productId;
  
  private Author author;
  
  /**
   * This List is used to get a list of authors from the xml file (a book can have multiple authors).
   * It was added since castor expects a collection element if an xml element occurs multiple times.
   * This attribute should not be used anywhere else in code, and is not populated when a Product is 
   * not populated in any other scenario.
   */
  private List<String> castorAuthorList;

  private Company company;

  /**
   * The product title
   */
  private String name;

  private String description;

  
  /**
   * The length. 
   */
  private float length;

  /**
   * The width.
   */
  private float width;

  /**
   * The height.
   */
  private float height;

  /**
   * The total count of this Product in stock
   */
  private int numInStock;
  
  /**
   * The count of Product currently available.
   * 
   */
  private Short availableInStock = new Short((short)0);

  /**
   * Small image name. If null, no image is available.
   */
  private String imageSmallName;
  
  /**
   * This is the name of the images as fetched from the xml file read using Castor.
   * This variable should not be use anywhere else, other than reading image name from Product xml file.
   * This property is not initialized when Product is loaded.
   */
  private String castorSmallImageName;
  
  /**
   * The small image holder.
   */
  private MultipartFile smallImage;
  
  /**
   * Name of large image. Null means no image is available
   */
  private String imageLargeName;
  
  /**
   * The large image holder.
   */
  private MultipartFile largeImage;
  

//  /**
//   * Set of Categories to which this Product belongs.
//   */
  //private Set productCategories = new HashSet(0);
  
  /**
   * This is the set of user selected categories that this product will 
   * belong to.
   */
  private Set categories;
  
  /**
   * This is the primary category of the Product
   */
  private int primaryCategoryId;
  
  private Set productCategories = new HashSet();
  
  /**
   * This is the instance Id that is created when the Product is saved for the first time.
   * This variable is just set the first time the Product is saved,
   * and is not set when Product is retreived.
   */
  private int productInstanceId;
  
  private String status;
  
  /**
   * Set of {@link ProductInstance}s of this Product.
   */
  private List productInstances;
  
  /**
   * Search related information for freetext search
   */
  private ProductSearch productSearch;
  
  private User activatedBy;
  
  private Date activationDate;
  
  /**
   * List of all Reviews that have been attached to a this product
   */
  private Set productReviews = new HashSet();
  

  public Product() {
  }

  /**
   * Full Constructor...will be called by a subclass to create
   * a concrete instance.
   * @param author
   * @param company
   * @param name
   * @param description
   * @param length
   * @param width
   * @param height
   * @param numInStock
   * @param imageSmall
   * @param imageLarge
   * @param productCategories
   * @since 1.0
   */
  public Product(Author author,
      Company company, String name, String description,
      float length, float width, float height,
      int numInStock, Short availableInStock, String imageSmall, String imageLarge) {
    this.author = author;
    this.company = company;
    this.name = name;
    this.description = description;
    this.length = length;
    this.width = width;
    this.height = height;
    this.numInStock = numInStock;
    this.availableInStock = availableInStock;
    this.imageSmallName = imageSmall;
    this.imageLargeName = imageLarge;
    //this.productCategories = productCategories;
  }

  /**
   * If returns true, this is a transient instance (has not been persisted, or new instance)
   * If returns false, this is a persisted or detached instance (for edit)
   * @return
   * @since 1.0
   */
  public boolean isNew() {
    return (this.productId == 0);
  }
  
  /**
   * Returns true if the status of a {@link Product} is ACTIVE
   * @return
   * @since 1.0
   */
  public boolean isActive() {
    return (ProductStatusEnum.ACTIVE.toString().equals(getStatus()));
  }
  
  public int getProductId() {
    return this.productId;
  }

  private void setProductId(int productId) {
    this.productId = productId;
  }

  public Author getAuthor() {
    return this.author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }
  
  public List<String> getCastorAuthorList() {
    return castorAuthorList;
  }

  public void setCastorAuthorList(List<String> castorAuthorList) {
    this.castorAuthorList = castorAuthorList;
  }

  public Company getCompany() {
    return this.company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  
  public float getLength() {
    return this.length;
  }

  public void setLength(float length) {
    this.length = length;
  }

  public float getWidth() {
    return this.width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return this.height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public int getNumInStock() {
    return this.numInStock;
  }

  
  public Short getAvailableInStock() {
    return availableInStock;
  }

  public void setAvailableInStock(Short availableInStock) {
    this.availableInStock = availableInStock;
  }

  public void setNumInStock(int numInStock) {
    this.numInStock = numInStock;
  }

  public String getImageSmallName() {
    return this.imageSmallName;
  }
  
  public String getCastorSmallImageName() {
    return castorSmallImageName;
  }

  public void setCastorSmallImageName(String castorSmallImageName) {
    this.castorSmallImageName = castorSmallImageName;
  }

  public void setImageSmallName(String imageSmall) {
    this.imageSmallName = imageSmall;
  }

  public String getImageLargeName() {
    return this.imageLargeName;
  }

  public void setImageLargeName(String imageLarge) {
    this.imageLargeName = imageLarge;
  }

//  public Set getProductCategories() {
//    return this.productCategories;
//  }

//  public void setProductCategories(Set productCategories) {
//    this.productCategories = productCategories;
//  }

  
  public MultipartFile getLargeImage() {
    return largeImage;
  }

  public void setLargeImage(MultipartFile largeImage) {
    this.largeImage = largeImage;
  }

  public MultipartFile getSmallImage() {
    return smallImage;
  }

  public void setSmallImage(MultipartFile smallImage) {
    this.smallImage = smallImage;
  }

  public Set getCategories() {
    return categories;
  }

  public void setCategories(Set categories) {
    this.categories = categories;
  }

  /**
   * returns the primary category id of a product. 
   * @return
   * @since 1.0
   */
  public int getPrimaryCategoryId() {
    //if value is set, return, else get from ProductCategory list
    if (primaryCategoryId > 0) {
      return primaryCategoryId;
    }
    
    Iterator itr = (Iterator)productCategories.iterator();
    while (itr.hasNext()) {
      ProductCategory productCategory = (ProductCategory)itr.next();
      if ("Y".equals(productCategory.getIsPrimary())) {        
        primaryCategoryId =  productCategory.getCategory().getCategoryId();
      }
    }
    
    return primaryCategoryId;
  }

  public void setPrimaryCategoryId(int primaryCategoryId) {
    this.primaryCategoryId = primaryCategoryId;
  }

  public Set getProductCategories() {
    return productCategories;
  }

  public void setProductCategories(Set productCategories) {
    this.productCategories = productCategories;
  }

  public int getProductInstanceId() {
    return productInstanceId;
  }

  public void setProductInstanceId(int productInstanceId) {
    this.productInstanceId = productInstanceId;
  }
  
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
  public List getProductInstances() {
    return productInstances;
  }

  public void setProductInstances(List productInstances) {
    this.productInstances = productInstances;
  }
  
  public ProductSearch getProductSearch() {
    return productSearch;
  }

  public void setProductSearch(ProductSearch productSearch) {
    this.productSearch = productSearch;
  }
  
  

  public User getActivatedBy() {
    return activatedBy;
  }

  public void setActivatedBy(User activatedBy) {
    this.activatedBy = activatedBy;
  }

  public Date getActivationDate() {
    return activationDate;
  }

  public void setActivationDate(Date activationDate) {
    this.activationDate = activationDate;
  }
  
  public Set getProductReviews() {
    return productReviews;
  }

  public void setProductReviews(Set productReviews) {
    this.productReviews = productReviews;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + productId;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Product other = (Product) obj;
    if (productId != other.productId)
      return false;
    return true;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("name", this.getName())
                                    .append("description", this.getDescription())
                                    .append("productId", this.getProductId())
                                    .append("author", this.getAuthor()).toString();
  }
  
  
  
  

}
