package com.sparrow.service.util;

import java.io.IOException;

import com.sparrow.service.AbstractServiceTests;


public class GeneralUtilsTests extends AbstractServiceTests {
  
  public void testGetKeywordsFromString() throws IOException {
    String keywords = GeneralUtils.getKeywordsFromString("The Way it Should Have Been   Georgia Bockoven");
    System.out.println(keywords);
    assertEquals("Keyword should be: way-should-have-been-georgia",  "way-should-have-been-georgia", keywords);
   
    keywords = GeneralUtils.getKeywordsFromString("The Tales Of Beedle The Bard J. K. Rowling");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "tales-beedle-bard-rowling", keywords);
    
    //test for all illegal chars in urls # % & * { } \ : < > ? / +
    
    keywords = GeneralUtils.getKeywordsFromString("Acts of Mal#ice  Perri O'Shaughnessy: A great book");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "acts-mal-ice-perri-oshaughnessy", keywords);
    
    //% & *
    keywords = GeneralUtils.getKeywordsFromString("Acts of Mal%ice  Perri O'Shaugh*ne>ssy& A great book");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "acts-mal-ice-perri-oshaugh", keywords);
    
    
    keywords = GeneralUtils.getKeywordsFromString("A?cts of Mal?ice  Perri O'Shaugh/ne+ssy: A great book");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "cts-mal-ice-perri-oshaugh", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString(" N Is for Noose  Sue Grafton");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "noose-sue-grafton", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Lightning Strikes  V. C. Andrews");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "lightning-strikes-andrews", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("McNally's Dilemma   Vincent Lardo");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "mcnally-dilemma-vincent-lardo", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Thrill!  Jackie Collins");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "thrill-jackie-collins", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Pretend You Don't See Her  Mary Higgins Clark");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "pretend-you-dont-see-her", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("The Rainmaker  John Grisham");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "rainmaker-john-grisham", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Lighthouse (St. Simons Trilogy, Volume one)  Eugenia Price");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "lighthouse-simons-trilogy-volume-one", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Sunset In St. Tropez - Large Print Edition   Danielle Steel");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "sunset-tropez-large-print-edition", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Losing Isaiah  Seth J. Margolis");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "losing-isaiah-seth-margolis", keywords);
    
    keywords = GeneralUtils.getKeywordsFromString("Tears of Yesterday   Mary Lynn Baxter");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "tears-yesterday-mary-lynn-baxter", keywords);    
    
    keywords = GeneralUtils.getKeywordsFromString("Deadly Intent (Nancy Drew Casefiles, Case 2)   Carolyn Keene");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "deadly-intent-nancy-drew-casefiles", keywords);   
    
    keywords = GeneralUtils.getKeywordsFromString(" Jeffrey and the Third-Grade Ghost#4 Pet Day Surprise  Megan Stine");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "jeffrey-third-grade-ghost-pet", keywords);   
    
    keywords = GeneralUtils.getKeywordsFromString("Outliers: The Story Of Success   Malcolm Gladwell");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "outliers-story-success-malcolm-gladwell", keywords);  
    
    
    keywords = GeneralUtils.getKeywordsFromString("Outliers: The Story Of Success   Malcolm Gladwell");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "outliers-story-success-malcolm-gladwell", keywords);     
    
    keywords = GeneralUtils.getKeywordsFromString("To Know a Woman  Amos Oz");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "know-woman-amos", keywords);     
    
    keywords = GeneralUtils.getKeywordsFromString("The Martha Rules: 10 Essentials for Achieving Success as You Start, Grow, or Manage a Business   Martha Stewart");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "martha-rules-essentials-achieving-success", keywords);   
    
    //what if no keywords exist
    keywords = GeneralUtils.getKeywordsFromString("The of the a b c in");
    System.out.println(keywords);
    assertEquals("Incorrect keywords",  "a", keywords);   
    
    
  }
}
