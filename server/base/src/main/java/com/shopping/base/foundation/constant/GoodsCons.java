package com.shopping.base.foundation.constant;

/**
 * 商品常量
 */
public class GoodsCons {

    /**
     * 无限库存标识
     */
    public static final int Unlimited = -100;

    //<editor-fold desc="商品状态">
    /**
     * 立即发布(上架)
     */
    public static final int ON_SALES = 0;
    /**
     * 放入仓库
     */
    public static final int ON_depot = 1;
    /**
     * 下架
     */
    public static final int OUT_SALES = -1;
    /**
     * 违规下架
     */
    public static final int OUT_SALES_Violation = -2;


    // 商户中台使用状态 //
    /**
     * 立即发布(上架)
     */
    public static final int PLATFORM_ON_SALES = 3;
    /**
     * 放入仓库
     */
    public static final int PLATFORM_ON_depot = 2;
    /**
     * 下架
     */
    public static final int PLATFORM_OUT_SALES = -3;
    // 商户中台使用状态 //

    //</editor-fold>

    //<editor-fold desc="商品营销状态">
    /**
     * 正常
     */
    public static final int GOODS_ACT_NORMAL = 0;
    /**
     * 砍价
     */
    public static final int GOODS_ACT_CUT = 1;
    /**
     * 2、特价、
     */
    public static final int GOODS_ACT_BARGAIN = 2;
    /**
     * 8、秒杀
     */
    public static final int GOODS_ACT_SECKILL = 8;
    /**
     * 拼团
     */
    public static final int GOODS_ACT_FIGHT_GROUP = 11;
    //</editor-fold>


}