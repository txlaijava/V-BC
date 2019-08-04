/**
 * 
 */
package com.shopping.base.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author HyNo
 * 
 */
public class DateUtils {

	public static Logger log = Logger.getLogger(DateUtils.class);

	public static final String DATE_FORMAT_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_MM = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_HH = "yyyy-MM-dd HH";
	public static final String DATE_FORMAT_DD = "yyyy-MM-dd";
	public static final String DATE_FORMAT_MD = "yyyy-M-d";
	
	public static final String DATE_FORMAT_NO_DD = "yyyy-MM";
	
	public static final String DATE_FORMAT_SPLITE_DD = "yyyy.MM.dd";
	public static final String DATE_FORMAT_NO_SPLITE_DD = "yyyyMMdd";
	
	public static final String DATE_FORMAT_HH_MM_SS="HH:mm:ss";
	
	public static final String DATE_FORMAT_MM_NO_DD = "yyyyMM";

	public static final String DATE_FORMAT_NO_SPLITE_MM = "yyyyMMddHHmm";
	public static final String DATE_FORMAT_NO_SPLITE_MM_HH = "yyyyMMddHH";
	public static final String YEAR = "yyyy";

	public static final String DATE_FORMAT_MMDD = "M月d日";
	public static final String DATE_FORMAT_WEEK = "星期";

	public static final String DATE_TIME_MORNING = "早上";
	public static final String DATE_TIME_AFTERNOON = "下午";
	public static final String DATE_TIME_NIGHT = "晚上";

	public static final String CENTRE_SCRIBING = "-";

	protected static final String EMPTY = "";
	protected static final String ZERO = "0";
	protected static final String SPLITE_CHAR = ":";

	protected static final String START_TIME = " 00:00:00";// 空格不能删除
	protected static final String END_TIME = " 23:59:59";// 空格不能删除

	protected static final int WEEK_DAYS = 7;

	protected static final String[] weeks = { "一", "二", "三", "四", "五", "六", "日" };
	
	/**年*/
	public static final int YEAR_RETURN = 0;
	/**月*/
	public static final int MONTH_RETURN = 1;
	/**日*/
	public static final int DAY_RETURN = 2;
	/**时*/
	public static final int HOUR_RETURN= 3;
	/**分*/
	public static final int MINUTE_RETURN = 4;
	/**秒*/
	public static final int SECOND_RETURN = 5;
	
	/**年*/
	public static final String YYYY = "yyyy";
	/**年-月*/
	public static final String YYYYMM = "yyyy-MM";
	/**年-月-日*/
	public static final String YYYYMMDD = "yyyy-MM-dd";
	/**年-月-日-时*/
	public static final String YYYYMMDDHH= "yyyy-MM-dd HH";
	/**年-月-日-时-分*/
	public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	/**年-月-日-时-分-秒*/
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.YEAR);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return 0;
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return 0;
	}
	
	/**
	 * 返回日
	 * 
	 * @param date
	 *            日期
	 * @return 返回日
	 */
	public static int getDay(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c.get(Calendar.DATE);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return 0;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		if (date == null) {
			return EMPTY;
		}
		DateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}

	/**
	 * 判断给定的日期是一周中的第几天，注意：按照中国的习惯，周日是第七天
	 * 
	 * @param date
	 * @return
	 */
	public static int dateToWeek(Date date) {
		if (date == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
			return 7;
		} else {
			return cal.get(Calendar.DAY_OF_WEEK) - 1;
		}
	}

	public static String dateOfWeek(Date date) {
		return DATE_FORMAT_WEEK + weeks[dateToWeek(date) - 1];
	}

	/**
	 * 指定时间的下一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDate(Date date) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return null;
	}

	/**
	 * 指定时间的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date previousDate(Date date) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return null;
	}

	/**
	 * 指定时间的下N天
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextNDate(Date date, int nDay) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.add(Calendar.DATE, nDay);
			return cal.getTime();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return null;
	}

	/**
	 * 指定时间的前N天
	 * 
	 * @param date
	 * @return
	 */
	public static Date previousNDate(Date date, int nDay) {
		if (date == null) {
			return date;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(date);
			cal.add(Calendar.DATE, -nDay);
			return cal.getTime();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}

		return null;
	}

	/**
	 * currentDat是否在referenceDate日期之前
	 * @param referenceDate
	 * @param currentDate
	 * @return
	 */
	public static boolean isBeforeDate(Date referenceDate, Date currentDate) {
		if (currentDate == null) {
			return false;
		}
		if (referenceDate == null) {
			return true;
		}
		return currentDate.before(referenceDate);
	}

	/**
	 * currentDat是否在referenceDate日期之后
	 * 
	 * @param referenceDate
	 * @param currentDate
	 * @return
	 */
	public static boolean isAffterDate(Date referenceDate, Date currentDate) {
		if (currentDate == null) {
			return false;
		}
		if (referenceDate == null) {
			return true;
		}
		return currentDate.after(referenceDate);
	}

	/**
	 * 判断currentDate是否在startDate和endDate之间，不包括startDate和endDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @param currentDate
	 * @return
	 */
	public static boolean isDuringDate(Date startDate, Date endDate,
									   Date currentDate) {
		if (currentDate == null) {
			return false;
		}

		if (isAffterDate(startDate, currentDate)
				&& isBeforeDate(endDate, currentDate)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取startDate到endDate之间的星期day（中文星期）不包括startDate和endDate
	 * 
	 * @param startDate
	 * @param endDate
	 * @param day
	 * @return
	 */
	public static List<Date> findDayDuringDate(Date startDate, Date endDate,
											   int day) {
		List<Date> listDate = new ArrayList<Date>();
		int startDay = dateToWeek(startDate);

		Date date = null;
		if (startDay == day) {
			date = nextNDate(startDate, WEEK_DAYS);
		} else {
			date = nextNDate(startDate, day - startDay);
		}
		while (isDuringDate(startDate, endDate, date)) {
			listDate.add(date);
			date = nextNDate(date, WEEK_DAYS);
		}

		return listDate;
	}
	/**
	 * 获取早中晚
	 * 
	 * @param time
	 * @return
	 */
	public static String getDateTime(int time) {
		// 早上
		if (time == 1) {
			return DateUtils.DATE_TIME_MORNING;
		}
		// 下午
		else if (time == 2) {
			return DateUtils.DATE_TIME_AFTERNOON;
		}
		// 晚上
		else if (time == 3) {
			return DateUtils.DATE_TIME_NIGHT;
		}
		return null;
	}

	/**
	 * 计算2个时间差，精确到 年、月、日、时、分、秒、毫秒
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param formatPattern 时间格式
	 * @param returnPattern 计算单位（年、月、日、时、分、秒、毫秒）
	 * @return
	 * @throws ParseException
	 */
	public static long getBetween(String beginTime, String endTime, String formatPattern, int returnPattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);
		
		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		switch (returnPattern) {
		case YEAR_RETURN:
			return DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR);
		case MONTH_RETURN:
			return DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR)*12 + DateUtils.getByField(beginCalendar, endCalendar, Calendar.MONTH);
		case DAY_RETURN:
			return DateUtils.getTime(beginDate, endDate)/(24*60*60*1000);
		case HOUR_RETURN:
			return DateUtils.getTime(beginDate, endDate)/(60*60*1000);
		case MINUTE_RETURN:
			return DateUtils.getTime(beginDate, endDate)/(60*1000);
		case SECOND_RETURN:
			return DateUtils.getTime(beginDate, endDate)/1000;
		default:
			return 0;
		}
	}

	/**
	 * 计算2个时间差，精确到 年、月、日、时、分、秒、毫秒
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param formatPattern 时间格式
	 * @return
	 * @throws ParseException
	 */
	public static String getBetween(String beginTime, String endTime, String formatPattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
		Date beginDate = simpleDateFormat.parse(beginTime);
		Date endDate = simpleDateFormat.parse(endTime);

		Calendar beginCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		endCalendar.setTime(endDate);
		Long YEAR = DateUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR);
		Long MONTH_RETURN = DateUtils.getByField(beginCalendar, endCalendar, Calendar.MONTH);
		Long DAY_RETURN = DateUtils.getTime(beginDate, endDate) / (24*60*60*1000);
		Long HOUR_RETURN = DateUtils.getTime(beginDate, endDate)/(60*60*1000);
		Long MINUTE_RETURN = DateUtils.getTime(beginDate, endDate)/(60*1000);
		Long SECOND_RETURN = DateUtils.getTime(beginDate, endDate)/1000;


		if(YEAR > 0){
			return YEAR  + "年前";
		}else if(HOUR_RETURN >= 720){
			return MONTH_RETURN  + "个月前";
		}else if(DAY_RETURN > 0 ){
			return DAY_RETURN  + "天前";
		}else if(HOUR_RETURN > 0 ){
			return HOUR_RETURN  + "小时前";
		}else if(MINUTE_RETURN > 0 ){
			return MINUTE_RETURN  + "分钟前";
		}else if(SECOND_RETURN > 0 ){
			return 1  + "分钟前";
		}

		return YEAR  + "年" + MONTH_RETURN + "月" + DAY_RETURN + "天" + HOUR_RETURN + "小时" + MINUTE_RETURN + "分" + SECOND_RETURN + "秒";
	}
	
	private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField){
		return endCalendar.get(calendarField) - beginCalendar.get(calendarField);
	}

	private static long getTime(Date beginDate, Date endDate){
		return endDate.getTime() - beginDate.getTime();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(DateUtils.getBetween("2013-05-02", "2013-05-05", DateUtils.YYYY, DateUtils.YEAR_RETURN));
			System.out.println(getBetween("2017-10-31 12:12:12",CommUtils.formatLongDate(new Date()),"yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
