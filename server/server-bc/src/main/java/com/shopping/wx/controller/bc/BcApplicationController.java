package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcApplication;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.wx.service.bc.BcApplicationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hx
 * @date 2019/7/19 18:05
 */
@Log4j2
@RestController
@RequestMapping("/bc/application")
public class BcApplicationController {

    @Autowired
    BcApplicationService bcApplicationService;

    @ApiOperation(value = "获取应用信息列表",tags = {"BcApplication"},notes = "")
    @GetMapping("/getApplicationList")
    public ActionResult  getApplication(){
        try{

            List<BcApplication> list = this.bcApplicationService.getApplicationList();
            return ActionResult.ok(list);
        }catch (Exception e){
            log.error("获取应用信息列表出错!",e);
        }
        return null;
    }

}
