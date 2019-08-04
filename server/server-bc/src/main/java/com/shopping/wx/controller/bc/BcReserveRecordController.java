package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcReserveRecord;
import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.form.bc.BcReserveRecordAddForm;
import com.shopping.wx.service.bc.BcReserveRecordService;
import com.shopping.wx.token.authorization.annotation.CurrentBcUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @anthor bin
 * @data 2019/7/9 15:41
 */
@Log4j2
@RestController
@RequestMapping("bc/{appid}/bcReserveRecord")
public class BcReserveRecordController {
    @Autowired
    BcReserveRecordService bcReserveRecordService;

    @ApiOperation(value = "预约就餐信息保存",tags = {"BcReserveRecord"},notes = "")
    @RequestMapping(value = "/bcReserveRecordSave",method = RequestMethod.GET)
    public ActionResult bcReserveRecordSave(@PathVariable String appid, @CurrentBcUser
            BcUser bcUser,BcReserveRecordAddForm bcReserveRecordAddForm){
        try {
            return bcReserveRecordService.bcReserveRecordSave(appid,bcUser.getId(),bcReserveRecordAddForm,bcUser.getStatus());
        }catch (Exception e){
            log.error("预约就餐信息保存失败",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "根据用户id和appid查询预约就餐记录",tags = {"BcReserveRecord"},notes = "")
    @RequestMapping(value = "/bcReserveRecordList",method = RequestMethod.GET)
    public ActionResult getBcReserveRecordList(@PathVariable String appid,@CurrentBcUser BcUser bcUser){
        try {

            Map<String,Object> params= new HashMap<>();
            params.put("bcUserId",bcUser.getId());
            params.put("appId",appid);
            List<BcReserveRecord> bcReserveRecordList = this.bcReserveRecordService.getBcReserveRecordList(bcUser.getId(),appid);
            return ActionResult.ok(bcReserveRecordList);
        } catch (Exception e) {
            log.error("查询预约记录失败",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "根据选中的记录删除数据库的预约记录",tags = {"BcReserveRecord"},notes = "")
    @RequestMapping(value = "/deleteBcReserveRecord",method = RequestMethod.GET)
    public ActionResult deleteBcReserveRecordByReserveTime(@PathVariable String appid,@CurrentBcUser BcUser bcUser,Long id){
        try {
           return  bcReserveRecordService.deleteBcReserveRecordById(appid,bcUser.getId(),id);
        }catch (Exception e){
            log.error("删除数据库的预约记录失败",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "根据预约时间获取预约记录",tags = {"BcReserveRecord"},notes = "")
    @GetMapping("/getBcReserveRecordListByReserveTime")
    public ActionResult getBcReserveRecordListByReserveTime(@PathVariable String appid){
        try{
            Date date = new Date();
            String reserveRecordTime = CommUtils.formatDate(date,"yyyy-MM-dd");
            List<BcReserveRecord>  bcReserveRecordList= bcReserveRecordService.getBcReserveRecordListByReserveTime(appid,reserveRecordTime);
            return  ActionResult.ok(bcReserveRecordList);
        }catch (Exception e){
            log.error("获取预约记录异常",e);
        }
        return  ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "批量处理预约记录",tags = {"BcReserveRecord"},notes = "")
    @GetMapping("/bctch")
    public  ActionResult bctchBcReserveRecordByReserveTime(@PathVariable String appid){
        try{
            return this.bcReserveRecordService.bctchBcReserveRecordByReserveTime(appid);
        }catch (Exception e){
            log.error("批量处理异常",e);
        }
        return  ActionResult.error("服务器异常!");
    }

    @ApiOperation(value = "根据年月获取预约记录",tags = {"BcReserveRecord"},notes = "")
    @GetMapping("/getBcReserveRecordListByCurYearAndCurMonth")
    public ActionResult getBcReserveRecordListByCurYearAndCurMonth(@PathVariable String appid,@CurrentBcUser BcUser bcUser,String curYearMonth){
        try {
            List<BcReserveRecord> bcReserveRecordList = this.bcReserveRecordService.getBcReserveRecordByYearAndMonth(appid,bcUser.getId(),curYearMonth);
            return ActionResult.ok(bcReserveRecordList);
        }catch (Exception e){
            log.error("获取预约记录异常",e);
        }
        return ActionResult.error("服务器异常");
    }
}
