package com.shopping.wx.service.bc.impl;

import com.alibaba.fastjson.JSONObject;
import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.domain.bc.BcRecord;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.dao.bc.BcRecordDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcRecordRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.base.utils.DateUtils;
import com.shopping.base.utils.excel.ExportExcel;
import com.shopping.wx.constant.BcRecordCons;
import com.shopping.wx.form.bc.BcRecordQueryForm;
import com.shopping.wx.service.bc.BcConfigService;
import com.shopping.wx.service.bc.BcRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.*;

/**
 * @anthor bin
 * @data 2019/7/5 15:20
 * 类描述：报餐接口实现
 */
@Log4j2
@Service(value = "BcRecord")
@Transactional(rollbackFor = Exception.class)
public class BcRecordServiceImpl extends BaseServiceImpl<BcRecord,Long> implements BcRecordService{
    @Autowired
    BcRecordDAO bcRecordDAO;
    @Autowired
    BcRecordRepository bcRecordRepository;
    @Autowired
    BcConfigService bcConfigService;
    @Override
    public ActionResult bcRecordMealSave(String appId,Long id,int status,Integer orderMeal) throws Exception{
        BcConfig config =bcConfigService.getConfigByAppId(appId);
        Boolean userNeedApprove = config.isUserNeedApprove();
        Boolean lunchCanMeal = config.isLunchCanMeal();
        //不需要审核
        if(!userNeedApprove){
            //中午报餐状态开启
            if(lunchCanMeal){
                return this.save(appId,id,orderMeal);
            }else{
                return ActionResult.error(2,"中午报餐功能未开启");
            }
        }else{
            if(lunchCanMeal){
                if(status == 1){
                    return this.save(appId,id,orderMeal);
                }else{
                    return ActionResult.error(1,"需要联系管理员给予激活");
                }
            }else{
                return ActionResult.error(2,"中午报餐功能未开启");
            }
        }

    }


    /**
     * 报餐记录保存
     * @param appId
     * @param id
     * @return
     * @throws Exception
     */
    public ActionResult save(String appId,Long id,Integer orderMeal)throws Exception{
        BcRecord bcRecord = new BcRecord();
        bcRecord.setAddTime(new Date());
        bcRecord.setAppId(appId);
        bcRecord.setUserId(id);
        bcRecord.setBcType(BcRecordCons.BC_TYPE_NOON);
        bcRecord.setBcChannel(BcRecordCons.BC_CHANNEL_MANUAL);
        if(1 == orderMeal){
            bcRecord.setDinTime(DateUtils.nextNDate(new Date(),1));
        }else{
            bcRecord.setDinTime(new Date());
        }

        bcRecord.setHadEat(BcRecordCons.HAD_EAT_CANNEL);
        this.save(bcRecord);
        return ActionResult.ok(bcRecord);
    }

    @Override
    public ActionResult getByUserIdAndDinTime(String appId,Long id,String date) throws Exception {
        BcRecord bcRecord = bcRecordRepository.getByUserIdAndDinTime(appId,id,date);
        if (bcRecord != null){
            return  ActionResult.ok(bcRecord);
        }else{
            return  ActionResult.error(1,"不存在报餐记录");
        }
    }

    @Override
    public ActionResult getBcRecordByMonth(String appId, Long id, String dinTime) throws Exception {
        Map<String,Object> map = new HashMap<>(3);
        map.put("appId",appId);
        map.put("userId",id);
        map.put("dintime",dinTime);
        String sql ="select DATE_FORMAT(dintime,'%e') as day from bc_record where dintime like concat(:dintime,'%') and user_id =:userId and app_id =:appId";
        List<Map<String,Object>> list = this.bcRecordDAO.queryByNativeSQL(sql,map);
        return ActionResult.ok(list);
    }

    @Override
    public int getTotalRecordByDinTime(String appId, String date) throws Exception {
        return this.bcRecordRepository.getTotalRecordByDinTime(appId,date);
    }

    @Override
    public int deleteBcRecordById(String appId, Long userId, Long id) throws Exception {
        return this.bcRecordRepository.deleteBcRecordByAppIdAndUserIdAndId(appId,userId,id);
    }

    @Override
    public List<Map<String, Object>> getBcRecordListByDinTime(String appId, String date,Long deptId,int currentPage,int pageSize) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("dintime",date);
        map.put("appId",appId);
        String sql = "select DATE_FORMAT (r.addTime, '%m-%d %H:%i:%s') as addTime ," +
                "r.id,r.had_eat as hadEat,u.name ,u.mobile, d.name AS deptName ,w.avatarurl, " +
                "  case  when  r.bc_channel ='0' then '手动报餐'" +
                "  when r.bc_channel ='1' then '预约报餐'" +
                "  when r.bc_channel ='2' then '补报' end as channel "+
                "from  bc_record r  join bc_user u on (r.app_id = u.app_id and r.user_id = u.id)"+
                " join bc_user_department d on (d.app_id = u.app_id and d.id = u.user_department_id)"+
                " join bc_user_wx w on (w.app_id = u.app_id and w.bc_user_id = u.id)"+
                " where r.app_id =:appId and r.dintime like concat('%',:dintime, '%') ";
        if(CommUtils.isNotNull(deptId)){
            sql+="and d.id=:deptId";
            map.put("deptId",deptId);
        }
            sql+=" order by r.dintime asc";
        List<Map<String, Object>> list=this.bcRecordDAO.findBySqlPage(sql,currentPage,pageSize,map);
        return list;
    }

    @Override
    public List<Map<String, Object>> getBcRecordList(BcRecordQueryForm bcRecordQueryForm) throws  Exception{
        bcRecordQueryForm.setCurrentPage(0);
        bcRecordQueryForm.setPageSize(0);
        return this.getBcRecordPageList(bcRecordQueryForm);
    }

    @Override
    public void export(HttpServletResponse response,BcRecordQueryForm bcRecordQueryForm) throws Exception {
        //先获取查询结果
        List<Map<String,Object>> list = this.getBcRecordList(bcRecordQueryForm);
        List<Object> dataList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            Object object = JSONObject.parseObject(JSONObject.toJSONString(map));
            dataList.add(object);
        }
        String[] title =new String[]{"报餐时间","dinTime","员工姓名","name","手机号","mobile","部门名称","deptName"};
        int [] width = new int[]{50,50,50,50};
        ExportExcel.exportExcel(response,"报餐记录",title,dataList,width);
    }

    public List<Map<String, Object>> countBcRecordList(BcRecordQueryForm queryForm) throws Exception{
        queryForm.setCurrentPage(0);
        queryForm.setPageSize(0);
        return  this.countBcRecordPageList(queryForm);
    }

    @Override
    public List<Map<String, Object>> countBcRecordPageList(BcRecordQueryForm queryForm) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",queryForm.getAppId());
        String sql="select d.name as deptName,u.name,u.mobile,count(r.id) as num from bc_record r"+
                " join bc_user u on(r.app_id=u.app_id and r.user_id =u.id )" +
                " left join bc_user_department d on(u.app_id=d.app_id and u.user_department_id=d.id )" +
                " where r.app_id =:appId";
        if (CommUtils.isNotNull(queryForm.getDepartmentId())){
            sql+=" and d.id=:departmentId";
            map.put("departmentId",queryForm.getDepartmentId());
        }
        if(CommUtils.isNotNull(queryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",queryForm.getName());
        }
        if (CommUtils.isNotNull(queryForm.getMobile())){
            sql+=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",queryForm.getMobile());
        }
        if (CommUtils.isNotNull(queryForm.getStartTime())){
            sql+=" and r.dintime>=:startTime";
            map.put("startTime",queryForm.getStartTime());
        }
        if (CommUtils.isNotNull(queryForm.getEndTime())){
            sql+=" and r.dintime<=:endTime";
            map.put("endTime",queryForm.getEndTime());
        }
        sql+=" group by r.user_id";
        List<Map<String,Object>> list =new ArrayList<>();
        if(queryForm.getCurrentPage()==0 && queryForm.getPageSize() ==0){
            list = this.bcRecordDAO.queryByNativeSQL(sql,map);
        }else{
            list=this.bcRecordDAO.findBySqlPage(sql,queryForm.getCurrentPage(),queryForm.getPageSize(),map);
        }
        return list;
    }

    @Override
    public BigInteger getTotalBcRecord(BcRecordQueryForm queryForm) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",queryForm.getAppId());
        String sql="select count(distinct(r.user_id)) from bc_record r"+
                " join bc_user u on(r.app_id=u.app_id and r.user_id =u.id )" +
                " left join bc_user_department d on(u.app_id=d.app_id and u.user_department_id=d.id )" +
                " where r.app_id =:appId";
        if (CommUtils.isNotNull(queryForm.getDepartmentId())){
            sql+=" and d.id=:departmentId";
            map.put("departmentId",queryForm.getDepartmentId());
        }
        if(CommUtils.isNotNull(queryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",queryForm.getName());
        }
        if (CommUtils.isNotNull(queryForm.getMobile())){
            sql+=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",queryForm.getMobile());
        }
        if (CommUtils.isNotNull(queryForm.getStartTime())){
            sql+=" and r.dintime>=:startTime";
            map.put("startTime",queryForm.getStartTime());
        }
        if (CommUtils.isNotNull(queryForm.getEndTime())){
            sql+=" and r.dintime<=:endTime";
            map.put("endTime",queryForm.getEndTime());
        }
        List<Map<String,Object>> list = this.bcRecordDAO.queryByNativeSQL(sql,map);
        BigInteger total = BigInteger.valueOf(0);
        if(list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                Map<String,Object> obj = list.get(i);
                for (String key:obj.keySet()){
                    total =(BigInteger)obj.get(key);
                }
            }
        }
        return total;
    }

    @Override
    public void exportCount(HttpServletResponse response,BcRecordQueryForm queryForm) throws Exception {
        List<Map<String,Object>> list = this.countBcRecordList(queryForm);
        List<Object> dataList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            Object object = JSONObject.parseObject(JSONObject.toJSONString(map));
            dataList.add(object);
        }
        String[] title =new String[]{"所属部门","deptName","员工姓名","name","手机号","mobile","报餐次数","num"};
        int [] width = new int[]{50,50,50,50};
        ExportExcel.exportExcel(response,"报餐统计",title,dataList,width);
    }

    @Override
    public int updateHadEatById(int hadEat,String appiId, Long id) throws Exception {
        int result = this.bcRecordRepository.updateHadEatById(hadEat,id,appiId);
        return result;
    }


    @Override
    public List<Map<String, Object>> getBcRecordPageList(BcRecordQueryForm bcRecordQueryForm) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("appId",bcRecordQueryForm.getAppId());
        String sql = "select DATE_FORMAT (r.dintime, '%Y-%m-%d %H:%i:%s') as dinTime ," +
                "case when r.bc_type = '0' then '早餐' when r.bc_type = '1' then '中餐' when r.bc_type = '2' then '晚餐' end as bcType," +
                "case when r.bc_channel = '0' then '手动报餐' when r.bc_channel = '1' then '预约报餐' when r.bc_channel = '2' then '补报' end as bcChannel," +
                "u.name ,u.mobile, d.name AS deptName from  bc_record r " +
                "join bc_user u on (r.app_id = u.app_id and r.user_id = u.id)"+
                "left join bc_user_department d on (d.app_id = u.app_id and d.id = u.user_department_id)"+
                " where r.app_id =:appId";
        if(CommUtils.isNotNull(bcRecordQueryForm.getDepartmentId())){
            sql +=" and d.id=:deptId";
            map.put("deptId",bcRecordQueryForm.getDepartmentId());
        }
        if (CommUtils.isNotNull(bcRecordQueryForm.getMobile())) {
            sql +=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",bcRecordQueryForm.getMobile());
        }
        if(CommUtils.isNotNull(bcRecordQueryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",bcRecordQueryForm.getName());
        }
        if(CommUtils.isNotNull(bcRecordQueryForm.getStartTime()) && CommUtils.isNotNull(bcRecordQueryForm.getEndTime())){
            sql+=" and r.dintime >= :startTime and r.dintime <= :endTime";
            map.put("startTime",bcRecordQueryForm.getStartTime());
            map.put("endTime",bcRecordQueryForm.getEndTime());
        }
        sql+=" order by r.dintime DESC";
        List<Map<String,Object>> list = new ArrayList<>();
        if(bcRecordQueryForm.getCurrentPage() ==0 && bcRecordQueryForm.getPageSize() ==0 ){
            list = this.bcRecordDAO.queryByNativeSQL(sql,map);
        }else{
            list = this.bcRecordDAO.findBySqlPage(sql,bcRecordQueryForm.getCurrentPage(),bcRecordQueryForm.getPageSize(),map);
        }
        return list;
    }

    @Override
    public List<Map<String,Object>> countByFields(BcRecordQueryForm bcRecordQueryForm) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",bcRecordQueryForm.getAppId());
        String sql = "select count(*) as count from  bc_record r " +
                "join bc_user u on (r.app_id = u.app_id and r.user_id = u.id) "+
                "left join bc_user_department d on (d.app_id = u.app_id AND d.id = u.user_department_id)"+
                " where r.app_id =:appId";
        if(CommUtils.isNotNull(bcRecordQueryForm.getDepartmentId())){
            sql +=" and d.id=:deptId";
            map.put("deptId",bcRecordQueryForm.getDepartmentId());
        }
        if (CommUtils.isNotNull(bcRecordQueryForm.getMobile())) {
            sql +=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",bcRecordQueryForm.getMobile());
        }
        if(CommUtils.isNotNull(bcRecordQueryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",bcRecordQueryForm.getName());
        }
        if(CommUtils.isNotNull(bcRecordQueryForm.getStartTime()) && CommUtils.isNotNull(bcRecordQueryForm.getEndTime())){
            sql+=" and r.dintime >= :startTime and r.dintime <= :endTime";
            map.put("startTime",bcRecordQueryForm.getStartTime());
            map.put("endTime",bcRecordQueryForm.getEndTime());
        }
        List<Map<String,Object>> total = this.bcRecordDAO.queryByNativeSQL(sql,map);
        return  total;
    }

}
