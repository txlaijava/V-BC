package com.shopping.base.foundation.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证号码校验工具
 * 此方法仅校对身份证号是否符合规则，不校验姓名等有效信息，如果需匹配号码与姓名，请使用其它工具处理
 * @author 陈道兴
 *
 *
 */

public class IDCardUtils {

	private static final Log logger = LogFactory.getLog(IDCardUtils.class);

	private static final String[] VAL_CODE_ARR = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
	private static final String[] WI = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8",
			"4", "2" };

	@SuppressWarnings("rawtypes")
	public static boolean idCardValidate(String paramIdCard) {

		String ai = "";

		if (StringUtils.isEmpty(paramIdCard)) {
			logger.error("身份证号码为空");
			return false;
		}
		String idCard = paramIdCard.toLowerCase();

		// 号码的长度 15位或18位
		if (idCard.length() != 15 && idCard.length() != 18) {
			logger.error("身份证号码长度应该为15位或18位");
			return false;
		}

		// 数字 除最后以为都为数字
		if (idCard.length() == 18) {
			ai = idCard.substring(0, 17);
		}

		if (idCard.length() == 15) {
			ai = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
		}

		if (IDCardUtils.isNumeric(ai) == false) {
			logger.error("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字");
			return false;
		}

		// 出生年月是否有效
		String strYear = ai.substring(6, 10);// 年份
		String strMonth = ai.substring(10, 12);// 月份
		String strDay = ai.substring(12, 14);// 月份
		if (IDCardUtils.isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			logger.error("身份证生日无效");
			return false;
		}

		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				logger.error("身份证生日不在有效范围");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			logger.error("身份证月份无效");
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			logger.error("身份证日期无效");
			return false;
		}

		// 地区码时候有效
		Hashtable h = IDCardUtils.getAreaCode();
		if (h.get(ai.substring(0, 2)) == null) {
			logger.error("身份证地区编码错误");
		}

		// 判断最后一位的值
		int totalMulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalMulAiWi = totalMulAiWi + +Integer.parseInt(String.valueOf(ai.charAt(i))) * Integer.parseInt(WI[i]);
		}
		int modValue = totalMulAiWi % 11;
		String strVerifyCode = VAL_CODE_ARR[modValue];
		ai = ai + strVerifyCode;
		if (idCard.length() == 18) {
			if (ai.equals(idCard) == false) {
				logger.info("身份证无效，不是合法的身份证号码");
				return false;
			}
		}

		logger.info("身份证验证通过!");
		return true;
	}

	/**
	 * 功能：设置地区编码
	 *
	 * @return Hashtable 对象
	 */

	public static Hashtable<String, String> getAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param strDate
	 * @return
	 */
	public static final Pattern ID_PATTERN = Pattern.compile(
			"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

	public static boolean isDate(String strDate) {
		Matcher m = ID_PATTERN.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static final Pattern NUM_PATTERN = Pattern.compile("[0-9]*");

	public static boolean isNumeric(String str) {

		Matcher isNum = NUM_PATTERN.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 对身份证 加* 处理
	 * 
	 * @param idnumber
	 * @return
	 * @author tonv
	 */
	public static String calcIdNumber(String idnumber) {
		if (StringUtils.isEmpty(idnumber) || idnumber.length() < 15) {
			return "******************";
		}
		if (idnumber.length() == 15) {
			idnumber = idnumber.substring(0, 4) + "*******"
					+ idnumber.substring(idnumber.length() - 4, idnumber.length());
		} else if (idnumber.length() == 18) {
			idnumber = idnumber.substring(0, 4) + "**********"
					+ idnumber.substring(idnumber.length() - 4, idnumber.length());
		}
		return idnumber;
	}


}
