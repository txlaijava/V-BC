package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcRecord;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.wx.form.bc.BcRecordQueryForm;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @anthor bin
 * @data 2019/7/5 15:19
 * 类描述：报餐接口
 */
public interface BcRecordService extends IBaseService<BcRecord,Long>{
    /**
     * 报餐记录保存
     * @return
     */
    ActionResult bcRecordMealSave(String appId,Long id,int status,Integer orderMeal)throws Exception;

    /**
     * 通过userid和时间查询报餐记录
     * @param id
     * @return
     * @throws Exception
     */
    ActionResult getByUserIdAndDinTime(String appId,Long id,String date)throws Exception;

    /**
     * 根据选中的日期判断选中的日期有没有报餐
     * @param appId
     * @param id
     * @param dinTime
     * @return
     * @throws Exception
     */
    ActionResult getBcRecordByMonth(String appId,Long id,String dinTime)throws Exception;
    /**
     * 获取某天的总报餐人数
     * @param appId
     * @param date
     * @return
     * @throws Exception
     */
    int getTotalRecordByDinTime(String appId,String date) throws  Exception;

    /**
     * 点击撤销报餐就会删除报餐记录
     * @param appId
     * @param userId
     * @param id
     * @return
     * @throws Exception
     */
    int deleteBcRecordById(String appId,Long userId,Long id)throws Exception;

    /**
     *根据就餐时间获取报餐列表(多表关联)
     * @param appId
     * @param date
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getBcRecordListByDinTime(String appId, String date,Long deptId,int currentPage,int pageSize) throws  Exception;


    /**
     * 获取报餐记录带条件(分页)
     * @param bcRecordQueryForm
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getBcRecordPageList( BcRecordQueryForm bcRecordQueryForm) throws  Exception;

    /**
     * 统计带条件
     * @param bcRecordQueryForm
     * @return
     * @throws Exception
     */
    List<Map<String,Object>>  countByFields(BcRecordQueryForm bcRecordQueryForm)throws  Exception;

    /**
     * 获取报餐记录
     * @param bcRecordQueryForm
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getBcRecordList(BcRecordQueryForm bcRecordQueryForm) throws  Exception;

    /**
     * 导出当前结果
     * @param response
     * @param bcRecordQueryForm
     * @throws Exception
     */
    void export(HttpServletResponse response,BcRecordQueryForm bcRecordQueryForm) throws  Exception;

    /**
     * 统计报餐列表(带条件)
     * @return
     * @throws Exception
     */
    List<Map<String,Object>>  countBcRecordPageList(BcRecordQueryForm bcRecordQueryForm)throws  Exception;

    /**
     * 获取统计列表总数
     * @param queryForm
     * @return
     * @throws Exception
     */
    BigInteger getTotalBcRecord(BcRecordQueryForm queryForm)throws Exception;

    /**
     * 导出统计报餐
     * @param response
     * @param queryForm
     * @throws Exception
     */
    void exportCount(HttpServletResponse response,BcRecordQueryForm queryForm)throws Exception;

    /**
     * 修改就餐状态
     * @param appiId
     * @param id
     * @return
     * @throws Exception
     */
    int  updateHadEatById(int hadEat,String appiId,Long id) throws  Exception;
}
