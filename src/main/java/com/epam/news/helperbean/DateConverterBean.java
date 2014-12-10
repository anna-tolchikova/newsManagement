package com.epam.news.helperbean;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public final class DateConverterBean {

	private static Logger LOG = Logger.getLogger(DateConverterBean.class);
	
	private DateConverterBean() {
	}
	
	public static String convertDateToStringByLocale(Date date, Locale locale) {
		String stringDate = null;
		ResourceBundle bundle = null;
		bundle = ResourceBundle.getBundle("ApplicationResource", locale);
		String datePattern = bundle.getString("layout.date.pattern");
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		if (date != null) {
			stringDate = formatter.format(date);
		}
		else {
			stringDate = formatter.format(new Date(System.currentTimeMillis()));
		}
				
		return stringDate;

	}
	
	public static Date convertStringToDateByLocale(String stringDate, Locale locale) {
		Date date = null;
		ResourceBundle bundle = null;
		bundle = ResourceBundle.getBundle("ApplicationResource", locale);
		String datePattern = bundle.getString("layout.date.pattern");
		SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
		if (stringDate != null) {
			try {
				date = new Date(formatter.parse(stringDate).getTime());
			} catch (ParseException e) {
				LOG.error("Error during conversion String into Date object." + e);
			}			
		}
		return date;
	}
}
