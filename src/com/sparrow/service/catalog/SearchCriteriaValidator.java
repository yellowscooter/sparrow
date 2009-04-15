package com.sparrow.service.catalog;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sparrow.domain.SearchCriteria;


public class SearchCriteriaValidator implements Validator {
  private ResourceBundleMessageSource messageSource;
  
  public ResourceBundleMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public boolean supports(Class clazz) {
    return SearchCriteria.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    SearchCriteria criteria = (SearchCriteria)target;
    String strCriteria = StringUtils.trim(criteria.getCriteria());
    
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "criteria", "");
    
    if (strCriteria.length() < 4) {
      errors.reject("criteria");
    }
    
    //reading from resource bundle
    if (messageSource.getMessage("blank.search.msg", null, null).equalsIgnoreCase(strCriteria)) {
      errors.reject("criteria");
    }

  }

}
