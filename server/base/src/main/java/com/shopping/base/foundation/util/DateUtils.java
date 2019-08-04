/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import com.shopping.base.foundation.result.ActionResult;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @version 1.0 created at 2017年6月1日 下午5:01:44
 *
 */
public class DateUtils {

	public static String format(Date date, String fmt) {
		return new SimpleDateFormat(fmt).format(date);
	}

	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseDate(String date, String pattern) {
		try {
			return StringUtils.isBlank(date) ? null : new SimpleDateFormat(pattern).parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date newDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		setBeginOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		setEndOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getBeginTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getEndTimeOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setEndOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getLastMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 上个月最后一天
	 */
	public static Date getLastMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 昨天
	 */
	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	public static Date getNextMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static Date getNextMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setBeginOfDay(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 2);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	public static Date addMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date addSeconds(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	public static Date addMinutes(int minutes) {
		return addMinutes(new Date(), minutes);
	}

	public static Date addSeconds(int seconds) {
		return addSeconds(new Date(), seconds);
	}

	private static void setBeginOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setEndOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}


	public static boolean isBeforeNow(Date time) {
		return time.getTime() < System.currentTimeMillis();
	}

	public static boolean isAfterNow(Date time) {
		return time.getTime() > System.currentTimeMillis();
	}

	public static ActionResult<?> validateDateRange(Date beginTime, Date endTime) {
		if (DateUtils.isBeforeNow(beginTime)) {
			return ActionResult.error("开始时间不能小于当前时间");
		}
		if (endTime.compareTo(beginTime) < 0) {
			return ActionResult.error("结束时间不能小于开始时间");
		}
		return ActionResult.ok();
	}

	public static String getTimeMillis(int length) {
		String millis = String.valueOf(System.currentTimeMillis());
		if (millis.length() > length) {
			return millis.substring(millis.length() - length, millis.length());
		}
		return millis;
	}

}
