package com.shopping.base.repository.bc;

import com.shopping.base.domain.bc.BcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author hx
 * @date 2019/7/18 14:59
 */
public interface BcUserRepository extends JpaRepository<BcUser,Long> {

    @Modifying
    @Query("update  BcUser set status=:status  where appId =:appId and id=:id")
    int updateStatusById(@Param("status")int status,@Param("appId")String appId,@Param("id")Long id);

    @Modifying
    @Query("update BcUser set userDepartmentId =null where appId = :appId and userDepartmentId=:userDepartmentId")
    int updateBcUserDepartmentId(@Param("appId")String appId,@Param("userDepartmentId")Long userDepartmentId);

    @Modifying
    @Query("update BcUser  set userDepartmentId =:userDepartmentId where appId =:appId and id=:id")
    int editBcUserDepartmentById(@Param("userDepartmentId")Long userDepartmentId,@Param("appId")String appId,@Param("id")Long id);
}
