package com.shopping.base.utils.date;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 时间操作工具类
 *
 * @author TanXiaolong
 */
public class DateUtil {

    /**
     * <p>
     * 创建时间
     * </p>
     *
     * @param year  "2007" 形式的字符串
     * @param month "09" 形式的字符串
     * @param date  "05" 形式的字符串
     * @return 返回"2007-09-05"格式化的时间(java.util.Date)
     */
    public static final Date getDate(String year, String month,
                                     String date) {

        int day = Integer.parseInt(date);
        int month_t = Integer.parseInt(month);
        int year_t = Integer.parseInt(year);
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(year_t, month_t - 1, day);
        Date date_t = new Date(rightNow.getTime().getTime());
        return date_t;
    }

    static public Date strToDate(String dateStr, String format) throws Exception {
        if (dateStr == null) return null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
        Date rt = null;
        rt = dateFormatter.parse(dateStr);
        return rt;
    }

    static public String dateToStr(String format, Date dateToFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateToFormat);
        return dateString;
    }

    /**
     * <p>
     * 创建时间
     * </p>
     *
     * @param year  2007 形式的数字
     * @param month 7 形式的数字
     * @param date  7 形式的数字
     * @return 返回"2007-07-07"格式化的时间(java.util.Date)
     */
    public static final Date getDate(int year, int month, int date) {

        Calendar rightNow = Calendar.getInstance();
        rightNow.set(year, month - 1, date);
        Date date_t = new Date(rightNow.getTime().getTime());
        return date_t;
    }

    /**
     * <p>
     * 时间类型转换
     * </p>
     *
     * @param date "2007-09-05" 形式的字符串,年必须是四个字符，月是两个字符，日是两个字符
     * @return 返回"2007-09-05"格式化的时间(java.util.Date)
     */
    public static final Date getDate(String date) {
        return getDate(getYear(date), getMonth(date), getDay(date));
    }

    /**
     * <p>
     * 取一个时间的字段:年
     * </p>
     *
     * @param newDate "2007-09-05" 形式的字符串,年必须是四个字符，月是两个字符，日是两个字符
     * @return 返回 int 类型的数字 <b>年</b> 如：2007
     */
    public static int getYear(String newDate) {
        if (newDate == null || newDate.trim().equals("")
                || newDate.trim().length() < 4) {
            return 0;
        } else {
            return Integer.parseInt(newDate.substring(0, 4));
        }
    }

    /**
     * <p>
     * 取一个时间的字段:年
     * </p>
     *
     * @param newDate java.util.Date 类型的时间对象Date
     * @return 返回 int 类型的数字 <b>年</b> 如：2007
     */
    public static int getYear(Date newDate) {

        if (newDate == null || newDate.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(newDate);
            return mycal.get(Calendar.YEAR);
        }

    }

    /**
     * <p>
     * 取一个时间的字段:月
     * </p>
     *
     * @param date java.util.Date 类型的时间对象Date
     * @return 返回 int 类型的数字 <b>月</b> 如：9
     */
    public static int getMonth(Date date) {

        if (date == null || date.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(date);
            return mycal.get(Calendar.MONTH) + 1;
        }

    }

    /**
     * <p>
     * 取一个时间的字段:月
     * </p>
     *
     * @param date "2007-09-05" 形式的字符串,年必须是四个字符，月是两个字符，日是两个字符
     * @return 返回 int 类型的数字 <b>月</b> 如：9
     */
    public static int getMonth(String date) {
        if (date == null || date.trim().equals("") || date.trim().length() < 7) {
            return 0;
        } else {
            return Integer.parseInt(date.substring(5, 7));
        }
    }

    /**
     * <p>
     * 取一个时间的字段:日
     * </p>
     *
     * @param date java.util.Date 类型的时间对象Date
     * @return 返回 int 类型的数字 <b>日</b> 如：4
     */
    public static int getDay(Date date) {
        if (date == null || date.equals("")) {
            return 0;
        } else {
            Calendar mycal = Calendar.getInstance();
            mycal.setTime(date);
            return mycal.get(Calendar.DATE);
        }
    }

    /**
     * @param date   java.util.Date 类型的时间对象Date
     * @param length String 长度是7或者是10
     * @return 返回 String 类型的时间，如果length的值是7,返回结果的时间格式为<b>"2007-09"</b>,如果length的值是10,返回结果的时间格式为<b>"2007-09-14"</b>,否则返回字符<b>"0000-00-00"</b>；
     */
    public static final String getDate(Date date, int length) {

        if (date == null || date.equals(""))
            return "0000-00-00";
        Calendar mycal = Calendar.getInstance();
        mycal.setTime(date);
        String month = null;
        String date_t = null;
        if (getMonth(date) < 10)
            month = "-0" + getMonth(date);
        else
            month = "-" + getMonth(date);

        if (getDay(date) < 10)
            date_t = "-0" + getDay(date);
        else
            date_t = "-" + getDay(date);

        if (length == 7) {
            return getYear(date) + month;
        } else if (length == 10) {
            return getYear(date) + month + date_t;
        } else
            return "0000-00-00";
    }

    /**
     * <p>
     * 取一个时间的字段:日
     * </p>
     *
     * @param date "2007-09-05" 形式的字符串,年必须是四个字符，月是两个字符，日是两个字符
     * @return 返回 int 类型的数字 <b>日</b> 如：5
     */
    public static int getDay(String date) {
        if (date == null || date.trim().equals("") || date.trim().length() < 10) {
            return 0;
        } else {
            return Integer.parseInt(date.substring(8));
        }
    }

    /**
     * <p>
     * 返回时间栈的时间
     * </p>
     *
     * @param date "2007-09-05" 形式的时间字符串,年必须是四个字符，月是两个字符，日是两个字符
     * @param time "15:45" 形式的时间字符串,HOUR 必须是两个字符，MINUTE 是两个字符
     * @return 返回 java.sql.Timestamp 类型的时间 如：<b>2007-09-05 03:45:04.203</b>
     */
    public static final java.sql.Timestamp getTimestamp(String date, String time) {

        Calendar _calendar = Calendar.getInstance();
        _calendar.setTime(getDate(getYear(date), getMonth(date), getDay(date)));
        _calendar.set(Calendar.HOUR, Integer.parseInt(time.substring(0, time
                .indexOf(":"))));
        _calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(time
                .indexOf(":") + 1)));
        // _calendar.set(_calendar.SECOND,
        // Integer.parseInt(timeString.substring(timeString.lastIndexOf(":")+1,
        // 2)));
        return new java.sql.Timestamp(_calendar.getTime().getTime());

    }

    /**
     * <p>
     * 取系统当前时间
     * </p>
     *
     * @return 返回系统当前时间(java.util.Date类型 0000-00-00)
     */
    public static final Date getTodayDate() {

        long date = System.currentTimeMillis();

        Date result = new Date(date);

        return result;

    }

    /**
     * <p>
     * 取系统当前时间
     * </p>
     *
     * @param style DateStyleEnum 样式
     * @return 返回系统当前时间
     */
    public static final String getFormatDate(String style) {

        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat(style);
        return fmt.format(rightNow.getTime());

    }

    /**
     * <p/>
     * 格式化日期
     * <p/>
     *
     * @param date  java.util.Date类型的时间
     * @param style DateStyleEnum 样式
     * @return 返回时间字符串
     */
    public static final String formatDate(Date date, String style) {
        SimpleDateFormat fmt = new SimpleDateFormat(style);
        return fmt.format(date);
    }


    public static final String formatDate(Date date) {
        String style = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat fmt = new SimpleDateFormat(style);
        return fmt.format(date);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * <p>
     * 日期按天累加
     * </p>
     *
     * @param date java.util.Date类型的时间
     * @param days 增加的天数
     * @return 返回java.util.Date类型的时间(0000-00-00)
     */
    public static final Date getDateAddDay(Date date, int days) {

        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + days);

        return new Date(calendar.getTime().getTime());
    }

    /**
     * <p>
     * 日期按月累加
     * </p>
     *
     * @param date   java.util.Date类型的时间
     * @param months 增加的月数
     * @return 返回java.util.Date类型的时间(0000-00-00)
     */
    public static final Date getDateAddMonth(Date date, int months) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month + months);

        return new Date(calendar.getTime().getTime());
    }

    /**
     * 增加分钟
     * @param date
     * @param minutes
     * @return
     */
    public static final Date getDateAddMinutes(Date date, int minutes) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.add(Calendar.MINUTE, minutes);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 增加秒数
     * @param date
     * @param SECOND
     * @return
     */
    public static final Date getDateAddSecond(Date date,int SECOND) {
        if (date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.add(Calendar.SECOND,SECOND);
        return new Date(calendar.getTime().getTime());
    }

    /**
     * <p>
     * 设置日期中的参数，可以对年、月、日、时、分、秒等进行设置
     * </p>
     *
     * @param date  java.util.Date类型的时间
     * @param param java.util.Calendar的常量值,设置日期中的参数，可以对年、月、日、时、分、秒等进行修改设置
     * @param value void
     */
    public static void setDateParameter(Date date, int param, int value) {

        if (date == null)
            return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        calendar.set(param, value);
        date.setTime(calendar.getTime().getTime());

    }

    /**
     * <p>
     * 取日期中的参数，可以对年、月、日、时、分、秒等进行读取
     * </p>
     *
     * @param date  java.util.Date类型的时间
     * @param param java.util.Calendar的常量值,取日期中的参数，可以对年、月、日、时、分、秒等进行读取
     * @return 返回int类型的值(年、月、日、时、分、秒等)
     */
    public static final int getDateParameter(Date date, int param) {
        if (date == null)
            return -1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(date.getTime()));
        return calendar.get(param);

    }

    // 计算日期间隔

    /**
     * <p>
     * 计算日期间隔的天数
     * </p>
     *
     * @param startdate 开始时间java.util.Date类型
     * @param enddate   结束时间java.util.Date类型
     * @return 返回long类型的值(表示相差的天数)
     */
    public static final long diffDate(Date startdate,
                                      Date enddate) {

        long diff = (startdate.getTime() - enddate.getTime()) / 86400000;
        return diff;

    }

	/*
     * 下面的程序片断用简单的整数除法转换毫秒到秒：
	 *
	 * long milliseconds = 1999; long seconds = 1999 / 1000;
	 *
	 * 这种方法舍去小数部分转换毫秒到秒，所以1,999毫秒等于1秒，2,000毫秒等于2秒。
	 * 计算更大的单位-例如天数，小时和分钟-给定一个时间数值，可以使用下面的过程： 1. 计算最大的单位，减去这个数值的秒数 2.
	 * 计算第二大单位，减去这个数值的秒数 3. 重复操作直到只剩下秒 例
	 * 如,如果你的时间的10,000秒，你想知道这个数值相应的是多少小时，多少分钟，多少秒，你从最大的单位开始：小时。10,000除以3600（一个小
	 * 时的秒数）得到小时数。使用整数除法，答案是2小时（整数除法中小数舍去）计算剩下的秒数，10,000-(3,600 x 2) =
	 * 2,800秒。所以你有2小时和2,800秒。 将2,800秒转换成分钟，2,800除以60。使用整数除法，答案是46。2,800 - (60 x
	 * 46) = 40秒。最后答案是2小时，46分，40秒。
	 */

    /**
     * <p>
     * 将毫秒转换成小时，分，秒
     * </p>
     *
     * @param timeInSeconds 毫秒值
     * @return 返回时分秒的时间字符串
     */
    public static final String calcHM(long timeInSeconds) {

        String shortdate = "";
        long hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;
        shortdate = hours + " hors " + minutes + " minute " + seconds
                + " second ";
        return shortdate;
    }


    /**
     * 接受YYYY-MM-DD的日期字符串参数,返回两个日期相差的天数
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static long getDistDates(String start, String end) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        return getDistDates(startDate, endDate);
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }


    /**
     * <p>
     * 计算两个时间间隔的天数
     * </p>
     *
     * @param g1 java.util.GregorianCalendar 类型的时间
     * @param g2 java.util.GregorianCalendar 类型的时间
     * @return 返回int类型的天数
     */
    public static final int getDays(GregorianCalendar g1, GregorianCalendar g2) {

        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }

        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);

        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);

        while (gc1.before(gc2)) {
            gc1.add(Calendar.DATE, 1);
            elapsed++;
        }
        return elapsed;
    }

    /**
     * <p>
     * 计算两个时间间隔的月数
     * </p>
     *
     * @param g1 java.util.GregorianCalendar 类型的时间
     * @param g2 java.util.GregorianCalendar 类型的时间
     * @return 返回int类型的月数
     */
    public static final int getMonths(GregorianCalendar g1, GregorianCalendar g2) {
        int elapsed = 0;
        GregorianCalendar gc1, gc2;

        if (g2.after(g1)) {
            gc2 = (GregorianCalendar) g2.clone();
            gc1 = (GregorianCalendar) g1.clone();
        } else {
            gc2 = (GregorianCalendar) g1.clone();
            gc1 = (GregorianCalendar) g2.clone();
        }

        gc1.clear(Calendar.MILLISECOND);
        gc1.clear(Calendar.SECOND);
        gc1.clear(Calendar.MINUTE);
        gc1.clear(Calendar.HOUR_OF_DAY);
        gc1.clear(Calendar.DATE);

        gc2.clear(Calendar.MILLISECOND);
        gc2.clear(Calendar.SECOND);
        gc2.clear(Calendar.MINUTE);
        gc2.clear(Calendar.HOUR_OF_DAY);
        gc2.clear(Calendar.DATE);

        while (gc1.before(gc2)) {
            gc1.add(Calendar.MONTH, 1);
            elapsed++;
        }
        return elapsed;
    }

    /**
     * @param date
     * @return java.util.Date 有分析跟踪功能的日期
     */
    public static final Date transferDate(String date, String style) {

        SimpleDateFormat fmt = new SimpleDateFormat(style);
        return fmt.parse(date, new ParsePosition(0));

    }

    /**
     * <p>
     * 比较两个时间的大小<br>
     * 如果dateOne>dateTwo返回1 ,dateOne<dateTwo返回-1, dateOne>dateTwo返回0 ;
     * </p>
     *
     * @param dateOne
     * @param dateTwo
     * @param style   DateStyleEnum 样式
     * @return dateOne 和 dateTwo 的比较值
     */
    public static final int compareDate(String dateOne, String dateTwo,
                                        String style) {

        Date dateTempOne = transferDate(dateOne, style);
        Date dateTempTwo = transferDate(dateTwo, style);
        return dateTempOne.compareTo(dateTempTwo);
    }

    /**
     * 取时间字段月的最大天数
     *
     * @param date
     * @return int 最大天数
     */
    public static final int maxDay(Date date) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        ca.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return ca.get(Calendar.DATE);

    }

    /**
     * 设置上个月的第一天时间
     *
     * @param date
     * @return java.util.Date 上个月的第一天时间
     */
    public static final Date monthFirstDay(Date date) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1); // 把日期设置为上一个月
        ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        return ca.getTime();

    }

    /**
     * 设置当前时间月的第一天
     *
     * @param date
     * @return java.util.Date 当前时间月的第一天
     */
    public static final Date currentMonthFirstDay() {

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        return ca.getTime();

    }

    /**
     * 设置上个月的最后一天时间
     *
     * @param date
     * @return java.util.Date 上个月的最后一天时间
     */
    public static final Date monthLastDay(Date date) {

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1); // 把日期设置为上一个月
        ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        ca.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return ca.getTime();

    }

    /**
     * 设置当前时间月的最后一天时间
     *
     * @param date
     * @return java.util.Date 当前时间月的最后一天时间
     */
    public static final Date currentMonthLastDay() {

        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        ca.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return ca.getTime();

    }

    /**
     * 取得当前日期所在周的第一天 注意：周的第一天是从星期天到星期六
     *
     * @return java.util.Date
     */
    public static Date getFirstDayOfWeek() {

        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();

    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @return java.util.Date
     */
    public static Date getLastDayOfWeek() {

        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();

    }
    /**
     * 取得当前日期最早时间
     *
     * @return java.util.Date
     */
    public static Date getFirstTime() {

        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY,0); // Sunday
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();

    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    public static Date getLasttTime() {

    	   Calendar c = new GregorianCalendar();
           c.set(Calendar.HOUR_OF_DAY,23); // Sunday
           c.set(Calendar.MINUTE,59);
           c.set(Calendar.SECOND,59);
           c.set(Calendar.MILLISECOND,999);
           return c.getTime();

    }
    public static final Date test1() {

        Calendar ca = Calendar.getInstance();
        ca.roll(Calendar.DATE, -1);// 日期回滚一天
        ca.set(Calendar.HOUR, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();

    }

    public static final String genTimeTraceId() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//		sdf.format(test1());
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间
     * @param format
     * 			时间格式
     * @return
     * 			当前时间
     */
    public static final String genTimeTraceId(String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // System.out.println( DateUtil.getDate(2007, 1, 24));

        // System.out.println( DateUtil.getDate("2007", "09", "4"));

        // System.out.println( DateUtil.getDate("2007-09-04"));

        // System.out.println( DateUtil.getYear("2007-09-04"));

        // System.out.println(
        // DateUtil.getYear(DateUtil.getDate("2007-09-04")));

        // System.out.println( DateUtil.getMonth("2007-09-04"));

        // System.out.println(
        // DateUtil.getMonth(DateUtil.getDate("2007-09-04")));

        // System.out.println( DateUtil.getDay("2007-09-04"));

        // System.out.println( DateUtil.getTimestamp("2007-09-04","15:45"));

        // System.out.println( DateUtil.getDay(DateUtil.getDate("2007-09-04")));

        // System.out.println(
        // DateUtil.getDate(DateUtil.getDate("2007-09-04"),7));

        // System.out.println( DateUtil.getTodayDate());

        // System.out.println( new java.util.Date());

        // System.out.println( DateUtil.toDateString(DateUtil.getTodayDate(),
        // DateUtil.STYLE11));

        // System.out.println( DateUtil.getDateAddDay(DateUtil.getTodayDate(),
        // 5));

        // System.out.println( DateUtil.getDateAddMonth(DateUtil.getTodayDate(),
        // 5));

		/*
		 * Date a = DateUtil.getTodayDate();
		 * DateUtil.setDateParameter(a,java.util.Calendar.DATE, 27);
		 * System.out.println(a);
		 */

        // System.out.println(DateUtil.getDateParameter(DateUtil.getTodayDate(),java.util.Calendar.MINUTE));
        // System.out.println(DateUtil.diffDate(a, DateUtil.getTodayDate()));
        // System.out.println(DateUtil.calcHM(10000));
        // GregorianCalendar gc1 = new GregorianCalendar(2001,
        // Calendar.DECEMBER,
        // 30);
        // GregorianCalendar gc2 = new GregorianCalendar(2002,
        // Calendar.FEBRUARY,
        // 1);
        // int days = DateUtil.getDays(gc1, gc2);
        // int months = DateUtil.getMonths(gc1, gc2);
        // System.out.println("Days = " + days);
        // System.out.println("Months = " + months);
        // System.out.println(DateUtil.compareDate("2007-09-09", "2007-09-09",
        // DateStyleEnum.STYLE10.toString()));
//		System.out.println(DateUtil.getTodayDate());
        // System.out.println(DateUtil.maxDay(DateUtil.getTodayDate()));
        // System.out.println(DateUtil.monthFirstDay(DateUtil.getTodayDate()));
        // System.out.println(DateUtil.monthLastDay(DateUtil.getTodayDate()));
//		 System.out.println(DateUtil.getFirstDayOfWeek());
//		 System.out.println(DateUtil.getLastDayOfWeek());

//		System.out.println(DateUtil.currentMonthFirstDay());
//		System.out.println(DateUtil.currentMonthLastDay());
//		System.out.println(DateUtil.formatDate(DateUtil.currentMonthFirstDay(),
//				DateStyleEnum.STYLE3.toString()));

//		SimpleDateFormat fmt = new SimpleDateFormat("yy/MM/dd");
//		fmt.parse(test1(), new ParsePosition(0));
//		System.out.println( DateUtil.genTimeTraceId() );
//		System.out.println( DateUtil.formatDate(DateUtil.test1(),"yyyyMMddhhmmss") );

        String i = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(i);
        System.out.println(new Date().getTime());
    }

    public static long getDiffSecond(String datetime1, String datetime2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = myFormatter.parse(datetime2);
            Date mydate = myFormatter.parse(datetime1);
            long day = (date.getTime() - mydate.getTime()) / 1000L;
            return day;
        } catch (Exception e) {
        }
        return 1148748325403492352L;
    }

    public static long getDiffSecond(Date datetime1, Date datetime2) {
        try {
            long day = (datetime1.getTime() - datetime2.getTime()) / 1000L;
            return day;
        } catch (Exception e) {
        }
        return 1148748325403492352L;
    }

    /**
     * 增加  默认为时间当天时间
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Map<String, String> DateValue(String startDate, String endDate) {
        Map<String, String> map = new HashMap<String, String>();
        if (startDate != null && !"".equals(startDate)) {
            if (startDate.length() < 15)//比较长度  如果重复查询 字符串会一直加00:00:00
                startDate = startDate + " 00:00:00";
        } else {
            if (endDate == null || "".equals(endDate))
                startDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00";
        }
        if (endDate != null && !"".equals(endDate)) {
            if (endDate.length() < 15)
                endDate = endDate + " 23:59:59";
        } else {
            endDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:59";
        }
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return map;
    }

    /**
     * 前推日期
     *
     * @param date
     * @param datNumber
     * @return
     */
    public static Date decDay(Date date, int datNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -datNumber);
        return calendar.getTime();
    }
    /**
     * 功能描述：返回小
     *
     * @param date
     *            日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date
     *            日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date
     *            Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }




}
