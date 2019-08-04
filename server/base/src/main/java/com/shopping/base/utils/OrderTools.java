package com.shopping.base.utils;

import com.shopping.base.utils.constant.OrderCons;
import com.shopping.base.utils.constant.RefundCons;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单处理工具类
 */
@Component
public class OrderTools {

    private Logger logger = Logger.getLogger(OrderTools.class);

    /**
     * 根据订单类型和订单状态获取订单状态文字描述
     */
    public static String getOrderViewStatus(Integer order_type, Integer order_status) {

        String view_order_status = "";

        //百货订单显示订单状态信息
        if (Utils.isNotEmpty(order_type) && Utils.isNotEmpty(order_status) && order_type == 1) {
            switch (order_status) {
                case 0:
                    view_order_status = "已取消";
                    break;
                case 10:
                    view_order_status = "待付款";
                    break;
                case 15:
                    view_order_status = "线下支付待审核";
                    break;
                case 16:
                    view_order_status = "货到付款待发货";
                    break;
                case 20:
                    view_order_status = "待发货";
                    break;
                case 30:
                    view_order_status = "已发货";
                    break;
                case 40:
                    view_order_status = "已完成";
                    break;
                case 50:
                    view_order_status = "已结束";
                    break;
                case 60:
                    view_order_status = "交易关闭";
                    break;
            }
        }

        //餐饮订单订单状态
        if (Utils.isNotEmpty(order_type) && Utils.isNotEmpty(order_status) && (order_type == 4 || order_type == 6)) {
            //System.out.println(order_type+"....."+order_status);
            switch (order_status) {
                case 0:
                    view_order_status = "已取消";
                    break;
                case 10:
                    view_order_status = "待付款";
                    break;
                case 20:
                    view_order_status = "待处理";
                    break;
                case 25:
                    view_order_status = "已拒绝";
                    break;
                case 26:
                    view_order_status = "已接受";
                    break;
                case 40:
                    view_order_status = "已完成";
                    break;
                case 50:
                    view_order_status = "已结束";
                    break;
                case 60:
                    view_order_status = "交易关闭";
                    break;
                case 70:
                    view_order_status = "退款中";
                    break;
            }
        }
        return view_order_status;
    }

    public static String getOrderTypeViewStatis(Integer orderType) {
        String view_order_status = "";
        //百货订单显示订单状态信息
        switch (orderType) {
            case OrderCons.ORDER_CATEGORY_COMMON:
                view_order_status = "普通订单";
                break;
            case OrderCons.ORDER_CATEGORY_COUPON:
                view_order_status = "团购券订单";
                break;
            case OrderCons.ORDER_CATEGORY_PACKAGE:
                view_order_status = "团购套餐订单";
                break;
            case OrderCons.ORDER_CATEGORY_RESTAURANT_DESPOKE:
                view_order_status = "餐饮预约订单";
                break;
            case OrderCons.ORDER_CATEGORY_LEISURE:
                view_order_status = "休闲类订单";
                break;
            case OrderCons.ORDER_CATEGORY_TAKEAWAY:
                view_order_status = "外卖类订单";
                break;
            case OrderCons.ORDER_CATEGORY_REDPACKET:
                view_order_status = "红包订单";
                break;
            case OrderCons.ORDER_CATEGORY_RESERVATION:
                view_order_status = "订座订单";
                break;
            case OrderCons.ORDER_CATEGORY_SUBSCRIBE:
                view_order_status = "点餐订单";
                break;
        }
        return view_order_status;
    }


    public static String getOrder_RefundStatus_View(int refund_order) {
        String view_refund_status = "";
        switch (refund_order) {
            case RefundCons.STATE_VERIFY:
                view_refund_status = "待审核";
                break;
            case RefundCons.STATE_AGREE_RETURN:
                view_refund_status = "同意退货";
                break;
            case RefundCons.STATE_AGREE:
                view_refund_status = "同意退款";
                break;
            case RefundCons.STATE_DISAGREE:
                view_refund_status = "拒绝";
                break;
            case RefundCons.STATE_BACK:
                view_refund_status = "撤销";
                break;
            case RefundCons.STATE_RETURNED:
                view_refund_status = "买家已经退货";
                break;
            case RefundCons.STATE_REFUNDED:
                view_refund_status = "已退款";
                break;
            case RefundCons.STATE_DISAGREE_RETURN:
                view_refund_status = "拒绝退货";
                break;
            case RefundCons.APPLY_AFTER_SALES_AGREE:
                view_refund_status = "同意售后申请";
                break;
            case RefundCons.APPLY_AFTER_SALES_DISAGREE:
                view_refund_status = "拒绝售后申请";
                break;
            case RefundCons.STATE_UPDATE_VERIFY:
                view_refund_status = "修改申请,重新审核";
                break;
            case RefundCons.STATE_FAILED:
                view_refund_status = "退款失败";
                break;
            default:
        }
        return view_refund_status;
    }

    public static Map<String, Object> getOrderRefundProcessView(int refundOrder, String refundChannel) {
        Map refund = new HashMap(3);
        String channel = "alipay";
        refund.put("sellerDealWith", false);
        refund.put("adminDealWith", false);
        refund.put("dealWithSuccess", false);
        Boolean bol1 = (RefundCons.STATE_AGREE == refundOrder || RefundCons.STATE_DISAGREE == refundOrder ||
                RefundCons.STATE_REFUNDED == refundOrder || RefundCons.STATE_FAILED == refundOrder) && RefundCons.STATE_VERIFY != refundOrder;
        if (bol1) {
            refund.put("sellerDealWith", true);
        }

        Boolean bol2 = (RefundCons.STATE_DISAGREE == refundOrder || RefundCons.STATE_REFUNDED == refundOrder || RefundCons.STATE_FAILED == refundOrder) &&
                RefundCons.STATE_VERIFY != refundOrder;
        if (bol2 && channel.equals(refundChannel)) {
            refund.put("adminDealWith", true);
        }

        Boolean bol3 = (RefundCons.STATE_DISAGREE == refundOrder || RefundCons.STATE_REFUNDED == refundOrder || RefundCons.STATE_FAILED == refundOrder) &&
                RefundCons.STATE_VERIFY != refundOrder;
        if (bol3) {
            refund.put("dealWithSuccess", true);
        }
        return refund;
    }
}
