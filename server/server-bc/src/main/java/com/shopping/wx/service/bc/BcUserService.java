package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.wx.form.bc.BcUserAddForm;
import com.shopping.wx.form.bc.BcUserQueryForm;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface BcUserService extends IBaseService<BcUser,Long> {
    /**
     * 报餐用户注册
     * @param bcUserAddForm
     * @return
     */
    ActionResult bcUserRegister(String appId,BcUserAddForm bcUserAddForm)throws Exception;


    /**
     * 获取用户信息列表(分页)
     * @param queryForm
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getUserPageList(BcUserQueryForm queryForm) throws  Exception;

    /**
     * 更改用户状态
     * @param appId
     * @param status
     * @param id
     * @return
     * @throws Exception
     */
    int updateStatusById(String appId,int status,Long id)throws  Exception;

    /**
     * 统计(带条件)
     * @param queryForm
     * @return
     * @throws Exception
     */
    BigInteger getTotalByFields(BcUserQueryForm queryForm)throws Exception;

    /**
     * 导出
     * @param queryForm
     * @throws Exception
     */
    void export(HttpServletResponse response,BcUserQueryForm queryForm) throws  Exception;

    /**
     * 获取用户列表
     * @param queryForm
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getUserList(BcUserQueryForm queryForm)throws Exception;

    /**
     * 更改用户的部门状态
     * @param appId
     * @param
     * @param userDepartmentId
     * @return
     * @throws Exception
     */
    int updateBcUserDepartmentId(String appId,Long userDepartmentId)throws Exception;

    /**
     * 修改部门
     * @param appId
     * @param userDepartmentId
     * @param id
     * @return
     * @throws Exception
     */
    int editDepartmentIdById(String appId,Long userDepartmentId,Long id) throws  Exception;
}
