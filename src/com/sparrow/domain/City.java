package com.sparrow.domain;

// Generated Nov 4, 2007 9:17:18 AM by Hibernate Tools 3.2.0.b9

/**
 * City generated by hbm2java
 */
public class City implements java.io.Serializable {

  private int cityId;

  /*
   * The city name
   */
  private String city;

  public City() {
  }

  public City(int cityId, String city) {
    this.cityId = cityId;
    this.city = city;
  }

  public int getCityId() {
    return this.cityId;
  }

  public void setCityId(int cityId) {
    this.cityId = cityId;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((city == null) ? 0 : city.hashCode());
    result = PRIME * result + cityId;
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
    final City other = (City) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (cityId != other.cityId)
      return false;
    return true;
  }
  
  

}
