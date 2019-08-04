package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcUserDepartment;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import java.util.List;
import java.util.Map;


/**
 * @anthor bin
 * @data 2019/7/2 10:25
 */
public interface BcUserDepartmentService extends IBaseService<BcUserDepartment,Long>{
    /**
     * 通过appId 获取部门列表
     * @param appId
     * @return
     * @throws Exception
     */
    List<BcUserDepartment> getBcUserDepartmentList(String appId) throws  Exception;

    /**
     *通过appId和id获取部门
     * @param appId
     * @param id
     * @return
     */
    BcUserDepartment getBcUserDepartment(String appId,Long id);

    /**
     * 获取部门人数
     * @return
     */
    ActionResult  countDinnerByDay(String appId,String dinTime) throws  Exception;


    /**
     * 获取部门列表
     */
    List<Map<String,Object>>  getDepartmentPageList(String appId, int currentPage, int pageSize) throws Exception;

    /**
     * 删除部门（根据id）
     * @param appId
     * @param id
     * @return
     * @throws Exception
     */
    int  deleteById(String appId,Long id)throws  Exception;

    int updateNameById(String appId,Long id,String name)throws Exception;
}
