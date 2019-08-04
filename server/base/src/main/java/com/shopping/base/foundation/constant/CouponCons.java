package com.shopping.base.foundation.constant;

/**
 * 优惠券常量
 */
public class CouponCons {
    //<editor-fold desc="券类型">
    /**
     * 优惠券
     */
    public static final int TYPE_COUPONS = 1;
    /**
     * 折扣券
     */
    public static final int TYPE_DISCOUNT = 4;
    /**
     * 代金券
     */
    public static final int TYPE_GOLD = 5;
    /**
     * 礼品券
     */
    public static final int TYPE_GIFT = 6;
    /**
     * 红宝券
     */
	public static final int TYPE_HB = 7;
    /**
     * 现金券
     */
	public static final int TYPE_XFK = 8;
    /**
     * 停车券
     */
	public static final int TYPE_CAR = 9;
    //</editor-fold>

    //<editor-fold desc="优惠券适用用户">
    /**
     * 全部用户适用
     */
    public static final int COUPON_VIP_DIS_ALL = 0;

    /**
     * VIP专享
     */
    public static final int COUPON_VIP_DIS_VIP = 1;

    /**
     * 生日用户
     */
    public static final int COUPON_VIP_DIS_BIR = 2;

    /**
     * 会员充值
     */
    public static final int COUPON_VIP_DIS_VIP_RECHARGE = 6;

    /**
     * 超级会员
     */
    public static final int COUPON_VIP_DIS_VIP_SUPER = 7;
    //</editor-fold>

    //<editor-fold desc="优惠券领取类型">
    /**
     * 固定日期
     */
    public static final int COUPON_valid_type_DAY = 1;

    /**
     * 领取后多少天失效
     */
    public static final int COUPON_valid_type_DAY_AFTER = 2;
    //</editor-fold>

    //<editor-fold desc="优惠券状态">
    /**
     * 草稿
     */
    public static final int STATUS_DRAFT = 2;
    /**
     * 发布
     */
    public static final int STATUS_ISSUE = 0;
    /**
     * 撤销
     */
    public static final int STATUS_REPEAL = 4;
    //</editor-fold>

    //<editor-fold desc="优惠券核销方式">
    /**
     * 网页核销
     */
    public static final int VERIFY_TYPE_WEB = 1;
    /**
     * 手机核销
     */
    public static final int VERIFY_TYPE_MOBILE = 2;
    //</editor-fold>

    //<editor-fold desc="优惠券来源">
    /**
     * 用户主动领取
     */
    public static final String COUPON_FROM_USER = "user";

    /**
     * 商品派发
     */
    public static final String COUPON_FROM_GOODS = "goods";

    /**
     * 红宝充值
     */
    public static final String COUPON_FROM_ACT_HB = "hb";

    /**
     * 积分兑换
     */
    public static final String COUPON_FROM_INTEGRAL = "integral";

    /**
     * 商户派发
     */
    public static final String COUPON_FROM_SELLER = "seller";
    //</editor-fold>

    //<editor-fold desc="优惠券指定门店类型">
    /**
     * 所有店铺通用
     */
    public static final int COUPON_APPLY_TYPE_ALL = 1;

    /**
     * 指定门店
     */
    public static final int COUPON_APPLY_TYPE_STORE = 2;
    //</editor-fold>

    //<editor-fold desc="优惠券状态">
    /**
     * 正常
     */
    public static final int COUPON_INFO_STATUS_NORMAL = 0;

    /**
     * 已使用
     */
    public static final int COUPON_INFO_STATUS_USED = 1;

    /**
     * 已过期
     */
    public static final int COUPON_INFO_STATUS_EXPIRED = 2;
    //</editor-fold>

}