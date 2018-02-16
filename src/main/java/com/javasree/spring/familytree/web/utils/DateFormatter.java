package com.javasree.spring.familytree.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date>{
	
	private final String defaultDateFormat = "dd-MM-yyyy";
	
	@Override
	public String print(Date dateObject, Locale arg1) {
		return new SimpleDateFormat(defaultDateFormat).format(dateObject);
	}

	@Override
	public Date parse(String arg0, Locale arg1) throws ParseException {
		return new SimpleDateFormat(defaultDateFormat).parse(arg0);
	}

}
