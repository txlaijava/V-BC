package com.shopping.base.foundation.constant.act;

/**
 * 砍价常量类
 */
public class ActBargainingCons {

    //<editor-fold desc="砍价活动状态">
    /**
     * 进行中
     */
    public static final int ACT_STATUS_OK = 10;

    /**
     * 已结束
     */
    public static final int ACT_STATUS_END = 20;

    /**
     * 已关闭
     */
    public static final int ACT_STATUS_CLOSE = 30;
    //</editor-fold>

    //<editor-fold desc="砍价记录状态">
    /**
     * 砍价中
     */
    public static final int ACT_RECORD_INCUT = 10;

    /**
     * 已支付
     */
    public static final int ACT_RECORD_PAYED = 20;

    /**
     * 交易超时取消
     */
    public static final int ACT_RECORD_CANCEL = 30;

    /**
     * 订单等待付款
     */
    public static final int ACT_RECORD_WAITPAY = 40;
    //</editor-fold>

    //<editor-fold desc="砍价类型">
    /**
     * 1:仿照拼多多,前30%人砍,50%价格
     */
    public static final int ACT_CUT_TYPE_PDD = 1;

    /**
     * 2:常规随机砍价
     */
    public static final int ACT_CUT_TYPE_RANDOM = 2;
    //</editor-fold>
}
