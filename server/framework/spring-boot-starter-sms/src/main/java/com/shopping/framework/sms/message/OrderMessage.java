package com.shopping.framework.sms.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor    //全参构造方法
public class OrderMessage {
    private String msgUrl;
    //1 是浏览器打开消息，2是app打开消息 3签到提醒
    private int openType = 2;
    private String title;
    private String msgDesc;
    private String pushWay;
    //接收终端  rblc==用户端  rblc_seller==商户端
    private String platform_type;

}