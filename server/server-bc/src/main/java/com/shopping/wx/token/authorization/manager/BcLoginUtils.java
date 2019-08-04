package com.shopping.wx.token.authorization.manager;


import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.foundation.view.bc.BcUserCacheView;
import com.shopping.base.utils.BeanUtilEx;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.redis.service.RedisService;
import com.shopping.wx.constant.RedisCons;
import com.shopping.wx.service.bc.BcUserService;
import com.shopping.wx.token.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登陆工具类
 */

@Component
public class BcLoginUtils {

    @Autowired
    RedisService redisService;

    @Autowired
    BcUserService  bcUserService;

    public String getToken(BcUser bcUser) throws Exception{
        // 进行登录跳转
        String Token = JwtTokenUtils.createJWT(CommUtils.null2String(bcUser.getId()), bcUser.getMobile(), Constants.JWT_TTL);

        BcUserCacheView view = BeanViewUtils.getView(bcUser,BcUserCacheView.class);

        redisService.hmset(RedisCons.JWT_SESSION_WX_USER + CommUtils.null2String(bcUser.getId()), BeanUtilEx.transBean2Map(view), Constants.JWT_TTL);
        return Token;
    }
}
