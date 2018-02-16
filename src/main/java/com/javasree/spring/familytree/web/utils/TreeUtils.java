package com.javasree.spring.familytree.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.javasree.spring.familytree.model.profile.Profile;
import com.javasree.spring.familytree.web.dto.TreeNode;

public class TreeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TreeUtils.class);
	
	private TreeUtils(){
		super();
	}
	public static TreeNode getTreeNodeFromProfile(Profile profile){
		TreeNode node = new TreeNode();
		node.setId(profile.getProfileId());
		node.setTitle(profile.getFirstName());
		node.setDescription(profile.getProfileName());
		node.setParent(profile.getParentId());
		node.setGender(profile.getGender());
		return node;
	}
	
	public static int getAge(Date dateOfBirth){
		int age = 0;
		if(dateOfBirth!=null){
			Calendar currentDate = Calendar.getInstance();
			Calendar birthDate = Calendar.getInstance();
			birthDate.setTime(dateOfBirth);
			/*if(dateOfBirth.after(currentDate.getTime())){
				throw new IllegalArgumentException("Can not born in future");
			}*/
			int currentYear = currentDate.get(Calendar.YEAR);
			int birthYear = birthDate.get(Calendar.YEAR);
			age = currentYear - birthYear;
			
			int currentMonth = currentDate.get(Calendar.MONTH);
			int birthMonth = birthDate.get(Calendar.MONTH);
			if(birthMonth > currentMonth){
				age --;
			}
			else if(birthMonth == currentMonth){
				int currentDayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);
				int birthDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
				if(birthDayOfMonth > currentDayOfMonth){
					age--;
				}
			}
		}
		return age;
	}
	
	public static int daysDifference(String fromDate,String toDate)
	{
		int year1 = Integer.parseInt(fromDate.substring( 0, 4));
		int month1 = Integer.parseInt(fromDate.substring( 4, 6));
		int day1 = Integer.parseInt(fromDate.substring( 6));
		int year2 = Integer.parseInt(toDate.substring( 0, 4));
		int month2 = Integer.parseInt(toDate.substring( 4, 6));
		int day2 = Integer.parseInt(toDate.substring( 6));
		Calendar first = new GregorianCalendar( year1, month1 - 1, day1);
		Calendar second = new GregorianCalendar( year2, month2 - 1, day2);
		long diffMillis = second.getTimeInMillis() - first.getTimeInMillis();
		long diffDays = diffMillis / (24 * 60 * 60 * 1000);
		return (int) diffDays;
	}
	
	public static double getDifferenceInTime( Calendar startTime, Calendar endTime) {
		double diffMillis = endTime.getTimeInMillis() - startTime.getTimeInMillis();
		double diffInTime = diffMillis / (1000);
		diffInTime = roundDouble( diffInTime, 4);
		return diffInTime;
	}
	public static final double roundDouble( double d, int places) {
		return Math.round( d * Math.pow( 10, (double) places)) / Math.pow( 10, (double) places);
	}
	
	@SuppressWarnings("deprecation")
	public static String computeEndDate(String p_BillDateStr) throws Exception
	{
		Date d1 = null;
		SimpleDateFormat p_simpleDate = new SimpleDateFormat("yyyyMMdd");
		Date d = p_simpleDate.parse(p_BillDateStr);
		d1 = new Date();
		d1.setYear(d.getYear());
		d1.setMonth(d.getMonth() + 1);
		d1.setDate(d.getDate());
		d1.setDate(d1.getDate() - 1);
		return p_simpleDate.format(d1);
	}
	public static String computeMonthStartDate(String currentDate)
	{
		Pattern datecharPatter=Pattern.compile("[0-9]{8}");
		Matcher unformattedDateMatcher=datecharPatter.matcher(currentDate);
		if(currentDate!=null && currentDate.trim().length()>0 && currentDate.trim().length()==8 && unformattedDateMatcher.find())
		{
			currentDate=currentDate.substring(0,6)+"01";
		}
		return currentDate;
	}
	@SuppressWarnings("deprecation")
	public static String computestartDate(String p_BillDateStr) throws Exception
	{
		Date d1=null;
		SimpleDateFormat p_simpleDate=new SimpleDateFormat("yyyyMMdd");
		Date d=p_simpleDate.parse(p_BillDateStr);
		d1=new Date();
		d1.setYear(d.getYear());
		d1.setMonth(d.getMonth()-1);
		d1.setDate(d.getDate());
		d1.setDate(d1.getDate());
		return  p_simpleDate.format(d1);
	}
	public static String computePeriodStartDate(String startDate) throws Exception
	{
		
		if (startDate.contains("-"))
		{
			Pattern p_p_date = Pattern.compile("[0-9]{4}-[\\d]{2}");
			  Matcher p_m_date = p_p_date.matcher(startDate);
			  if (p_m_date.find())
			  {
				  startDate = startDate.substring(startDate.indexOf("-")+1)+"-01-"+startDate.substring(0,startDate.indexOf("-")); 
			  }
		}
		else if(startDate.trim().length()!=8)
		{
			startDate = "01 "+startDate;
		}
		

		startDate=getDateString(startDate);
		return startDate;
	}
	public static String convertDateToString(Date date,String dateFormat)
	{
		String formatedDate="";
		try
		{
			SimpleDateFormat finalDateFormat=new SimpleDateFormat(dateFormat);
			formatedDate=finalDateFormat.format(date);
		}
		catch(Exception exception)
		{
			logger.warn(exception.getMessage());	
		}
		return formatedDate;
	}
	public static String convertDateFormat(String inputDate,String inputDateFormat,String outputDateFormat)
	{
		String formatedDate="";
		try
		{
			SimpleDateFormat inputFormat=new SimpleDateFormat(inputDateFormat);
			SimpleDateFormat outputFormat=new SimpleDateFormat(outputDateFormat);
			
			Date convertedDate=inputFormat.parse(inputDate);
			formatedDate=outputFormat.format(convertedDate);
		}
		catch(Exception exception)
		{
			logger.warn(exception.getMessage());
		}
		return formatedDate;
	}
	public static Date convertToDate(String dateString,String dateFormat)
	{
		Date outputDate=null;
		try
		{
			SimpleDateFormat outputFormat=new SimpleDateFormat(dateFormat);
			outputDate=outputFormat.parse(dateString);
		}
		catch(Exception exception)
		{
			logger.warn(exception.getMessage());
		}
		return outputDate;
	}
	@SuppressWarnings("deprecation")
	public static String getDateString(String dateStr)
	{
		int year = 0;
		Pattern p_pattern =Pattern.compile("[A-Z]+");
		String dateString = dateStr.trim();
		SimpleDateFormat simp_date=new SimpleDateFormat("yyyyMMdd");
		Matcher p_mathces=p_pattern.matcher(dateString.toUpperCase());
		if(!p_mathces.find())
		{
			while(dateString.indexOf(".")!=-1)
				dateString= dateString.replace("."," ").trim();
			dateString=dateString.replaceAll("-","/");
			dateString=dateString.replaceAll(" ","/");
		}
		try
		{
			dateString=simp_date.format(new Date(dateString));
			dateStr=dateString;
		}
		catch(Exception e)
		{
			try
			{
				dateString+=" "+year;
				dateString=simp_date.format(new Date(dateString));
				dateStr=dateString;
			}
			catch (Exception ex)
			{
				logger.warn( "WARNING: Error in formatting DATE "+dateStr);
			}
		}
		return  dateStr;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isEvent(Date date){
		if(date!=null){
			try {
				int eventRangeInMonths = 1;
				Date todayDate = new Date();
				String currentMonthStartdate = computeMonthStartDate(convertDateToString(todayDate, "yyyyMMdd"));
				Date currentMonth = convertToDate(currentMonthStartdate, "yyyyMMdd");
				Date eventRangeMinDate = rangeDate(currentMonth, -eventRangeInMonths);
				Date eventRangeMaxDate = rangeDate(currentMonth, eventRangeInMonths);
				String eventMaxEndDate = computeEndDate(convertDateToString(eventRangeMaxDate, "yyyyMMdd"));
				eventRangeMaxDate = convertToDate(eventMaxEndDate, "yyyyMMdd");
				date.setYear(currentMonth.getYear());
				return eventRangeMinDate.compareTo(date) * date.compareTo(eventRangeMaxDate) >=0;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static String getEvent(Date eventDate){
		eventDate.setYear(new Date().getYear());
		return convertDateToString(eventDate, "yyyy-MM-dd");
	}
	@SuppressWarnings("deprecation")
	public static Date rangeDate(Date date, int range){
		Date eventRangeDate = new Date();
		eventRangeDate.setYear(date.getYear());
		eventRangeDate.setMonth(date.getMonth() + range);
		eventRangeDate.setDate(date.getDate());
		return eventRangeDate;
	}
	public static Date rangeDate(String dateString,String dateFormat, int range){
		Date date = convertToDate(dateString, dateFormat);
		return rangeDate(date , range);
	}
}
