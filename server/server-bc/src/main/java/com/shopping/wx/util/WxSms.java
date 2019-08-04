package com.shopping.wx.util;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import com.shopping.wx.conf.WxMaConfiguration;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述:
 *
 * @author wpl
 * @date 2018/11/30 14:02
 */
@Log4j2
@Component
public class WxSms {

    /**
     * 常规微信推送接口
     * @param uniformMessage
     * @return
     */
    public Boolean UniformMsg(WxMaUniformMessage uniformMessage){
        try {
            final WxMaService wxService = WxMaConfiguration.getMaServices().get(uniformMessage.getAppid());
            if (wxService == null) {
                throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", uniformMessage.getAppid()));
            }
            log.info("调用微信模板消息接口");
            wxService.getMsgService().sendUniformMsg(uniformMessage);

        } catch (Exception e) {
            log.error("微信发送模板消息接口异常", e);
        }
        return false;
    }

    public void wxPushByAppId(String appId, String type,String openId,String formId,String page, List<WxMaTemplateData> dataList) throws Exception{
        WxMaUniformMessage uniformMessage = WxMaUniformMessage.builder()
                .templateId(type).appid(appId)
                .page(page).toUser(openId).formId(formId)
                .data(dataList).build();
        this.UniformMsg(uniformMessage);
    }

    public byte[] wxQr(String appid,String scene,String page){
        try {
            final WxMaService wxMaService = WxMaConfiguration.getMaServices().get(appid);
            byte[] b = wxMaService.getQrcodeService().createWxaCodeUnlimitBytes(scene,page,430,true, (WxMaCodeLineColor)null, false);
            return b;
        } catch (Exception e) {
            log.error("获取小程序码异常", e);
        }
        return null;
    }
}
