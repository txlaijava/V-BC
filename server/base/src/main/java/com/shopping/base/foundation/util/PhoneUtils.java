/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Tonvchong
 * @version 1.0 created at 2017年6月16日 下午2:35:40
 *
 */
public class PhoneUtils {

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhone(String str) throws PatternSyntaxException {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		return isChinaPhone(str) || isHKPhone(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+任意数 17+任意数 147
	 */
	public static boolean isChinaPhone(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhone(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
