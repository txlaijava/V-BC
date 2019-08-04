package com.shopping.base.utils.constant;

import org.springframework.web.servlet.ModelAndView;

/**
 * 订单常量
 */
public class OrderCons {
	// 订单状态
    public static final int CANCEL = 0;                                       // 已取消
    public static final int WAIT_PAY = 10;                                    // 待付款
	public static final int WAIT_GROUP = 15;                                  // 组团中
	public static final int GROUP_FAIL = 18;                                  // 组团失败
	public static final int PAYED = 20;                                       // 已支付
    public static final int WAIT_SEND = 20;                                   // 待发货
    public static final int WAIT_HANDLE = 20;                                 // 待处理
	public static final int REFUSE = 25;                                      // 已拒绝
	public static final int ACCEPTED = 26;                                    // 已接受
    public static final int WAIT_GET = 30;                                    // 待收货
	public static final int FINISH = 40;                                      // 已完成
	public static final int OVER = 50;                                        // 已结束
	public static final int TRANS_CLOSE = 60;                                 // 交易关闭
    public static final int REFUND = 70;                                      // 退款中
    public static final int REFUND_FAIL = 71;                                 // 退款拒绝   // 目前针对外卖
    public static final int SYS_CANCEL = 80;                                  // 系统取消状态 ps：餐饮预约单定时取消状态
    public static final int ACCEPT = 90;                                      // 已接受    //针对外卖员
    public static final int REJECT = 91;                                      // 已拒绝    //针对外卖员
    public static final int STAY_HANDLE = 92;                                 // 待处理    //针对外卖员



    //<editor-fold desc="线下付款订单状态">
    public static final int PAYMENT_ORDER_STATUS_WAITPAY = 30;
    //</editor-fold>


    public static final int WAIT_REDPACKET_SEND = 10;                         // 待发放
    public static final int REDPACKET_SEND = 20;                              // 发放中
    public static final int WAIT_REDPACKET_GET = 30;                          // 待领取


    // 订单来源
    public static final int ORDER_FROM_WEIXIN = 1;                            // weixin
    public static final int ORDER_FROM_IOS = 2;                               // Ios
    public static final int ORDER_FROM_ANDROID = 3;                           // Android
    public static final int ORDER_FROM_PC = 4;                                // Pc
    public static final int ORDER_FROM_WXAPP = 5;                                // 微信小程序
    public static final int ORDER_FROM_APP = 6;                                // APP

    // 订单分类
    public static final int ORDER_CATEGORY_COMMON = 1;                        // 常规订单
    public static final int ORDER_CATEGORY_COUPON = 2;                        // 团购券订单
    public static final int ORDER_CATEGORY_PACKAGE = 3;                       // 团购套餐订单
    public static final int ORDER_CATEGORY_RESTAURANT_DESPOKE = 4;            // 餐饮预约订单
    public static final int ORDER_CATEGORY_LEISURE = 5;                       // 休闲类订单
    public static final int ORDER_CATEGORY_TAKEAWAY = 6;                      // 外卖类订单
    public static final int ORDER_CATEGORY_REDPACKET = 7;                     // 红包订单
    public static final int ORDER_CATEGORY_RESERVATION = 8;                   // 订座订单
    public static final int ORDER_CATEGORY_SUBSCRIBE = 9;                     // 点餐订单
    public static final int ORDER_CATEGORY_Delta = 0;                         // 充值订单
    public static final int ORDER_CATEGORY_Delta_telephoneCharge = 11;        // 话费充值
    public static final int ORDER_CATEGORY_Delta_flowCharge = 12;             // 流量充值
    public static final int ORDER_CATEGORY_Ticket = 13;                       // 售票订单
	public static final int ORDER_CATEGORY_FIGHT_GROUP = 14;                  // 拼团订单
    public static final int ORDER_CATEGORY_MOVIE_TICKETS = 15;                // 电影票订单
    public static final int ORDER_CATEGORY_OFFLINE = 16;                      // 线下支付订单
    public static final int ORDER_CATEGORY_PARK_CAR = 17;                      // 停车订单
    public static final int ORDER_CATEGORY_BARGAIN = 18;                      // 砍价订单

    public static final int ORDER_CATEGORY_HB_RECHARGE = 60;                  //红宝充值
    public static final int ORDER_SELLER_CASHOUT = 63;                        //红掌柜商家提现订单
    public static final int ORDER_USER_CASHOUT = 64;                            //分销用户提现订单

    // 订单类型
    public static final int ORDER_TYPE_COMMON = 1;                            // 总订单/订单公共
    public static final int ORDER_TYPE_CHILD = 2;                             // 子订单
	public static final int ORDER_TYPE_REDSHOP = 3;                           // 红小店订单
    public static final int ORDER_TYPE_PAYMENT = 4;                           // 付款订单
    public static final String ORDER_TYPE_HYG = "C";                          // 红云购订单
    public static final int ORDER_TYPE_AC_HB = 6;                             // 红宝充值
    public static final String ORDER_TYPE_VIP_RECHARGE = "D";                 // 商家会员充值
    public static final String ORDER_TYPE_SVIP_OPEN = "S";                 // 开通超级会员或者续费超级会员

    public static final int ORDER_TYPE_GIFT = 7;                 // 红商城礼品卡

    // 配送方式
    public static final int SEND_TYPE_SEND = 1;                               // 送货上门
    public static final int SEND_TYPE_RECEIVE = 2;                            // 上门自提

    // 退货方式
    public static final int ORDER_REFUND_SINGLE = 1;                          // 单退
    public static final int ORDER_REFUND_ALL = 2;                             // 全退


    // 退款状态
    public static final int ORDER_REFUND_STATE_NORMAL = 1;                    // 正常
    public static final int ORDER_REFUND_STATE_DISPOSE = 2;                   // 退款处理中
    public static final int ORDER_REFUND_STATE_SUCCESS = 3;                   // 退款成功
    public static final int ORDER_REFUND_STATE_FAIL = 4;                      // 退款被拒绝
    public static final int ORDER_APPLY_AFTER_SALES_DISPOSE = 5;              // 申请售后处理中
    public static final int ORDER_APPLY_AFTER_SALES_SUCCESS = 6;              // 申请售后成功
    public static final int ORDER_APPLY_AFTER_SALES_FAIL = 7;                 // 申请售后被拒绝
    public static final int ORDER_REFUND_STATE_WATING_ACCOUNT=8;              // 等待到账

    // 收款类型
    public static final int ORDER_TYPE_FIXED_PAY = 2;                         // 固定付
    public static final int ORDER_TYPE_ARBITRARILY_PAY = 1;                   // 任意付
	public static final int ORDER_TYPE_SWEEQ_PAY = 3;                         // 扫码付款
    public static final int ORDER_TYPE_PARK_CAR_PAY = 4;                      // 停车付款

    // 订单状态常量设入
	public static void setOrderStatusInMv(ModelAndView mv){
		mv.addObject("CANCEL", CANCEL);
		mv.addObject("WAIT_PAY", WAIT_PAY);
		mv.addObject("PAYED", PAYED);
		mv.addObject("WAIT_GROUP", WAIT_GROUP);
		mv.addObject("GROUP_FAIL", GROUP_FAIL);
		mv.addObject("WAIT_SEND", WAIT_SEND);
		mv.addObject("WAIT_HANDLE", WAIT_HANDLE);
		mv.addObject("WAIT_GET", WAIT_GET);
		mv.addObject("TRANS_CLOSE", TRANS_CLOSE);
		mv.addObject("ACCEPTED", ACCEPTED);
		mv.addObject("REFUSE", REFUSE);
		mv.addObject("FINISH", FINISH);
		mv.addObject("OVER", OVER);
		mv.addObject("WAIT_REDPACKET_SEND",WAIT_REDPACKET_SEND);
		mv.addObject("REDPACKET_SEND",REDPACKET_SEND);
		mv.addObject("WAIT_REDPACKET_GET",WAIT_REDPACKET_GET);
		mv.addObject("REFUND",REFUND);
		mv.addObject("REFUND_FAIL",REFUND_FAIL);
        mv.addObject("ACCEPT",ACCEPT);
        mv.addObject("REJECT",REJECT);
        mv.addObject("STAY_HANDLE",STAY_HANDLE);

		mv.addObject("ORDER_REFUND_STATE_NORMAL", ORDER_REFUND_STATE_NORMAL);
		mv.addObject("ORDER_REFUND_STATE_DISPOSE", ORDER_REFUND_STATE_DISPOSE);
		mv.addObject("ORDER_REFUND_STATE_SUCCESS", ORDER_REFUND_STATE_SUCCESS);
        mv.addObject("ORDER_REFUND_STATE_FAIL", ORDER_REFUND_STATE_FAIL);
		mv.addObject("ORDER_APPLY_AFTER_SALES_DISPOSE", ORDER_APPLY_AFTER_SALES_DISPOSE);
        mv.addObject("ORDER_APPLY_AFTER_SALES_SUCCESS", ORDER_APPLY_AFTER_SALES_SUCCESS);
        mv.addObject("ORDER_APPLY_AFTER_SALES_FAIL", ORDER_APPLY_AFTER_SALES_FAIL);
        mv.addObject("ORDER_REFUND_STATE_WATING_ACCOUNT", ORDER_REFUND_STATE_WATING_ACCOUNT);
		mv.addObject("ORDER_CATEGORY_TAKEAWAY", ORDER_CATEGORY_TAKEAWAY);
	}


	// 配送方式常量设入
	public static void setSendTypeInMv(ModelAndView mv){
		mv.addObject("SEND_TYPE_RECEIVE", SEND_TYPE_RECEIVE);
		mv.addObject("SEND_TYPE_SEND", SEND_TYPE_SEND);
	}
}