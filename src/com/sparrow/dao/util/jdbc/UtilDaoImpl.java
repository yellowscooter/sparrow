package com.sparrow.dao.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.sparrow.dao.util.UtilDao;
import com.sparrow.domain.BooksMostInDemandReportVO;
import com.sparrow.domain.PagedList;
import com.sparrow.domain.SearchVO;
import com.sparrow.domain.User;

public class UtilDaoImpl extends JdbcDaoSupport implements UtilDao {

  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.util.UtilDao#updateLoginDate(com.sparrow.domain.User, java.util.Date)
   */
  public void updateLoginDate(User user, Date date) {
    String sql = "update user set last_login_date=? where user_id=?";
    this.getJdbcTemplate().update(sql, new Object[] {date, new Integer(user.getUserId())});
  }
  
  
  public PagedList findBooksMostInDemand(final int start, final int objectsPerPage) {
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
    }
    PagedList pagedList = new PagedList();
    List booksInDemandList = this.getJdbcTemplate().query("SELECT count(q.product_id) cont, q.product_id, p.name " +
                                                                "FROM product_request q, product p " +
                                                                "where p.product_id = q.product_id " +
                                                                "group by product_id " +
                                                                "order by cont desc " +
                                                                "limit ?, ?"
                                                                , new Object[]{new Integer(start), new Integer(objectsPerPage)}
                                                                , new RowMapper() {
                                                                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                                        BooksMostInDemandReportVO booksMostInDemandReportVO = new BooksMostInDemandReportVO();
                                                                        booksMostInDemandReportVO.setCount(rs.getInt("cont"));
                                                                        booksMostInDemandReportVO.setProductId(rs.getInt("product_id"));
                                                                        booksMostInDemandReportVO.setProductName(rs.getString("name"));
                                                                        
                                                                        return booksMostInDemandReportVO;
                                                                    }
                                                                });
    
    pagedList.setList(booksInDemandList);
    int sizeOfBooksInDemandList = this.getJdbcTemplate().queryForInt("select count(*) from (SELECT count(q.product_id) cont, q.product_id, p.name " +
                                                                      "FROM product_request q, product p " +
                                                                      "where p.product_id = q.product_id " +
                                                                      "group by product_id) temp;");
    pagedList.setFullListSize(sizeOfBooksInDemandList);
    
    return pagedList;
  }


  /*
   * (non-Javadoc)
   * @see com.sparrow.dao.util.UtilDao#userSearch(int, int)
   */
  public PagedList userSearch(int start, int objectsPerPage) {
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
    }
    PagedList pagedList = new PagedList();
    List searchList = this.getJdbcTemplate().query("select search_criteria, u.username, u.firstname, u.lastname, us.search_date " +
                                                   "from user_search us " +
                                                   "left join user u on us.user_id = u.user_id " +
                                                   "order by search_date desc " +
                                                   "limit ?, ?;"
                                                   , new Object[]{new Integer(start), new Integer(objectsPerPage)}
                                                   , new RowMapper() {
                                                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                        SearchVO searchVO = new SearchVO();
                                                        searchVO.setSearch(rs.getString("search_criteria"));
                                                        searchVO.setUserName(rs.getString("username"));
                                                        searchVO.setFirstName(rs.getString("firstname"));
                                                        searchVO.setLastName(rs.getString("lastname"));
                                                        searchVO.setSearchDate(rs.getTimestamp("search_date"));
                                                        return searchVO;
                                                        }
                                                    });
    
    pagedList.setList(searchList);
    int searchListSize = this.getJdbcTemplate().queryForInt("select count(*) " +
                                                           "from user_search us " +
                                                           "left join user u on us.user_id = u.user_id;");
    pagedList.setFullListSize(searchListSize);
    
    return pagedList;
  }
  
  public PagedList userSearchCount(int start, int objectsPerPage) {
    if (logger.isDebugEnabled()) {
      logger.debug("Fetching user list with start index=" + start + " and objectsPerPage=" + objectsPerPage);
    }
    PagedList pagedList = new PagedList();
    List searchList = this.getJdbcTemplate().query("select count(search_criteria) cont, search_criteria " +
                                                   "from user_search group by search_criteria order by cont desc " +
                                                   "limit ?, ?"
                                                   , new Object[]{new Integer(start), new Integer(objectsPerPage)}
                                                   , new RowMapper() {
                                                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                                                        SearchVO searchVO = new SearchVO();
                                                        searchVO.setSearch(rs.getString("search_criteria"));
                                                        searchVO.setCount(rs.getInt("cont"));
                                                        return searchVO;
                                                        }
                                                    });
    
    pagedList.setList(searchList);
    int searchListSize = this.getJdbcTemplate().queryForInt("select count(*) from " +
                                                           "(select count(search_criteria) cont, search_criteria " +
                                                           "from user_search group by search_criteria) temp");
    pagedList.setFullListSize(searchListSize);
    
    return pagedList;
  }

  
  
}
