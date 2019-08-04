package com.shopping.framework.sms.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.sms.conf.DayuConfig;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j
@Component
public class DaYuSms {

    @Autowired
    private DayuConfig dayuConfig;

    public boolean sendSms(String templateCode, String paramsJson, String mobile) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(dayuConfig.getApp_url(), dayuConfig.getApp_key(), dayuConfig.getApp_secret());
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType(dayuConfig.getSms_type());
            //req.setSmsFreeSignName(dayuConfig.getSign_name());
            req.setSmsFreeSignName("红商城");
            req.setSmsParamString(paramsJson);
            req.setRecNum(mobile);
            req.setSmsTemplateCode(templateCode);
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            log.info("发送短信结果：" + rsp.getBody());
            if (CommUtils.isNotNull(rsp.getBody())) {
                JSONObject json = JSON.parseObject(rsp.getBody()).getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
                if (json == null) {
                    return false;
                }
                JSONObject result = json.getJSONObject("result");
                if (CommUtils.isNotNull(json.get("result")) && result.getBoolean("success")) {
                    //发送短信成功
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
