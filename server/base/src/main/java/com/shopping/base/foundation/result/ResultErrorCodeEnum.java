package com.shopping.base.foundation.result;

/**
 * 类描述：异常枚举类
 *
 * @author：GuoFuJun
 * @date：2018年05月08日 15:25:03.
 */
public enum ResultErrorCodeEnum {

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

    /**
     * 对象错误
     */
    ACCOUNT_NULL_ERROR(2000,"未找到用户账户对象！"),
    ACCOUNT_PWD_ERROR(2001,"账号密码错误！"),
    DEL_SUB_ACCOUNT_PARENT_NULL_ERROR(2002,"删除子账号错误，该账号不是子账号！"),
    DEL_SUB_ACCOUNT_NOT_PARENT_ID_ERROR(2003,"删除的子账号，不是对应账户下的子账号！"),
    UPDATA_SUB_ACCOUNT_NOT_PARENT_ID_ERROR(2004,"修改的子账号信息，不是对应账户下的子账号！"),
    THEME_NULL_ERROR(2005,"未找到主题对象！"),

    /**
     * 短信接口错误
     */
    SMS_API_ERROR(3000,"短信发送失败！，接口异常"),
    SMS_API_SEND_ERROR(3001,"短信发送失败！"),


    /**
     * 验证错误
     */
    VALID_SMS_MOBILE_NO_ERROR(4000,"该手机号码没有发送验证码！"),
    VALID_SMS_CODE_ERROR(4001,"验证失败,验证码错误！"),
    VALID_EMAIL_MORE_ERROR(4002,"验证失败, 该邮箱已绑定账号！"),
    VALID_MOBILENO_MORE_ERROR(40023,"验证失败, 该手机号码已绑定账号！"),


    //<editor-fold desc="会员卡相关">
    VIP_NO_PASSWORD(1001,"会员卡密码为空,请设置"),
    VIP_PASSWORD_ERROR(1002,"会员卡密码输入错误")
    //</editor-fold>
    ;

    private Integer code;

    private String msg;

    ResultErrorCodeEnum(Integer code, String msg) {
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