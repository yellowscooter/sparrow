package com.sparrow.dao.catalog.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SQLCriterion;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sparrow.dao.catalog.ProductDao;
import com.sparrow.dao.catalog.ProductStatusEnum;
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
import com.sparrow.domain.UserRole;

/**
 * The ProductDao implementation class.
 * @author manishk
 * @since 1.0
 */
public class ProductDaoImpl extends HibernateDaoSupport implements ProductDao {
  private static final Log logger = LogFactory.getLog(ProductDaoImpl.class);
  
  private static final int AUTHOR_LIST_FETCH_SIZE = 3000;

  private int productCountInRecentlyAddedList;
  
  public int getProductCountInRecentlyAddedList() {
    return productCountInRecentlyAddedList;
  }

  public void setProductCountInRecentlyAddedList(int productCountInRecentlyAddedList) {
    this.productCountInRecentlyAddedList = productCountInRecentlyAddedList;
  }

  public Author saveAuthor(Author author) {
    //return (Author)this.getHibernateTemplate().merge(author);
    this.getHibernateTemplate().saveOrUpdate(author);
    return author;
  }
  
  public Collection getAllAuthors() {
    HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
    //  for reasons I do not understand, Hibernate is only fetching 15 records after tomcat has been running for a while
    //setting fetch size to see if this works.
    hibernateTemplate.setFetchSize(AUTHOR_LIST_FETCH_SIZE);
    hibernateTemplate.setMaxResults(AUTHOR_LIST_FETCH_SIZE);

    if (logger.isDebugEnabled()) {
      logger.debug(">>>Fetch Size = " + hibernateTemplate.getFetchSize());
    }
    return hibernateTemplate.find("from Author order by firstName, lastName");
  }
  
  public Author getAuthorById(int id) {
    return (Author)this.getHibernateTemplate().get(Author.class, new Integer(id));
  }
  
  public Author getAuthorByFullName(String fullName) {
    Author author = null;
    HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
    if (logger.isDebugEnabled()) {
      logger.debug(">>>Fetch Size = " + hibernateTemplate.getFetchSize());
    }
    List authorList = hibernateTemplate.findByNamedParam("from Author where lower(fullName) = :fullName", "fullName", fullName.toLowerCase());
    if (authorList.size() > 1) {
      throw new IllegalStateException("cannot have multiple authors with the same name:" + fullName);
    } else if (authorList.size() == 1){
      author = (Author)authorList.get(0);
    }
    return author;
  }
  
  

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.ProductDao#searchAuthor(com.sparrow.domain.SearchCriteria)
   */
  public List searchAuthor(SearchCriteria searchCriteria) {
    if (searchCriteria == null) {
      throw new IllegalArgumentException("Search criteria cannot be null");
    }
    String searchKey = searchCriteria.getSearchKey();
    String criteria = searchCriteria.getCriteria();
    
    if (searchKey == null || criteria == null) {
      throw new IllegalArgumentException("Search key and Criteria cannot be null");
    }
    
    HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
    //for reasons I do not understand, Hibernate is only fetching 15 records after tomcat has been running for a while
    //setting fetch size to see if this works.
    hibernateTemplate.setFetchSize(AUTHOR_LIST_FETCH_SIZE);
    hibernateTemplate.setMaxResults(AUTHOR_LIST_FETCH_SIZE);
    if (logger.isDebugEnabled()) {
      logger.debug(">>>Fetch Size = " + hibernateTemplate.getFetchSize() + " Should be 3000");
    }
    List authorList = hibernateTemplate.findByNamedParam("from Author where " +
                       "lower(" + searchKey + ") like :criteria order by lastName, firstName", "criteria", criteria.toLowerCase());    
    
    
    return authorList;
  }

  public Company saveCompany(Company company) {
    //return (Company)this.getHibernateTemplate().merge(company);
    this.getHibernateTemplate().saveOrUpdate(company);
    return company;
  }
  
  public Company getCompanyById(int id) {
    return (Company)this.getHibernateTemplate().get(Company.class, new Integer(id));
  }
  
  public Collection getAllCompanies() {
    return this.getHibernateTemplate().find("from Company order by companyName");
  }

  public Product saveProduct(Product product) {
    //return (Product)this.getHibernateTemplate().merge(product);
    this.getHibernateTemplate().saveOrUpdate(product);
    return product;
  }

  public void deleteProduct(Product product) {
    this.getHibernateTemplate().delete(product);
  }
  
  public Product getProductById(int id) {
    return (Product)this.getHibernateTemplate().get(Product.class, new Integer(id));
  }

  public Collection getAllBooks() {
    return this.getHibernateTemplate().loadAll(Book.class);
  }

  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.ProductDao#search(int, int, com.sparrow.domain.SearchCriteria, com.sparrow.dao.catalog.ProductStatusEnum)
   */
  public PagedList search(final int start, final int objectsPerPage, final SearchCriteria searchCriteria, final ProductStatusEnum status) {
    if (status == null) {
      throw new IllegalArgumentException("Status cannot be null for search");
    }
    
    if (searchCriteria == null) {
      throw new IllegalArgumentException("Search criteria cannot be null");
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
      logger.debug("Search criteria:" + searchCriteria.toString());
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        

        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(Product.class, "product")
                            .createAlias("productSearch", "productSearch", CriteriaSpecification.INNER_JOIN);
        if (!ProductStatusEnum.ALL.toString().equals(status.toString())) {
          criteria.add(Restrictions.eq("status", status.toString()));
        }
                                                                      
        if (searchCriteria.getCriteria() != null) {
          criteria.add(Restrictions.sqlRestriction(" MATCH (product_name, product_desc, product_isbn, author_full_name) AGAINST (?)", searchCriteria.getCriteria(), Hibernate.STRING));
        }

        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        pagedList.setFullListSize(fullListSize);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        
        criteria.setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });

    
  }

  public ProductInstance saveProductInstance(ProductInstance productInstance) {
    //return (ProductInstance)this.getHibernateTemplate().merge(productInstance);
    this.getHibernateTemplate().saveOrUpdate(productInstance);
    return productInstance;
  }

  public ProductInstance getProductInstanceById(int id) {
    return (ProductInstance)this.getHibernateTemplate().get(ProductInstance.class, new Integer(id));
  }
  
  
  
  
  

//  public Collection getProductsByAuthor(Author author, ProductType productType) {
////    return this.getHibernateTemplate().findByNamedParam(
////                                        "from Product where author_id = :authorId and product_type= :productType" +
////                                        " order by name"
////                                        , new String[] {"authorId", "productType"}
////                                        , new Object[] {author.getAuthorId(), productType.getProductType()});
//    DetachedCriteria criteria = DetachedCriteria.forClass(Book.class);
//    criteria.addOrder(Order.asc("name"));
//    
//    return this.getHibernateTemplate().findByCriteria(criteria);
//  }
  
  public Collection getProductsByCategory(Category category) {
    return getProductsByCategory(category.getCategoryId());
  }
  
  public Collection getProductsByCategory(int categoryId) {
    return this.getHibernateTemplate().find("select product from Product product " +
                                            "join product.productCategories prodCat" +
                                            " where product.status='" + ProductStatusEnum.ACTIVE.toString() + 
                                            "' and prodCat.id.categoryId = ? ", categoryId);
  }
  
  
  

  public PagedList getRecentlyAddedProducts(final int start, final int objectsPerPage) {
        
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        
        
        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(Product.class)
                                   .add(Restrictions.eq("status", ProductStatusEnum.ACTIVE.toString()));
                
        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        //we just want to see last productCountInRecentlyAddedList number of books
        pagedList.setFullListSize(productCountInRecentlyAddedList);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        criteria.addOrder(Order.desc("activationDate"))
                .addOrder(Order.desc("productId"))
                .setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });

  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.ProductDao#getBookByIsbn(java.lang.String)
   */
  public Product getBookByIsbn(String isbn) {
    Product product = null;
    List productList = this.getHibernateTemplate().find("select product from Product product " +
                                          " where isbn = ? ", isbn);
    
    if (productList.size() > 1) {
      String msg = "Found " + productList.size() + " products for ISBN " + isbn;
      logger.fatal(msg);
      throw new IllegalStateException(msg);
    } else if (productList.size() == 1) {
      product = (Book)productList.get(0);
    }
    
    return product;
  }
  
  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.user.UserDao#getUserList(int, int)
   */
  public PagedList getProductsPagedListByCategory(final int start, final int objectsPerPage, final int categoryId) {
    
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
      logger.debug("Category Id:" + categoryId);
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        
        
        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(Product.class)
                .createAlias("productCategories", "prodCat")
                .createAlias("author", "author", CriteriaSpecification.LEFT_JOIN)
                .add(Restrictions.eq("status", ProductStatusEnum.ACTIVE.toString()))
                .add(Restrictions.eq("prodCat.id.categoryId", categoryId));
        
        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        pagedList.setFullListSize(fullListSize);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        //we want to display products which have this category as primary,
        //and then display non primary products.
        criteria.addOrder(Order.desc("prodCat.isPrimary"))
                .addOrder(Order.asc("author.lastName"))
                .setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });

    
   
  }

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.catalog.ProductDao#getProductInstances(com.sparrow.domain.Product)
   */
  public void getProductInstances(Product product) {
    List productInstances = this.getHibernateTemplate().findByNamedParam("from ProductInstance where product = :product ", "product", product );
    product.setProductInstances(productInstances);
    
  }

  public PagedList findActiveBooksByAuthor(final int start, final int objectsPerPage, final Author author, final ProductStatusEnum status) {
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
      logger.debug("Author Id:" + author.getAuthorId());
    }

    return (PagedList)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        PagedList pagedList = new PagedList();
        
        
        //will reuse this criteria in getting the total count and the paged list
        Criteria criteria = session.createCriteria(Product.class)
                .createAlias("author", "author", CriteriaSpecification.LEFT_JOIN)
                .add(Restrictions.eq("status", status.toString()))
                .add(Restrictions.eq("author.authorId", author.getAuthorId()));
        
        //get the total count
        Criteria countCriteria = criteria.setProjection(Projections.projectionList().add(Projections.rowCount()));
        List fullList = countCriteria.list(); 
        int fullListSize = (Integer)fullList.get(0);
        pagedList.setFullListSize(fullListSize);
        
        //reset criteria. clear projection, change to root entity
        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.ROOT_ENTITY);
        
        criteria.addOrder(Order.desc("name"))
                .setMaxResults(objectsPerPage)
                .setFirstResult(start);
        
        List userList = criteria.list();
        pagedList.setList(userList);
        
        if (logger.isDebugEnabled()) {
          logger.debug("Fetched partial user list. Full list size=" + fullListSize);
        }
        
        return pagedList;
      }
    });
  }

  public List findRandomAvailableBooks(final int numOfBooks, final User user) {
    //HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
    //hibernateTemplate.setMaxResults(numOfBooks);
//    return hibernateTemplate.find("select product from Product product " +
//                                             " where product.status='" + ProductStatusEnum.ACTIVE.toString() + 
//                                             "' and product.availableInStock > 1 " +
//                                             " and product.imageSmallName IS NOT NULL" +
//                                             " Order By RAND() ");
//    
    
    return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        List list = null;
        String sql = "select {product.*} from product product " +
                      " where product.status='" + ProductStatusEnum.ACTIVE.toString() + 
                      "' and product.available_in_stock > 0 " +
                      " and product.image_small_name IS NOT NULL";
        
        if (user != null) {
          sql = sql + " and product.product_id NOT IN (select pr.product_id from product_request pr where pr.user_id = " + user.getUserId() + 
                      " UNION select pi.product_id from product_instance pi, user_product up where pi.product_instance_id = up.product_instance_id and up.user_id =  " + user.getUserId() +  
                      " UNION select pi.product_id from product_instance pi, user_product_history up where pi.product_instance_id = up.product_instance_id and up.user_id = " + user.getUserId() + " ) ";
        }
        sql = sql + " Order By RAND() ";
        SQLQuery query = session.createSQLQuery(sql);
        
        query.addEntity("product", Product.class);
        //query.addEntity("uzer", User.class);
        query.setMaxResults(numOfBooks);
        list = query.list();
        
        return list;
      }
    });
    
  }

  
  public List findBooksReadByOtherUsersWhoReadThisBook(final int numOfBooks, final User user, final Product book) {
    return (List)this.getHibernateTemplate().execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        List list = null;
        String userSql = "SELECT user_id FROM user_product_history u, product_instance pi where ";
        if (user != null) {
          userSql = userSql + " u.user_id != " + user.getUserId() + " and ";  
        }
        
        userSql = userSql + " pi.product_id = " + book.getProductId() +
                          " and u.product_instance_id = pi.product_instance_id " +
                          " Order By Rand()";
        
        SQLQuery randomUserQuery = session.createSQLQuery(userSql);
        randomUserQuery.setMaxResults(1);
        //randomUserQuery.setEntity("user_id", Integer.class);
        List userList = randomUserQuery.list();
        
        int randomUser;
        if (userList.size() == 1) {
          randomUser = (Integer)userList.get(0);
        } else {
          //if no user is found who has read this book, we return an empty list
          return new ArrayList();
        }
        
        String sql = "select {product.*} from product product , user_product_history h, product_instance pi " +
                      " where h.user_id = " + randomUser +
                      " and product.status='" + ProductStatusEnum.ACTIVE.toString() + "'" + 
                      " and product.available_in_stock > 0 " +
                      " and product.image_small_name IS NOT NULL" +
                      " and product.product_id != " + book.getProductId() + 
                      " and h.product_instance_id = pi.product_instance_id " +
                      " and pi.product_id = product.product_id";
        if (user != null) {
          sql = sql + " and product.product_id NOT IN (select pr.product_id from product_request pr where pr.user_id = " + user.getUserId() + 
          " UNION select pi.product_id from product_instance pi, user_product up where pi.product_instance_id = up.product_instance_id and up.user_id =  " + user.getUserId() +  
          " UNION select pi.product_id from product_instance pi, user_product_history up where pi.product_instance_id = up.product_instance_id and up.user_id = " + user.getUserId() + " ) ";  
        }
                      
          sql = sql + " Order By Rand()";
        
        SQLQuery query = session.createSQLQuery(sql);
        
        query.addEntity("product", Product.class);
        query.setMaxResults(numOfBooks);
        list = query.list();
        
        return list;
      }
    });
    
  }

  
  
}
