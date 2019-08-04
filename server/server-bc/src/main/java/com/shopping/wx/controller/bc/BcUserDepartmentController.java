package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcUserDepartment;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcUserDepartmentRepository;
import com.shopping.base.repository.bc.BcUserRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.wx.service.bc.BcUserDepartmentService;
import com.shopping.wx.service.bc.BcUserService;
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
 * @data 2019/7/2 10:42
 */
@RestController
@RequestMapping("/bc/{appid}/BcUserDepartment")
@Log4j2
public class BcUserDepartmentController {
    @Autowired
    BcUserDepartmentService bcUserDepartmentService;
    @Autowired
    BcUserDepartmentRepository bcUserDepartmentRepository;
    @Autowired
    BcUserService bcUserService;
    @Autowired
    BcUserRepository bcUserRepository;
    /**
     * 查询部门信息
     * @return
     */
    @ApiOperation(value = "查询部门信息", tags = {"bcDepartment"}, notes = "")
    @GetMapping("/getBcUserDepartmentList")
    public ActionResult getBcUserDepartmentList(@PathVariable String appid) {
        try {
           List<BcUserDepartment>  departmentList= this.bcUserDepartmentService.getBcUserDepartmentList(appid);
            return ActionResult.ok(departmentList);
        } catch (Exception e) {
            log.error("查询部门信息异常",e);

        }
        return  ActionResult.error("服务器异常");
    }


    @ApiOperation(value = "统计每个部门的报餐人数", tags = {"bcDepartment"}, notes = "")
    @GetMapping("/countDinnerByDay")
    public ActionResult countByName(@PathVariable String appid,int curIndex){
        try{
            String dinTime= CommUtils.formatDate(CommUtils.getDateAfter(new Date(),curIndex),"yyyy-MM-dd");
            return this.bcUserDepartmentService.countDinnerByDay(appid,dinTime);
        }catch (Exception e){
            log.error("查询部门人数异常",e);
        }
        return ActionResult.error("服务器异常!");

    }

    @ApiOperation(value = "获取部门列表",tags = {"bcDepartment"}, notes = "")
    @GetMapping("/getDepartmentPageList")
    public  ActionResult getDepartmentPageList(@PathVariable String appid,int currentPage,int pageSize){
        try{
            Map<Object,Object>  map = new HashMap<>(2);
            List<Map<String,Object>> deptList=this.bcUserDepartmentService.getDepartmentPageList(appid,currentPage,pageSize);
            Long total = this.bcUserDepartmentRepository.count();
            map.put("data",deptList);
            map.put("total",total);
            return  ActionResult.ok(map);
        }catch(Exception e){
            log.error("获取部门列表异常",e);
        }
        return  ActionResult.error("服务器异常!");
    }

    @ApiOperation(value = "删除部门",tags = {"bcDepartment"},notes = "")
    @DeleteMapping("/deleteById/{id}")
    public ActionResult deleteById(@PathVariable String appid,@PathVariable Long id){
        try{
            this.bcUserService.updateBcUserDepartmentId(appid,id);
            int  delResult = this.bcUserDepartmentService.deleteById(appid,id);
            return  ActionResult.ok(delResult);
        }catch (Exception e){
            log.error("删除部门异常",e);
        }
        return  ActionResult.error("服务器异常!");
    }

    @ApiOperation(value ="添加部门",tags = {"bcDepartment"},notes = "")
    @PostMapping("/save")
    public ActionResult save(@PathVariable String appid,String name){
        try{
            BcUserDepartment department= new BcUserDepartment();
            department.setAppId(appid);
            department.setName(name);
            this.bcUserDepartmentService.save(department);
            return ActionResult.ok();
        }catch (Exception e){
            log.error("新增部门异常",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value ="编辑部门名称",tags = {"bcDepartment"},notes = "")
    @PostMapping("/updateName")
    public ActionResult updateName(@PathVariable String appid,Long id,String name){
        try{
            int result =this.bcUserDepartmentService.updateNameById(appid, id, name);
            return  ActionResult.ok(result);
        }catch (Exception e){
            log.error("编辑部门名称异常",e);
        }
        return ActionResult.error("服务器异常");
    }

}
