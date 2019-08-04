package com.shopping.wx.service.bc;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.shopping.base.domain.bc.BcUserWx;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;

public interface BcUserWxService  extends IBaseService<BcUserWx,Long> {

    /**
     * 通过openid查找并且登陆返回Token
     * @return
     * @throws Exception
     */
    ActionResult findUserByOpenIdOrUnionid(String appid, WxMaJscode2SessionResult session, String loginType) throws Exception;

    /**
     * 通过openid查找BcUserWx对象
     * @param openid
     * @return
     * @throws Exception
     */
    BcUserWx findByOpenid(String openid)throws Exception;

    /**
     *保存或者更新信息
     * @param appId
     * @param userInfo
     * @return
     * @throws Exception
     */
    ActionResult saveOrUpdate(String appId, WxMaUserInfo userInfo) throws Exception;
}


