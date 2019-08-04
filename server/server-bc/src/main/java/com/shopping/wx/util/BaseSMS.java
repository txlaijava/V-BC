package com.shopping.wx.util;

import com.alibaba.fastjson.JSON;
import com.shopping.base.domain.other.Template;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.sms.core.DaYuSms;
import com.shopping.framework.sms.core.OthSms;
import com.shopping.wx.service.bc.ITemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Log4j2
@Component
public class BaseSMS {

    @Autowired
    private DaYuSms daYuSms;

    @Autowired
    private ITemplateService templateService;

    /**
     * 发送手机验证码
     * @param mobileNo
     * @return
     */
    public Map<String, Object> sendSMS(String mobileNo) {
        //生成4位随机手机验证码
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String authCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        log.info("手机验证码：" + authCode);
        paramMap.put("authCode", authCode);
        boolean flag = this.getSMSTemplateAndSend(paramMap, mobileNo, "sms_tobuyer_user_register_notify");
        paramMap.put("flag", flag);
        return paramMap;
    }

    /**
     * 手机号码通过SMS模板进行发送消息
     * @param paramMap
     * @param mobile
     * @param mark      模板标识
     * @return
     */
    public boolean getSMSTemplateAndSend(Map<String, Object> paramMap, String mobile, String mark) {
        boolean flag = false;
        try {
            //获取模板
            log.info("进入发短信方法");
            Template template = this.templateService.getOneObjByProperty("mark", mark);
            //判断模板状态为“开启”
            if ((template != null) && (template.isOpen())) {
                String content = template.getContent();
                //获取Map对象里的键值
                Iterator it = paramMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String key = "\\$!\\{"+entry.getKey().toString()+"\\}";
                    content = content.replaceAll(key,entry.getValue().toString());
                    //设置模板里参数及值
                    //context.put(entry.getKey().toString(), entry.getValue());
                }
                // 去掉模板里自带的<pre></pre>标签，如果不去，则无法发送。
                content = this.formatTemplate(content);
                // 判断是发短信还是推送消息
                if(CommUtils.null2String(template.getType()).contains("sms")) {
                    // 如果有阿里大鱼模板
                    if (!"".equals(CommUtils.null2String(template.getCode_id()))) {
                        // 阿里大鱼发送
                        flag = this.sendDayuSMS(template.getCode_id(), JSON.toJSONString(paramMap), mobile);
                    }else{
                        // 其他手机短信接口
                        flag = this.sendSMS(mobile, content);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取模板并发送失败", e);
        }
        return flag;
    }

    private String formatTemplate(String strTemplate){
        if(strTemplate.contains("pre")) {
            strTemplate = strTemplate.substring(5, strTemplate.length());
            strTemplate = strTemplate.substring(0, strTemplate.length() - 6);
        }
        return strTemplate;
    }


    /**
     * 其他接口发短信方法
     * @param mobile
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    private boolean sendSMS(String mobile, String content) throws UnsupportedEncodingException {
        String url = "";
        String userName = "";
        String password = "";
        OthSms sb = new OthSms(url, userName, password);
        for (int i = 0; i < 3; i++) {
            int ret = sb.sendSms(mobile, content);
            if (ret > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 阿里大鱼发短信方法
     * @param templateCode
     * @param paramsJson
     * @param mobile
     * @return
     * @throws UnsupportedEncodingException
     */
    private boolean sendDayuSMS(String templateCode,String paramsJson,String mobile) throws UnsupportedEncodingException {
        boolean ret = daYuSms.sendSms(templateCode, paramsJson,mobile);
        if(ret){
            return true;
        }
        return false;
    }
}
