package com.sparrow.domain;

import java.io.Serializable;

/**
 * Simple object to hold the key.
 * @author manishk
 * @since 1.0
 */
public class GuardKeyHolder implements Serializable {
  private String key;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
  
  

}
