package com.shopping.wx.service.bc.impl;

import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.domain.bc.BcRecord;
import com.shopping.base.domain.bc.BcReserveRecord;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.dao.bc.BcReserveRecordDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcRecordRepository;
import com.shopping.base.repository.bc.BcReserveRecordRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.constant.BcRecordCons;
import com.shopping.wx.form.bc.BcReserveRecordAddForm;
import com.shopping.wx.service.bc.BcConfigService;
import com.shopping.wx.service.bc.BcReserveRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @anthor bin
 * @data 2019/7/9 15:01
 * 类描述：预约报餐
 */
@Log4j2
@Service(value = "BcReserveRecord")
@Transactional(rollbackFor = Exception.class)
public class BcReserveRecordServiceImpl extends BaseServiceImpl<BcReserveRecord,Long> implements BcReserveRecordService{
    @Autowired
    BcReserveRecordDAO bcReserveRecordDAO;
    @Autowired
    BcReserveRecordRepository bcReserveRecordRepository;
    @Autowired
    BcRecordRepository bcRecordRepository;
    @Autowired
    BcConfigService bcConfigService;

    @Override
    public ActionResult bcReserveRecordSave(String appid,Long id,BcReserveRecordAddForm bcReserveRecordAddForm,int status) throws Exception {
        BcConfig config =bcConfigService.getConfigByAppId(appid);
        Boolean userNeedApprove =config.isUserNeedApprove();
        Boolean lunchCanMeal = config.isLunchCanMeal();
        //如果用户不需要审核就给予状态为1：已激活
        if(!userNeedApprove){
            //中午报餐状态开启
            if(lunchCanMeal){
                return this.save(appid,id,bcReserveRecordAddForm);
            }else{
                return ActionResult.error(2,"中午报餐功能未开启");
            }
        }else{
            if(lunchCanMeal){
                if(status == 1){
                    return this.save(appid,id,bcReserveRecordAddForm);
                }else{
                    return ActionResult.error(1,"需要联系管理员给予激活");
                }
            }else{
                return ActionResult.error(2,"中午报餐功能未开启");
            }
        }
    }
    public ActionResult save(String appid,Long id,BcReserveRecordAddForm bcReserveRecordAddForm){
        BcReserveRecord bcReserveRecord = new BcReserveRecord();
        bcReserveRecord.setAddTime(new Date());
        bcReserveRecord.setAppId(appid);
        bcReserveRecord.setBcUserId(id);
        bcReserveRecord.setReserveTime(CommUtils.formatDate(bcReserveRecordAddForm.getReserveTime(),"yyyy-MM-dd"));
        bcReserveRecord.setReserveTimeWeek(bcReserveRecordAddForm.getReserveTimeWeek());
        this.save(bcReserveRecord);
        return ActionResult.ok(bcReserveRecord);
    }
    @Override
    public List<BcReserveRecord> getBcReserveRecordList(Long id, String appid) throws Exception {
        return this.bcReserveRecordRepository.getBcReserveRecordList(id,appid);
    }

    @Override
    public ActionResult deleteBcReserveRecordById(String appId, Long bcUserId, Long id) throws Exception {
        Integer delResult = this.bcReserveRecordRepository.deleteBcReserveRecordByAppIdAndBcUserIdAndReserveTime(appId,bcUserId,id);
        return ActionResult.ok(delResult);
    }

    @Override
    public List<BcReserveRecord> getBcReserveRecordListByReserveTime(String appId, String reserveRecordTime) throws Exception {
        //String date = CommUtils.formatDate(reserveRecordTime,"yyyy-MM-dd");
        //return  this.bcReserveRecordRepository.getBcReserveRecordListByReserveTime(appId,CommUtils.formatDate(date,"yyyy-MM-dd"));
        return  this.bcReserveRecordRepository.getBcReserveRecordListByReserveTime(appId,reserveRecordTime);
    }



    @Override
    public ActionResult bctchBcReserveRecordByReserveTime(String appId) throws Exception {
        Date date = new Date();
        String reserveRecordTime = CommUtils.formatDate(date,"yyyy-MM-dd");
        //先获取没有报餐的预约记录
        List<BcReserveRecord> reserveRecordList =  this.getBcReserveRecordListByReserveTime(appId,reserveRecordTime);
        //新增报餐的集合
        List<BcRecord> addList= new ArrayList<>();
        for(int i=0;i<reserveRecordList.size();i++){
            BcReserveRecord reserveRecord= reserveRecordList.get(i);
            BcRecord tranForRecord = new BcRecord();
            tranForRecord.setAddTime(new Date());
            tranForRecord.setAppId(appId);
            tranForRecord.setBcChannel(BcRecordCons.BC_CHANNEL_ORDER);
            tranForRecord.setBcType(BcRecordCons.BC_TYPE_NOON);
            tranForRecord.setUserId(reserveRecord.getBcUserId());
            tranForRecord.setHadEat(BcRecordCons.HAD_EAT_CANNEL);
            Date reserveTime = reserveRecord.getReserveTime();
            String time =CommUtils.formatDate(new Date(),"HH:mm:ss");
            String prefix = CommUtils.formatDate(reserveTime,"yyyy-MM-dd");
            Date dinTime = CommUtils.formatDate((prefix+" "+time),"yyyy-MM-dd HH:mm:ss");
            tranForRecord.setDinTime(dinTime);
            addList.add(tranForRecord);
        }
        //删除预约记录
        this.deleteAllByReserveTime(appId,date);
        //批量增加记录
        this.bcRecordRepository.saveAll(addList);
        return ActionResult.ok("处理成功");
    }

    @Override
    public int deleteAllByReserveTime(String appId,Date reserveTime) throws Exception {
        String date = CommUtils.formatDate(reserveTime,"yyyy-MM-dd");
        return this.bcReserveRecordRepository.deleteAllByReserveTime(CommUtils.formatDate(date,"yyyy-MM-dd"),appId);
    }

    @Override
    public List<BcReserveRecord> getBcReserveRecordByYearAndMonth(String appId,Long bcUserId,String curYearMonth) throws Exception {
        return this.bcReserveRecordRepository.getBcReserveRecordByCurYearAndMonth(appId,bcUserId,curYearMonth);
    }

}
