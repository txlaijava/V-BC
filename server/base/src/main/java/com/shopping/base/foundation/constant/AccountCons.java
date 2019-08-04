package com.shopping.base.foundation.constant;

/**
 * 账户相关常量
 */
public class AccountCons {

    /**
     * 平台账户id
     */
    public static final Long ADMIN_ACC_ID = new Long(10000000);

    public static final Long DEFAULT_HB_ID = new Long(1);

    /**********************账户类型************************/
    /**
     * 账户-平台
     */
    public static final  int ACCOUNT_TYPE_OF_PLATFORM = 1;
    /**
     * 账户-商家
     */
    public static final  int ACCOUNT_TYPE_OF_SELLER = 2;
    /**
     * 账户-个人
     */
    public static final  int ACCOUNT_TYPE_OF_BUYER = 3;




    /**********************账户级别************************/
    /**
     * 平台账户的级别
     */
    public static final  int ACCOUNT_LEVEL_OF_PLATFORM = 1;
    /**
     * 个人与一般的商家账户的级别
     */
    public static final  int ACCOUNT_LEVEL_OF_SELLER_AND_BUYER = 2;
    /**
     * 连锁或综合商城下属商户的级别
     */
    public static final  int ACCOUNT_LEVEL_OF_CHAIN = 3;


    /**********************账户交易类型************************/
    //结算
    public static final int ACCOUNT_ACMOUNT_TYPE_BALANCE = 0;
    //提现
    public static final int ACCOUNT_ACMOUNT_TYPE_OUT = 1;
    //佣金
    public static final int ACCOUNT_ACMOUNT_TYPE_YJ = 2;
    //充值
    public static final int ACCOUNT_ACMOUNT_TYPE_CHARGE = 3;
    //退款
    public static final int ACCOUNT_ACMOUNT_TYPE_REFUEND = 4;
    //提现拒绝
    public static final int ACCOUNT_ACMOUNT_TYPE_OUT_REJECT = 5;




    /**********************账户状态************************/
    /**
     * 账户状态-正常
     */
    public static final  int ACCOUNT_STATUS_OF_ABLE = 0;

    /**
     * 账户状态-禁用
     */
    public static final  int ACCOUNT_STATUS_OF_ENABLE = 1;

    /**
     * 账户状态-锁定
     */
    public static final  int ACCOUNT_STATUS_OF_LOCK = 2;


    /**********************第三方账户类型************************/
    /*帐号类型：1.支付宝；2.微信;  3.银行卡 4.其它*/

    public static final  int BIND_TYPE_OF_ALIPAY = 1;

    public static final  int BIND_TYPE_OF_WEIXIN = 2;

    public static final  int BIND_TYPE_OF_BANK = 3;

    public static final  int BIND_TYPE_OF_OTHER = 4;

    /*第三方账户绑定状态*/

    public static final  int BIND_STATUS_OF_ABLE = 1;

    public static final  int BIND_STATUS_OF_ENABLE = 2;

    public static final  int BIND_STATUS_OF_REMOVE = 3;

    /*银行卡类型*/
    public static final  int BANK_TYPE_OF_CASHCARD = 1;

    public static final  int BANK_TYPE_OF_CREDITCARD = 2;



    /*交易类型*/
    //public static final  int TRADE_TYPE_OF_RECHARNGE = 0;   //充值

    public static final  int TRADE_TYPE_OF_PAY = 1;         //支出

    public static final  int TRADE_TYPE_OF_EARN = 2;        //收益

    public static final  int TRADE_TYPE_OF_GET = 3;         //提现

    public static final  int TRADE_TYPE_OF_REFUND = 4;      //退款

    public static final  int TRADE_TYPE_OF_GIVING = 5;      //赠送

    public static final int  TRADE_TYPE_OF_RECHARGE = 6;    //充值

    public static final  int TRADE_TYPE_OF_PAYMENT = 7;     //收付款

    public static final  int TRADE_TYPE_OF_INVITEEARN = 8;  // 邀约充值收益


    /*交易源 1.余额 2.红宝 3.微信 4 微信公众号 5.支付宝 6.银行卡*/
    public static final  int TRADE_SOURCES_TYPE_OF_BALANCE = 1;      // 余额

    public static final  int TRADE_SOURCES_TYPE_OF_HB = 2;           // 红宝

    public static final  int TRADE_SOURCES_TYPE_OF_WEIXIN = 3;       // 微信

    public static final  int TRADE_SOURCES_TYPE_OF_WEIXIN_PUB = 4;   // 微信公众号

    public static final  int TRADE_SOURCES_TYPE_OF_ALIPAY = 5;       // 支付宝

    public static final  int TRADE_SOURCES_TYPE_OF_BANK = 6;         // 银行卡

    /* 交易源 7.线上订单赠送红宝 8.线下订单赠送红宝 9.活动赠送红宝 10.平台赠送红宝 11.邀约充值赠送红宝 12.优惠券兑换赠送红宝（赠送使用类型） */
    public static final  int TRADE_SOURCES_TYPE_OF_ORDER = 7;               // 线上订单赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_PAYMENT_ORDER = 8;       // 线下订单赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_ACTIVITY = 9;            // 活动赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_ADMIN = 10;              // 平台赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_RECHARGE = 11;           // 邀约充值赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_COUPON = 12;             // 优惠券兑换赠送红宝（赠送使用类型）

    public static final  int TRADE_SOURCES_TYPE_OF_DUIBA = 13;             // 兑吧积分平台

    public static final  int TRADE_SOURCES_TYPE_OF_HBCARD = 14;             // 红宝卡兑换

    public static final  int TRADE_SOURCES_TYPE_OF_PARK = 15;             // 停车缴费

    public static final  int TRADE_SOURCES_TYPE_OF_WXAPP = 18;         // 微信小程序

    public static final  int TRADE_SOURCES_TYPE_OF_VIP_CARD = 19;         // 会员卡

    /*交易状态：  10 订单提交   20 订单支付成功   30 处理成功  40 处理失败*/
    public static final  int TRADE_STATUS_OF_SUB = 10; //订单提交

    public static final  int TRADE_STATUS_OF_SUCCESS = 20; //订单支付成功

    public static final  int TRADE_STATUS_OF_DEAL_SUCCESS = 30; //处理成功

    public static final  int TRADE_STATUS_OF_DEAL_FAIL = 40; //处理失败

    /*红宝类型： 1 常规赠送 2 固定赠送 3 冻结赠送*/
    public static final int CONVENTION_TYPE = 1;     //常规赠送类型

    public static final int FIXED_TYPE = 2;          //固定赠送

    public static final int INSTALLMENT_TYPE = 3;    //冻结赠送

    public static final int ALL_INSTALLMENT_TYPE = 4; //全额冻结

    /*状态  0:已取消  1:待审核  2:审核拒绝  3:审核通过(已提交付款请求至第三方)  4:付款异常  5:提现成功*/
    public static final int CASH_OUT_CANCEL = 0;

    public static final int CASH_OUT_AUDIT= 1;

    public static final int CASH_OUT_AUDIT_REFUSED = 2;

    public static final int CASH_OUT_AUDIT_OK = 3;

    public static final int CASH_OUT_ERROR = 4;

    public static final int CASH_OUT_SUCCESS = 5;

    /*红宝交易结算状态*/
    //0未清算 1 已清算 2 存在退款 3 已退完 4 已结算
    public static final int WAIT_BALANCE = 0;

    public static final int BALANCE_SUCCESS= 1;

    public static final int BALANCE_REFUNDING = 2;

    public static final int BALANCE_REFUNDED = 3;

    public static final int BALANCED = 4;
}
