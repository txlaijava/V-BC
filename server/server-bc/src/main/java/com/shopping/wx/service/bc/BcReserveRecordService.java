package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcReserveRecord;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.wx.form.bc.BcReserveRecordAddForm;

import java.util.Date;
import java.util.List;

/**
 * @anthor bin
 * @data 2019/7/9 15:00
 * 类描述：预定就餐接口
 */
public interface BcReserveRecordService extends IBaseService<BcReserveRecord,Long>{
    /**
     * 预约就餐记录保存
     * @param  appid
     * @param id
     * @param bcReserveRecordAddForm
     * @return
     * @throws Exception
     */
    ActionResult bcReserveRecordSave(String appid,Long id,BcReserveRecordAddForm bcReserveRecordAddForm,int status)throws Exception;
    /**
     * 根据用户id和appid查询预约就餐记录
     * @param id
     * @param appid
     * @return
     * @throws Exception
     */
    List<BcReserveRecord> getBcReserveRecordList(Long id, String appid) throws Exception;

    /**
     * 根据选中的记录删除数据库的预约记录
     * @param appId
     * @param id
     * @param bcUserId
     * @return
     * @throws Exception
     */
    ActionResult deleteBcReserveRecordById(String appId,Long bcUserId,Long id)throws Exception;

    /**
     * 根据预约时间获取当天没有报餐的预约记录
     * @param appId
     * @param reserveRecordTime
     * @return
     * @throws Exception
     */
    List<BcReserveRecord> getBcReserveRecordListByReserveTime(String appId,String reserveRecordTime) throws  Exception;

    /**
     * 批量处理预约记录
     * @param appId
     * @return
     * @throws Exception
     */
    ActionResult bctchBcReserveRecordByReserveTime(String appId) throws  Exception;

    /**
     * 根据预约时间删除当天所有的预约记录
     * @param reserveTime
     * @return
     * @throws Exception
     */
    int deleteAllByReserveTime(String appId,Date reserveTime)throws  Exception;

    /**
     * 获取当前年份与之相匹配的预约记录
     * @param appId
     * @param curYearMonth
     * @return
     * @throws Exception
     */
    List<BcReserveRecord> getBcReserveRecordByYearAndMonth(String appId,Long bcUserId,String curYearMonth)throws Exception;
}
