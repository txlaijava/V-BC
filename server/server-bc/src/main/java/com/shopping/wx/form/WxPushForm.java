package com.shopping.wx.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 微信推送参数类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WxPushForm implements Serializable {

    private static final long serialVersionUID = -2624253925403159396L;

    /**
     * 获取微信模板消息配置
     */
    private ArrayList<WxPushTemplateForm> wxMaTemplateDatas;

    /**
     * 模板类型(常量类)
     */
    private String push_type;

    /**
     * 微信模板编号ID
     */
    private String template_no;

    /**
     * 微信小程序appID
     */
    private String app_id;

    /**
     * 页面链接
     */
    private String page;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 微信表单id
     */
    private String formId;
}
