package com.shopping.base.utils.constant;

/**
 * 退款常量
 *
 * @author 邹
 */
public class RefundCons {

    //退款订单状态
    public static final int STATE_VERIFY = 1;                                      // 待审核
    public static final int STATE_AGREE_RETURN = 2;                                // 同意退货
    public static final int STATE_AGREE = 3;                                       // 同意
    public static final int STATE_DISAGREE = 4;                                    // 不同意
    public static final int STATE_BACK = 5;                                        // 撤销
    public static final int STATE_RETURNED = 6;                                    // 买家已经退货
    public static final int STATE_REFUNDED = 7;                                    // 已退款
    public static final int STATE_DISAGREE_RETURN = 8;                             // 拒绝退货
    public static final int APPLY_AFTER_SALES_AGREE = 9;                           // 同意售后申请
    public static final int APPLY_AFTER_SALES_DISAGREE = 10;                       // 拒绝售后申请
    public static final int STATE_UPDATE_VERIFY = 11;                              // 修改申请,重新审核
    public static final int STATE_FAILED = 12;                                     // 退款失败
    public static final int STATE_AGREE_PARTOF = 13;                               // 部分金额退款

    //退货方式
    public static final int EXPRESS_TYPE_EXPRESS = 1;                              // 快递
    public static final int EXPRESS_TYPE_MAIL = 2;                                 // 平邮
    public static final int EXPRESS_TYPE_EMS = 3;                                  // ems
    public static final int EXPRESS_TYPE_VISIT = 4;                                // 送货上门


    public static final int ORDER_TYPE_REFUND = 1;                                // 退款
    public static final int ORDER_TYPE_REFUND_AND_REFUNDGOODS = 2;                  // 退款退货

}