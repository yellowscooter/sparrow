package com.sparrow.domain;

import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Book extends Product {
  
  private String isbn;
  
  /**
   * format is PAPERBACK, HARDCOVER etc
   */
  private String format;

  /**
   * number of pages if productType=BOOK
   */
  private int numOfPages;

  public Book() {
    super();
  }
  
  /**
   * Full constructor.
   * @param author
   * @param company
   * @param name
   * @param description
   * @param isbn
   * @param length
   * @param width
   * @param height
   * @param numInStock
   * @param availableInStock
   * @param imageSmall
   * @param imageLarge
   * @param productCategories
   * @since 1.0
   */
  public Book(Author author, Company company, String name, String description, String isbn
      , Float length, Float width, float height, int numInStock, Short availableInStock
      , String imageSmall, String imageLarge) {
    super(author, company, name, description, length, width, height
        , numInStock, availableInStock
        , imageSmall, imageLarge);
    this.isbn = isbn;
    this.format = format;
    this.numOfPages = numOfPages;
  }

  
  
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getFormat() {
    return this.format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public int getNumOfPages() {
    return this.numOfPages;
  }

  public void setNumOfPages(int numOfPages) {
    this.numOfPages = numOfPages;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append(super.toString())
                                    .append("format", this.format)
                                    .append("isbn", this.isbn)
                                    .append("numOfPages", this.numOfPages)
                                    .toString();
  }



}
