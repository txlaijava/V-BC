package com.shopping.base.repository.bc;


import com.shopping.base.domain.bc.BcUserDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BcUserDepartmentRepository extends JpaRepository<BcUserDepartment,Long> {

    /**
     * 统计每个部门报餐人数
     * @param appId
     * @return
     */
    @Query("select  count(r.id) as count,d.name,d.id from BcUserDepartment d left join BcUser u on (d.id=u.userDepartmentId) left  join BcRecord r on(u.id =r.userId AND u.appId= r.appId and r.dinTime like concat(:dinTime,'%') ) where  d.appId=:appId   group by d.name")
    List<BcUserDepartment>  countDinnerByDay(@Param("appId")String appId,@Param("dinTime")String dinTime);

    /**
     * 根据appId  获取部门
     * @param appid
     * @return
     */
    List<BcUserDepartment> findByAppId(String  appid);

    /**
     * 根据appId和id获取部门
     * @param appId
     * @param id
     * @return
     */
    @Query("select obj from BcUserDepartment obj where obj.appId = :appId and obj.id=:id")
    BcUserDepartment getBcUserDepartment(@Param("appId")String appId,@Param("id")Long id);

    /**
     * 删除部门(根据id)
     * @param appId
     * @param id
     * @return
     */
    @Modifying
    @Query("delete  from BcUserDepartment  where appId=:appId and id=:id")
    int deleteById(@Param("appId")String appId,@Param("id")Long id);

    /**
     * 修改部门名称
     * @param name
     * @param appId
     * @param id
     * @return
     */
    @Modifying
    @Query("update BcUserDepartment set name=:name where appId=:appId and id=:id")
    int  updateNameById(@Param("name")String name,@Param("appId")String appId,@Param("id")Long id);
}
