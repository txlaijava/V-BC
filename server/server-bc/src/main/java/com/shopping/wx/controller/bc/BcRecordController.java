package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.constant.BcRecordCons;
import com.shopping.wx.form.bc.BcRecordQueryForm;
import com.shopping.wx.service.bc.BcRecordService;
import com.shopping.wx.token.authorization.annotation.CurrentBcUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @anthor bin
 * @data 2019/7/5 15:57
 * 类描述：报餐
 */
@Log4j2
@RestController
@RequestMapping("bc/{appid}/BcRecord")
public class BcRecordController {
    @Autowired
    BcRecordService bcRecordService;

    @ApiOperation(value = "报餐信息保存", tags = {"BcRecord"}, notes = "")
    @RequestMapping(value = "/BcRecordMealSave",method = RequestMethod.GET)
    public ActionResult bcRecordMealSave(@PathVariable String appid, @CurrentBcUser BcUser bcUser,Integer orderMeal){
        try {
            return bcRecordService.bcRecordMealSave(appid,bcUser.getId(),bcUser.getStatus(),orderMeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "通过用户Id和报餐时间记录查询当天有没有就餐", tags = {"BcRecord"}, notes = "")
    @GetMapping(value = "/BcRecordByUserIdAndDinTime")
    public ActionResult bcRecordByUserIdAndDinTime(@PathVariable String appid, @CurrentBcUser BcUser bcUser,Integer orderMeal){
        try {
            String date = "";
            if(1 == orderMeal){
                date = CommUtils.formatDate(CommUtils.getDateAfter(new Date(),1),"yyyy-MM-dd");
            }else{
                date = CommUtils.formatDate(new Date(),"yyyy-MM-dd");
            }


            if(bcUser.getId()!=null){
                return bcRecordService.getByUserIdAndDinTime(appid,bcUser.getId(),date);
            }
        }catch (Exception e){
            log.error("查询信息异常",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "根据月份查询该月的所有报餐记录", tags = {"BcRecord"}, notes = "")
    @GetMapping(value = "/bcBcRecordByMonth")
    public ActionResult bcBcRecordByMonth(@PathVariable String appid, @CurrentBcUser BcUser bcUser,String dinTime){
        try {
            return bcRecordService.getBcRecordByMonth(appid,bcUser.getId(),dinTime);
        }catch (Exception e){
            log.error("查询信息异常");
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "查询报餐总人数",tags={"BcRecord"},notes = "")
    @GetMapping(value = "/getTotalRecordByDinTime")
    public ActionResult getTotalRecord(@PathVariable String appid,int curIndex){
        try{
            String date = CommUtils.formatDate(CommUtils.getDateAfter(new Date(),curIndex),"yyyy-MM-dd");
            int  totalNum = this.bcRecordService.getTotalRecordByDinTime(appid,date);
            return  ActionResult.ok(totalNum);
        }catch (Exception e){
            log.error("获取报餐总人数异常!",e);
        }
        return  ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "根据id删除报餐记录",tags={"BcRecord"},notes = "")
    @GetMapping(value = "/deleteBcRecordById")
    public ActionResult deleteBcRecordById(@PathVariable String appid,@CurrentBcUser BcUser bcUser,Long id){
        try {
            int result = bcRecordService.deleteBcRecordById(appid,bcUser.getId(),id);
            return ActionResult.ok(result);
        } catch (Exception e) {
            log.error("删除预约记录失败",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value ="获取报餐信息列表",tags = {"BcRecord"},notes = "")
    @GetMapping(value = "/getBcRecordListByDinTime")
    public  ActionResult  getBcRecordListByDinTime(@PathVariable String  appid,Long deptId,int curIndex,int currentPage,int pageSize){
        try{
            String date = CommUtils.formatDate(CommUtils.getDateAfter(new Date(),curIndex),"yyyy-MM-dd");
            List<Map<String, Object>> bcRecordList = this.bcRecordService.getBcRecordListByDinTime(appid,date,deptId,currentPage,pageSize);
            return ActionResult.ok(bcRecordList);
        }catch (Exception e){
            log.error("获取报餐信息列表出错",e);
        }
        return  ActionResult.error("服务器异常");
    }

    @ApiOperation(value ="有条件查询报餐信息列表",tags = {"BcRecord"},notes = "")
    @GetMapping(value = "/getBcRecordList")
    public ActionResult getBcRecordList(@PathVariable String appid, BcRecordQueryForm bcRecordQueryForm){
        try {
            Map<Object,Object> map = new HashMap<>(2);
            bcRecordQueryForm.setAppId(appid);
            List<Map<String,Object>> list = this.bcRecordService.getBcRecordPageList(bcRecordQueryForm);
            List<Map<String,Object>> total = this.bcRecordService.countByFields(bcRecordQueryForm);
            map.put("data",list);
            map.put("total",total);
            return ActionResult.ok(map);
        } catch (Exception e) {
            log.error("查询报餐信息异常");
        }
        return ActionResult.error("服务器异常");
    }


    @ApiOperation(value ="导出当前结果",tags = {"BcRecord"},notes = "")
    @PostMapping(value = "/export")
    public void  export(HttpServletResponse response, @PathVariable String appid,BcRecordQueryForm bcRecordQueryForm){
        try{
             bcRecordQueryForm.setAppId(appid);
             bcRecordService.export(response,bcRecordQueryForm);
        }catch (Exception e){
            log.error("导出当前结果异常",e);
        }
    }

    @ApiOperation(value ="报餐统计",tags = {"BcRecord"},notes = "")
    @GetMapping(value = "countBcRecordPageList")
    public ActionResult countBcRecordPageList(@PathVariable String appid,BcRecordQueryForm bcRecordQueryForm){
        try{
            Map<String,Object> map = new HashMap<>(2);
            bcRecordQueryForm.setAppId(appid);
            List<Map<String,Object>> list = this.bcRecordService.countBcRecordPageList(bcRecordQueryForm);
            BigInteger total = this.bcRecordService.getTotalBcRecord(bcRecordQueryForm);
            map.put("data",list);
            map.put("total",total);
            return ActionResult.ok(map);
        }catch (Exception e){
            log.error("获取报餐统计列表异常",e);
        }
        return ActionResult.error("服务器异常!");
    }

    @ApiOperation(value ="导出报餐统计",tags = {"BcRecord"},notes = "")
    @PostMapping("/exportCount")
    public ActionResult exportCount(HttpServletResponse response,@PathVariable String appid,BcRecordQueryForm queryForm){
        try{
            queryForm.setAppId(appid);
            this.bcRecordService.exportCount(response,queryForm);
            return  ActionResult.ok();
        }catch (Exception e){
            log.error("导出报餐统计",e);
        }
        return  ActionResult.error("服务器异常");
    }


    @ApiOperation(value ="确认报餐",tags = {"BcRecord"},notes = "")
    @GetMapping("/confirmEat")
    public  ActionResult confirmEat(@PathVariable String appid,Long id){
        try{
            int result = this.bcRecordService.updateHadEatById(BcRecordCons.HAD_EAT_CONFIRM,appid,id);
            return ActionResult.ok(result);
        }catch (Exception e){
            log.error("确认报餐异常",e);
        }
        return ActionResult.error();
    }
}
