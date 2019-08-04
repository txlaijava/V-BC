/*
 * Copyright 2016 - 2017 suoke & Co., Ltd.
 */
package com.shopping.base.foundation.util;

import java.text.DecimalFormat;

/**
 *
 * @version 1.0 created at 2017年6月8日 下午2:21:42
 *
 */
public class NumberUtils {

	public static String format(double number, String pattern) {
		DecimalFormat fmt = new DecimalFormat(pattern);
		return fmt.format(number);
	}
}
