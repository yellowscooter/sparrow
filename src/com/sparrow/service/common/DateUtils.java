package com.sparrow.service.common;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.sparrow.web.WebConstants;

/**
 * Utility class to return date formatters.
 * @author Manish Kumar
 * @since 1.0
 */
public class DateUtils {
  private static final DateFormat mediumDateformatter = DateFormat.getDateInstance(DateFormat.MEDIUM);
  private static final DateFormat mediumDateTimeformatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
  
  static {
    mediumDateformatter.setTimeZone(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    mediumDateTimeformatter.setTimeZone(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
  }

  public static DateFormat getMediumDateformatter() {
    return mediumDateformatter;
  }

  public static DateFormat getMediumDateTimeformatter() {
    return mediumDateTimeformatter;
  }
  
  public static Date addMonths(Date date, int unit) {
    GregorianCalendar dateCal = new GregorianCalendar(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    dateCal.setTime(date);
    dateCal.add(Calendar.MONTH, unit);
    
    return dateCal.getTime();
  }
  
  public static Date addDays(Date date, int unit) {
    GregorianCalendar dateCal = new GregorianCalendar(TimeZone.getTimeZone(WebConstants.IST_TIME_ZONE));
    dateCal.setTime(date);
    dateCal.add(Calendar.DAY_OF_MONTH, unit);
    
    return dateCal.getTime();
  }
}
