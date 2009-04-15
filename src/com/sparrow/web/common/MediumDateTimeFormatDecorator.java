package com.sparrow.web.common;

import java.text.DateFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.sparrow.service.common.DateUtils;


/**
 * ColumnDecorator for Display tag to format a date time in {@link DateFormat#MEDIUM} format.
 * 
 * @author manishk
 * @since 1.0
 */
public class MediumDateTimeFormatDecorator implements DisplaytagColumnDecorator {
  
  public Object decorate(Object arg0, PageContext arg1, MediaTypeEnum arg2) throws DecoratorException {
    String result = null;
    Date date = (Date) arg0;
    DateFormat formatter = DateUtils.getMediumDateTimeformatter();
    if (date != null) {
      result = formatter.format(date);
    }
    return result;
  }

}
