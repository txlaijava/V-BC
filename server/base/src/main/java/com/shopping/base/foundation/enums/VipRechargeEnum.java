package com.shopping.base.foundation.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 类描述:
 *
 * @author GuoFuJun
 * @date 2018/12/28 下午2:08
 */
public enum VipRechargeEnum {

    /**
     * 订单状态
     */
    STATUS_WAITING_PAY("10","订单提交"),
    STATUS_PAY_SUCCEED("20","支付成功"),
    STATUS_RECHARGE_SUCCEED("30","充值成功"),
    STATUS_RECHARGE_FAIL("40","充值失败"),

    TYPE_VIP_RECHARGE("0","充值"),
    TYPE_OPEN_SUPER_VIP("1","超级会员"),

    WX_CHANNEL("wx", "微信支付"),
    ALIPAY_CHANNEL("alipay", "支付宝支付"),
    WX_APP_CHANNEL("wxapp", "微信小程序支付"),
    NOT_PAY_CHANNEL("notpay", "未支付"),
    ;

    private String code;

    private String msg;

    VipRechargeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static VipRechargeEnum getEnumByCode(String code){
        for(VipRechargeEnum vipRechargeEnum : VipRechargeEnum.values()){
            if(StringUtils.equals(code, vipRechargeEnum.code)){
                return vipRechargeEnum;
            }
        }
        return null;
    }
}
