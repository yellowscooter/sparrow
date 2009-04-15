package com.sparrow.dao.user;

/**
 * Enumeration for Verified/Unverified status of a User account.
 * 
 * @author manishk
 * @since 1.0
 */
public enum UserStatusEnum {
  //A-Active,I-Inactive,S-Suspended,T-Terminated
  ACTIVE ("A"),
  INACTIVE ("I"),
  SUSPENDED ("S"),
  TERMINATED ("T");
  
  /**
   * The code value of an enum type...this is used in comparasions
   */
  private final String value;
  
  private UserStatusEnum(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
  public static UserStatusEnum getEnumForValue(String value) {
    if (value == null) return null;
    UserStatusEnum userStatusEnum = null;
    
    if (value.equals(ACTIVE.value)) {
      userStatusEnum = ACTIVE;
    } else if (value.equals(INACTIVE.value)) {
      userStatusEnum = INACTIVE;
    } else if (value.equals(SUSPENDED.value)) {
      userStatusEnum = SUSPENDED;
    } else if (value.equals(TERMINATED.value)) {
      userStatusEnum = TERMINATED;
    }
    
    return userStatusEnum;
  }
  
}
