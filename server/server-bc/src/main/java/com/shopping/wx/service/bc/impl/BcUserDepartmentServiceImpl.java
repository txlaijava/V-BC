package com.shopping.wx.service.bc.impl;

import com.shopping.base.domain.bc.BcUserDepartment;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.dao.bc.BcUserDepartmentDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcUserDepartmentRepository;
import com.shopping.wx.service.bc.BcUserDepartmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @anthor bin
 * @data 2019/7/2 10:25
 */
@Log4j2
@Service(value = "bcUserDepartment")
@Transactional(rollbackFor = Exception.class)
public class BcUserDepartmentServiceImpl extends BaseServiceImpl<BcUserDepartment,Long> implements BcUserDepartmentService{
    @Autowired
    BcUserDepartmentDAO bcUserDepartmentDAO;
    @Autowired
    BcUserDepartmentRepository bcUserDepartmentRepository;

    @Override
    public List<BcUserDepartment> getBcUserDepartmentList(String appId) throws  Exception {
        return  this.bcUserDepartmentRepository.findByAppId(appId);
    }

    @Override
    public BcUserDepartment getBcUserDepartment(String appId, Long id) {
        return this.bcUserDepartmentRepository.getBcUserDepartment(appId,id);
    }

    @Override
    public ActionResult countDinnerByDay(String appId,String dinTime) throws  Exception{
        List<BcUserDepartment>  list= this.bcUserDepartmentRepository.countDinnerByDay(appId,dinTime);
        return  ActionResult.ok(list);
    }

    @Override
    public List<Map<String,Object>> getDepartmentPageList(String appId, int currentPage, int pageSize) throws Exception {
        String sql="select d.name,count(u.name) as headcount,d.id from bc_user_department d  left join bc_user u on(u.app_id = d.app_id and u.user_department_id = d.id) where d.app_id =?0  group by d.name order by d.id asc";
        List<Map<String,Object>> list = this.bcUserDepartmentDAO.queryBySQLPage(sql,currentPage,pageSize,appId);
        return list;
    }

    @Override
    public int deleteById(String appId, Long id) throws Exception {
        int delResult = this.bcUserDepartmentRepository.deleteById(appId,id);
        return delResult;
    }

    @Override
    public int updateNameById(String appId, Long id, String name) throws Exception {
        return this.bcUserDepartmentRepository.updateNameById(name,appId,id);
    }


}
