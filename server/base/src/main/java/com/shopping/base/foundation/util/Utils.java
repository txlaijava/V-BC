/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @version 1.0 created at 2017年5月9日 下午2:53:27
 *
 */
public class Utils {

	public static String shortUUID() {
		return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes()).substring(8, 23);
	}

	public static String uuid() {
		return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
	}

	public static long unixTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	public static long todayUnixTimestamp() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() / 1000;
	}

	public static String randomNum(int len) {

		Random random = new Random();
		int i = random.nextInt((int) Math.pow(10, len) - 1);// random.nextInt(10^len-1)+"";
		String str = String.format("%0" + len + "d", i);
		return str;

	}

	public static String nonceStr(int len) {
		String str = "";
		String strPol = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		int max = strPol.length();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			str += strPol.charAt(random.nextInt(max));
		}
		return str;
	}

	public static <T> T ifNull(T value, T defValue) {
		if (value == null) {
			return defValue;
		} else {
			return value;
		}
	}

}
