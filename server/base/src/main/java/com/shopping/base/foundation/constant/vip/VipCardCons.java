package com.shopping.base.foundation.constant.vip;

/**
 * 类描述: 会员卡常量类
 *
 * @author GuoFuJun
 * @date 2018/11/15 上午10:32
 */
public class VipCardCons {

    // <editor-fold desc="店铺会员充值订单常量">

    /**
     * ORDER_STATUS_WAIT_PAY : 待付款
     */
    public static final int ORDER_STATUS_WAIT_PAY = 10;

    /**
     * ORDER_STATUS_PAYED : 已支付
     */
    public static final int ORDER_STATUS_PAYED = 20;


    /**
     * ORDER_STATUS_VIP_RECHARGE : 开通&续费超级会员
     */
    public static final int ORDER_STATUS_VIP_RECHARGE = 1;

    // </editor-fold>


    // <editor-fold desc="会员卡状态常量">

    /**
     * ACCOUNT_VIP_STATUS_ABNORMAL : 账户状态异常
     */
    public static final Integer ACCOUNT_VIP_STATUS_ABNORMAL = 0;

    /**
     * ACCOUNT_VIP_STATUS_ABNORMAL : 账户状态正常
     */
    public static final Integer ACCOUNT_VIP_STATUS_NORMAL = 1;

    // </editor-fold>

    // <editor-fold desc="会员卡交易类型常量">

    /**
     * TRADE_TYPE_OF_PAY : 支出
     */
    public static final  Integer TRADE_TYPE_OF_PAY = 1;

    /**
     * TRADE_TYPE_OF_EARN : 收益
     */
    public static final  Integer TRADE_TYPE_OF_EARN = 2;

    /**
     * TRADE_TYPE_OF_GET : 提现
     */
    public static final  Integer TRADE_TYPE_OF_GET = 3;

    /**
     * TRADE_TYPE_OF_REFUND : 退款
     */
    public static final  Integer TRADE_TYPE_OF_REFUND = 4;

    /**
     * TRADE_TYPE_OF_GIVING : 赠送
     */
    public static final  Integer TRADE_TYPE_OF_GIVING = 5;

    /**
     * TRADE_TYPE_OF_RECHARGE : 充值
     */
    public static final Integer  TRADE_TYPE_OF_RECHARGE = 6;

    /**
     * TRADE_TYPE_OF_PAYMENT : 收付款
     */
    public static final Integer TRADE_TYPE_OF_PAYMENT = 7;


    // </editor-fold>

    //<editor-fold desc="充值支付渠道">
    /**
     * 微信小程序
     */
    public static final String TRADE_paySource_wxapp = "wxapp";

    /**
     * 微信
     */
    public static final String TRADE_paySource_wx = "wx";

    /**
     * 支付宝
     */
    public static final String TRADE_paySource_alipay = "alipay";

    /**
     * 支付宝小程序
     */
    public static final String TRADE_paySource_alipayapp = "alipayapp";
    //</editor-fold>
}
