package com.shopping.wx.form;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 微信推送参数类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class WxPushTemplateForm extends WxMaTemplateData implements Serializable{

    private static final long serialVersionUID = -2624253925403159396L;

    private String name;
    private String value;
    private String color;

    public WxPushTemplateForm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public WxPushTemplateForm(String name, String value, String color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}
