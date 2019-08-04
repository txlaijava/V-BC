package com.shopping.base.utils.constant;

import org.springframework.web.servlet.ModelAndView;

/**
 * 提现状态
 *
 * @author wyj
 *
 */
public class BalanceStatus {
	/*OrderBalance*/
	// 未结算
	public static final int NO_BALANCE = 0;
	// 已结算
	public static final int BALANCED = 1;

	/*Balance*/
	// 提现中
	public static final int BALANCE_STATUS_ON = 0;
	// 已提现
	public static final int BALANCE_STATUS_OK = 1;


	public static void setBalanceStatusInMv(ModelAndView mv){
		mv.addObject("NO_BALANCE",NO_BALANCE);
		mv.addObject("ON_BALANCE",BALANCED);
		mv.addObject("BALANCE_STATUS_ON",BALANCE_STATUS_ON);
		mv.addObject("BALANCE_STATUS_OK",BALANCE_STATUS_OK);
	}
}
