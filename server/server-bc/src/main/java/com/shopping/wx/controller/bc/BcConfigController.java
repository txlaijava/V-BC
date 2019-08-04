package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.foundation.view.bc.BcConfigView;
import com.shopping.wx.service.bc.BcConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/bc/{appid}/config")
public class BcConfigController  {

    @Autowired
    private BcConfigService bcConfigService;


    /**
     *获取配置
     * @return
     */
    @GetMapping("/getConfig")
    public ActionResult getConfig(@PathVariable String appid){
        try{
            BcConfig cfg= this.bcConfigService.getConfigByAppId(appid);
            BcConfigView bcConfigView = BeanViewUtils.getView(cfg,BcConfigView.class);
            return  ActionResult.ok(bcConfigView);
        }catch (Exception e){
            log.error("获取截止时间异常",e);
        }
        return ActionResult.error("服务器异常");
    }

    /**
     * 保存或者更新配置
     * @param appid
     * @param config
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public ActionResult saveOrUpdate(@PathVariable String appid,BcConfig config,String imgUrlList){
        try{
            return this.bcConfigService.saveOrUpdate(appid,config,imgUrlList);
        }catch (Exception e){
            log.error("保存或者更新配置失败!",e);
        }
        return ActionResult.error("服务器异常");
    }


}
