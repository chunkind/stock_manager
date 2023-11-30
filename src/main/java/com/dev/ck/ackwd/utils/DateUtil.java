package com.dev.ck.ackwd.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	public static final String DAFAULT_DATE_FORMAT = "yyyyMMdd";
	public static final String DAFAULT_DATE_FORMAT2 = "yyyy-MM-dd";

	public static void testDate() {
		// Input
		Date date = new Date(System.currentTimeMillis());
		//Date date2 = new Date(System.currentTimeMillis()-(1000*60*60*24*30));

		// Conversion
		SimpleDateFormat sdf;
		
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		System.out.println(sdf.format(date));
		
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DATE  , -7);
		//cal.add(Calendar.MONTH , -1);
		cal.add(Calendar.YEAR , -1);
		String beforeMonth = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(cal.getTime());
		System.out.println(beforeMonth);

	}

	public static String getLastDayOfMonth(){
		String stdToday = DateUtil.toDay(DateUtil.DAFAULT_DATE_FORMAT2);
		String[] splitDate = stdToday.split("-");



		Calendar cal = Calendar.getInstance();
		String result = "";
		Date today = DateUtil.toDayTypeDate();
		cal.setTime(today);

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

//		result = today.getYear()
		if(lastDay < 10){

		}
		return "";
	}

	/**
	 * @auth : K. J. S.
	 * @date : 2018. 12. 2.
	 * 스트링 문자 2개를 받아 비교.
	 */
	public static int compareDate(String date1, String date2, String format) {
		Date target1 = null;
		Date target2 = null;
		int compare = 0;
		SimpleDateFormat mSimpleDateFormat = null;
		try {
			if(date1.length() != date2.length()) {
				throw new RuntimeException("두 날짜간 형식이 맞지 않습니다.");
			}
			mSimpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);

			target1 = mSimpleDateFormat.parse(date1);
			target2 = mSimpleDateFormat.parse(date2);
			compare = target1.compareTo(target2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return compare;
	}
	public static int compareDate(Date date1, Date date2) {
		return date1.compareTo(date2);
	}

	/**
	 * @auth : K. J. S.
	 * @date : 2018. 12. 2.
	 * Date 타입을 String으로 변환.
	 */
	public static String dateToStr(Date date) {
		return DateUtil.dateToStr(date, "");
	}
	public static String dateToStr(Date date, String format) {
		if("".equals(format)) format = DAFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * @Auth K. J. S.
	 * @date 2018. 11. 28.
	 * 오늘 날짜 구하기.
	 */
	public static String toDay(){
		return DateUtil.toDay("");
	}
	public static String toDay(String format){
		if("".equals(format)) format = DAFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		return df.format(DateUtil.toDayTypeDate(format));
	}
	public static Date toDayTypeDate(){
		return DateUtil.toDayTypeDate("");
	}
	public static Date toDayTypeDate(String format){
		if("".equals(format)) format = DAFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		Calendar cal = null;
		try {
			date = df.parse(df.format(new Date()));
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	/**
	 * @Auth K. J. S.
	 * @date 2018. 11. 28.
	 * targetDate 기준일에 날짜 더하기
	 * targetDate가 널이면 오늘 날짜를 기준.
	 * format이 널이면 yyyyMMdd가 기본.
	 */
	public static String addDate(int addYear, int addMonth, int addDate){
		String format = DAFAULT_DATE_FORMAT;
		return DateUtil.addDate(toDay(format), format, addYear, addMonth, addDate);
	}
	public static String addDate(String format, int addYear, int addMonth, int addDate){
		return DateUtil.addDate(toDay(format), format, addYear, addMonth, addDate);
	}
	public static String addDate(String targetDate, String format, int addYear, int addMonth, int addDate){
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		Calendar cal = null;
		try {
			if("".equals(targetDate)){
				date = df.parse(df.format(new Date()));
			}else{
				date = df.parse(targetDate);
			}
			cal = Calendar.getInstance();
			cal.setTime(date);

			if(addDate > 0){
				cal.add(Calendar.DATE, addDate);
			}
			if(addMonth > 0){
				cal.add(Calendar.MONTH, addMonth);
			}
			if(addYear > 0){
				cal.add(Calendar.YEAR, addYear);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return df.format(cal.getTime());
	}

	/**
	 * @auth : K. J. S.
	 * @date : 2018. 12. 2.
	 * Date 객체를 리턴.
	 */
	public static Date date(String date) {
		return DateUtil.date(date, "");
	}
	public static Date date(String date, String format) {
		if("".equals(format)) format = DAFAULT_DATE_FORMAT;
		DateFormat df = new SimpleDateFormat(format);
		Calendar cal = null;
		try {
			cal = Calendar.getInstance();
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

}