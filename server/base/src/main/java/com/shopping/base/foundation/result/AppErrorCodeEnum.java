package com.shopping.base.foundation.result;

/**
 * 类描述：APP异常枚举类
 *
 * @author：txl
 */
public enum AppErrorCodeEnum {

    /**
     * 500，未知错误
     */
    UNKNOWN_ERROR(500,"未知错误！"),

    /**
     * 系统错误
     */
    SYS_ERROR(5000,"系统业务异常"),

    /**
     * 参数错误
     */
    PARM_NULL_ERROR(1000,"参数异常,请检查参数的信息！"),
    PARM_EMAIL_NULL_ERROR(1001,"参数异常,邮箱地址为空！"),
    PARM_PWD_NULL_ERROR(1002,"参数异常,密码为空！"),
    PARM_MOBILE_NO_NULL_ERROR(1003,"参数异常,手机号码为空！"),
    PARM_SMS_CODE_NULL_ERROR(1004,"参数异常,短信验证码为空！"),
    PARM_SMS_TEMPLATE_CODE_NULL_ERROR(1005,"参数异常,发送短信模板编号为空！"),
    PARM_ACNAME_NULL_ERROR(1006,"参数异常,账户真实姓名为空！"),
    PARM_ACCOMPANY_NULL_ERROR(1007,"参数异常,账户所属公司为空！"),
    PARM_APPLYNAME_NULL_ERROR(1008,"参数异常,应用名称为空！"),
    PARM_APPLY_AC_NULL_ERROR(1008,"参数异常,应用所属账户为空！"),
    PARM_AC_ID_NULL_ERROR(1009,"参数异常,账户编号为空！"),
    PARM_AC_PARENT_ID_NULL_ERROR(1010,"参数异常,父级账户编号为空！"),
    PARM_APPLY_ID_NULL_ERROR(1011,"参数异常,应用编号为空！"),
    PARM_THEME_ID_NULL_ERROR(1012,"参数异常,主题编号为空！"),
    PARM_THEME_ITEM_TYPE_NULL_ERROR(1013,"参数异常,主题模板类型为空！"),
    ;

    private Integer code;

    private String msg;

    AppErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}