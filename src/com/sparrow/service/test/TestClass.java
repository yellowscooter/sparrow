package com.sparrow.service.test;

public class TestClass {

  /**
   * @param args
   * @since 1.0
   */
  public static void main(String[] args) {
    String aToZ="ABCDEF";
    for (int i = 0; i < aToZ.length(); i++) {
      String oneChar = aToZ.substring(i, i + 1);
      System.out.println(oneChar);
    }

  }

}
