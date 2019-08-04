package com.shopping.framework.oss;

import com.shopping.framework.oss.enums.BigDecimalType;

import java.math.BigDecimal;


public class ArithmeticUtil {

	private static final int DEF_DIV_SCALE = 10;

	public ArithmeticUtil() {
	}

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static boolean doubleEq(Double v1, double v2) {
		return Double.doubleToLongBits(v1.doubleValue()) == Double.doubleToLongBits(v2);
	}

	public static BigDecimal add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2);
	}

	public static String strAdd(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.add(b2).setScale(scale, 4).toString();
		}
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	public static BigDecimal sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2);
	}

	public static BigDecimal round(String v, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b = new BigDecimal(v);
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, 4);
		}
	}

	public static BigDecimal round(String v, int scale, BigDecimalType type) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b = new BigDecimal(v);
			BigDecimal one = new BigDecimal("1");
			return type.getValue() == 4 ? b.divide(one, scale, 4) : b.divide(one, scale, 5);
		}
	}

	public static void main(String[] args) {
		System.err.println(round("3", 4, BigDecimalType.ROUND_HALF_DOWN));
	}

	public static String strSub(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.subtract(b2).setScale(scale, 4).toString();
		}
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static BigDecimal mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2);
	}

	public static double mul2(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.multiply(b2).doubleValue(), scale);
	}

	public static String strMul2(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.multiply(b2).setScale(scale, 4).toString();
		}
	}

	public static BigDecimal div(String v1, String v2) {
		return div(v1, v2, 10);
	}

	public static double div(double v1, double v2) {
		return div(v1, v2, 10);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));
			BigDecimal b2 = new BigDecimal(Double.toString(v2));
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	public static BigDecimal div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, 4);
		}
	}

	public static String strDiv(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, 4).toString();
		}
	}

	public static BigDecimal bigDiv(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.divide(b2, scale, 4);
		}
	}

	public static BigDecimal strRemainder(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.remainder(b2).setScale(scale, 4);
		}
	}

	public static String strRemainder2Str(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.remainder(b2).setScale(scale, 4).toString();
		}
	}

	public static boolean strcompareTo(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		int bj = b1.compareTo(b2);
		boolean res;
		if (bj > 0) {
			res = true;
		} else {
			res = false;
		}

		return res;
	}

	public static boolean strcompareTo2(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		int bj = b1.compareTo(b2);
		boolean res;
		if (bj >= 0) {
			res = true;
		} else {
			res = false;
		}

		return res;
	}

	public static boolean strcompareTo3(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		int bj = b1.compareTo(b2);
		return bj == 0;
	}

	public static BigDecimal bigRemainder(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			return v1.remainder(v2).setScale(scale, 4);
		}
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, 4).doubleValue();
		}
	}

	public static String strRound(String v, int scale) {
		if (scale < 0) {
			throw new RuntimeException("The scale must be a positive integer or zero.");
		} else {
			BigDecimal b = new BigDecimal(v);
			return b.setScale(scale, 4).toString();
		}
	}

}

