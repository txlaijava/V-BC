package com.shopping.base.foundation.constant.act;

/**
 * 拼团常量类
 */
public class ActGroupCons {

    //<editor-fold desc="活动状态">
    /**
     * 进行中
     */
    public static final int ACT_STATUS_OK = 3;
    /**
     * 已结束
     */
    public static final int ACT_STATUS_END = 4;

    /**
     * 审核中
     */
    public static final int ACT_STATUS_APPROVE = 2;
    //</editor-fold>

    //<editor-fold desc="组团状态">
    /**
     * 订单提交
     */
    public static final int GROUP_STATUS_ORDER = 0;
    /**
     * 组团中
     */
    public static final int GROUP_STATUS_IN = 1;
    /**
     * 组团失败
     */
    public static final int GROUP_STATUS_FAIL = 2;

    /**
     * 组团成功
     */
    public static final int GROUP_STATUS_OK = 3;
    //</editor-fold>
}
