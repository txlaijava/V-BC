package com.shopping.wx.service.bc.impl;

import com.alibaba.fastjson.JSONObject;
import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.domain.bc.BcUserWx;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.dao.bc.BcUserDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.foundation.view.bc.BcUserInfoView;
import com.shopping.base.repository.bc.BcUserRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.constant.BcUserCons;
import com.shopping.base.utils.excel.ExportExcel;
import com.shopping.wx.form.bc.BcUserAddForm;
import com.shopping.wx.form.bc.BcUserQueryForm;
import com.shopping.wx.service.bc.BcConfigService;
import com.shopping.wx.service.bc.BcUserService;
import com.shopping.wx.service.bc.BcUserWxService;
import com.shopping.wx.token.authorization.manager.BcLoginUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.*;

@Log4j2
@Service(value="bcUserServiceImpl")
@Transactional
public class BcUserServiceImpl  extends BaseServiceImpl<BcUser,Long> implements  BcUserService {

    @Autowired
    BcUserDAO bcUserDAO;
    @Autowired
    BcUserWxService bcUserWxService;
    @Autowired
    BcConfigService bcConfigService;
    @Autowired
    BcLoginUtils bcLoginUtils;
    @Autowired
    BcUserRepository bcUserRepository;

    @Override
    public ActionResult bcUserRegister(String appId,BcUserAddForm bcUserAddForm)throws Exception {
        Map map = new HashMap(2);
        BcUser bcUser = new BcUser();
        BcConfig config =bcConfigService.getConfigByAppId(appId);
        Boolean userNeedApprove =config.isUserNeedApprove();
        //如果用户不需要审核就给予状态为1：已激活
        if(!userNeedApprove ){
            bcUser.setStatus(BcUserCons.STATUS_NOACTIVE);
        }else{
            bcUser.setStatus(BcUserCons.STATUS_ACTIVE);
        }
        bcUser.setAddTime(new Date());
        bcUser.setAppId(appId);
        bcUser.setName(bcUserAddForm.getName());
        bcUser.setMobile(bcUserAddForm.getMobile());
        bcUser.setUserDepartmentId(CommUtils.null2Long(bcUserAddForm.getUserDepartmentId()));
        this.save(bcUser);
        BcUserWx bcUserWx = bcUserWxService.findByOpenid(bcUserAddForm.getOpenid());
        if(bcUserWx!=null){
            bcUserWx.setBcUser(bcUser);
            bcUserWxService.update(bcUserWx);
            //获取Token
            String Token = bcLoginUtils.getToken(bcUser);
            BcUserInfoView bcUserInfoView = BeanViewUtils.getView(bcUser,BcUserInfoView.class);
            map.put("Token",Token);
            map.put("BcUser",bcUserInfoView);
            return ActionResult.ok(map);
        }
        return ActionResult.ok(bcUser.toString());
    }

    @Override
    public List<Map<String, Object>> getUserPageList(BcUserQueryForm queryForm) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",queryForm.getAppId());
        String sql ="select  u.id,u.name,u.mobile,d.name as department,u.status,u.user_department_id from bc_user u"+
                " left join bc_user_department d on(u.app_id= d.app_id and u.user_department_id =d.id)"+
                " where u.app_id =:appId";
        if(CommUtils.isNotNull(queryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",queryForm.getName());
        }
        if(CommUtils.isNotNull(queryForm.getDepartmentId())){
            sql+=" and d.id=:departmentId";
            map.put("departmentId",queryForm.getDepartmentId());
        }
        if(CommUtils.isNotNull(queryForm.getMobile())){
            sql+=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",queryForm.getMobile());
        }
        sql+=" order by u.id asc";
        List<Map<String,Object>> list  = new ArrayList<>();
        if (queryForm.getCurrentPage()==0 && queryForm.getPageSize()==0){
            list = this.bcUserDAO.queryByNativeSQL(sql,map);
        }else{
            list = this.bcUserDAO.findBySqlPage(sql,queryForm.getCurrentPage(),queryForm.getPageSize(),map);
        }
        return list;
    }

    @Override
    public int updateStatusById(String appId, int status, Long id) throws Exception {
        return this.bcUserRepository.updateStatusById(status,appId,id);
    }

    @Override
    public BigInteger getTotalByFields(BcUserQueryForm queryForm) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",queryForm.getAppId());
        String sql ="select  count(u.id) as count from bc_user u"+
                " left join bc_user_department d on(u.app_id= d.app_id and u.user_department_id =d.id)"+
                " where u.app_id =:appId";
        if (CommUtils.isNotNull(queryForm.getName())){
            sql+=" and u.name like concat('%',:name,'%')";
            map.put("name",queryForm.getName());
        }
        if(CommUtils.isNotNull(queryForm.getDepartmentId())){
            sql+=" and d.id=:departmentId";
            map.put("departmentId",queryForm.getDepartmentId());
        }
        if(CommUtils.isNotNull(queryForm.getMobile())){
            sql+=" and u.mobile like concat('%',:mobile,'%')";
            map.put("mobile",queryForm.getMobile());
        }
        List<Map<String,Object>> list = this.bcUserDAO.queryByNativeSQL(sql,map);
        BigInteger total = BigInteger.valueOf(0);
        if (list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                Map<String,Object> obj = list.get(i);
                for (String key:obj.keySet()){
                    Object value = obj.get(key);
                    total =(BigInteger) value;
                }
            }
        }
        return total;
    }

    @Override
    public void export(HttpServletResponse response,BcUserQueryForm queryForm) throws Exception {
        List<Map<String,Object>> list = this.getUserList(queryForm);
        List<Object> dataList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            Object object = JSONObject.parseObject(JSONObject.toJSONString(map));
            dataList.add(object);
        }
        String[] title =new String[]{"员工姓名","name","手机号","mobile","所属部门","department"};
        int [] width = new int[]{50,50,50,50};
        ExportExcel.exportExcel(response,"员工列表",title,dataList,width);
    }

    @Override
    public List<Map<String, Object>> getUserList(BcUserQueryForm queryForm) throws Exception {
        queryForm.setCurrentPage(0);
        queryForm.setPageSize(0);
        return this.getUserPageList(queryForm);
    }

    @Override
    public int updateBcUserDepartmentId(String appId,Long userDepartmentId) throws Exception {
        return this.bcUserRepository.updateBcUserDepartmentId(appId,userDepartmentId);
    }

    @Override
    public int editDepartmentIdById(String appId, Long userDepartmentId,Long id) throws Exception {
        return this.bcUserRepository.editBcUserDepartmentById(userDepartmentId,appId,id);
    }

}
