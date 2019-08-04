package com.shopping.base.repository.bc;

import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.domain.bc.BcUserWx;
import com.shopping.base.domain.user.User;
import com.shopping.base.domain.user.UserWx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BcUserWxRepository  extends JpaRepository<BcUserWx,Long> {
    BcUserWx findByOpenid(String openid);

    @Query("select obj from BcUserWx obj where obj.appId=:appId and obj.bcUser=:bcUser")
    BcUserWx getByAppidUser(@Param("appId") String appId, @Param("bcUser") BcUser user);

    @Query("select obj from BcUserWx obj where obj.appId=:appId and obj.openid=:openid")
    BcUserWx getByAppidOpenId(@Param("appId") String appId,@Param("openid") String openid);

    @Query("select obj from BcUserWx obj where obj.appId=:appId and obj.unionid=:unionid")
    BcUserWx getByAppidUnId(@Param("appId") String appId,@Param("unionid") String unionid);
}
