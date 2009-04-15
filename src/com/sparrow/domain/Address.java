package com.sparrow.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

// Generated May 25, 2007 1:51:18 PM by Hibernate Tools 3.2.0.b9

/**
 * This class represents a complete user Address.
 */
public class Address implements java.io.Serializable {

  private int addressId;

  private String street1;

  private String street2;

  private String city;

  private String state;

  private String postalCode;

  public Address() {
  }

  public Address(String street1, String street2, String city, String state, String postalCode) {
    this.street1 = street1;
    this.street2 = street2;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
  }

  public int getAddressId() {
    return this.addressId;
  }

  private void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  public String getStreet1() {
    return this.street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getStreet2() {
    return this.street2;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }
  
  /**
   * Returns true if all fields of the object are null.
   * 
   * @return
   * @since 1.0
   */
  public boolean isEmpty() {
    return (this.street1 == null 
            && this.street2 == null
            && this.city == null
            && this.state == null
            && this.postalCode == null);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(street1).append("\n");
    buffer.append(street2).append("\n");
    buffer.append(city).append("\n");
    buffer.append(state).append("-");
    buffer.append(postalCode);
    return buffer.toString();
  }
  
  

 
  
  
  
  

}
