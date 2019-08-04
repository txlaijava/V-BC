package com.shopping.wx.service.bc.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.domain.bc.BcUserDepartment;
import com.shopping.base.domain.bc.BcUserWx;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.foundation.view.BeanViewUtils;
import com.shopping.base.foundation.view.bc.BcUserInfoView;
import com.shopping.base.foundation.view.bc.BcUserWxView;
import com.shopping.base.repository.bc.BcUserWxRepository;
import com.shopping.wx.service.bc.BcUserDepartmentService;
import com.shopping.wx.service.bc.BcUserWxService;
import com.shopping.wx.token.authorization.manager.BcLoginUtils;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service(value = "bcUserWxServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BcUserWxServiceImpl  extends BaseServiceImpl<BcUserWx,Long> implements BcUserWxService {

    @Autowired
    BcUserWxRepository bcUserWxRepository;
    @Autowired
    BcLoginUtils bcLoginUtils;
    @Autowired
    BcUserDepartmentService bcUserDepartmentService;

    @Override
    public ActionResult findUserByOpenIdOrUnionid(String appid, WxMaJscode2SessionResult session, String loginType) throws Exception {
        Map wxMap = new HashMap(4);
        BcUserWx bcUserWx;
        if("unionid".equalsIgnoreCase(loginType)) {
            bcUserWx = this.bcUserWxRepository.getByAppidUnId(appid, session.getUnionid());
        }else{
            bcUserWx = this.bcUserWxRepository.getByAppidOpenId(appid, session.getOpenid());
        }
        // 如果并且关联用户了
        if(bcUserWx!=null && bcUserWx.getBcUser()!=null){
            BcUser bcUser = bcUserWx.getBcUser();
            BcUserInfoView userInfoView = BeanViewUtils.getView(bcUser,BcUserInfoView.class);
            BcUserWxView bcUserWxView = BeanViewUtils.getView(bcUserWx,BcUserWxView.class);
            // 获得Token
            String Token = bcLoginUtils.getToken(bcUser);
            //获取部门名称
            BcUserDepartment department = this.bcUserDepartmentService.getObjById(bcUser.getUserDepartmentId());
            wxMap.put("Token", Token);
            wxMap.put("openid",session.getOpenid());
            wxMap.put("unionid",session.getUnionid());
            wxMap.put("sessionKey", session.getSessionKey());
            wxMap.put("userWx",bcUserWxView);
            wxMap.put("user",userInfoView);
            wxMap.put("userDepartmentName",department.getName());
            return ActionResult.ok(wxMap);
        }else if(bcUserWx!=null){
            BcUserWxView bcUserWxView = BeanViewUtils.getView(bcUserWx,BcUserWxView.class);
            wxMap.put("sessionKey", session.getSessionKey());
            wxMap.put("userWx",bcUserWxView);
            wxMap.put("openid",session.getOpenid());
            return ActionResult.ok(wxMap);
        }else{
            wxMap.put("openid", session.getOpenid());
            wxMap.put("sessionKey", session.getSessionKey());
            return ActionResult.ok(wxMap);
        }
    }

    @Override
    public BcUserWx findByOpenid(String openid) throws Exception {
        return bcUserWxRepository.findByOpenid(openid);
    }

    @Override
    public ActionResult saveOrUpdate(String appId, WxMaUserInfo userInfo) throws Exception {
        //先判断是否存在该记录,存在则更新,不存在则保存
        BcUserWx bcUserWx = this.bcUserWxRepository.getByAppidOpenId(appId,userInfo.getOpenId());
        if(bcUserWx==null) {
            bcUserWx = new BcUserWx();
        }
        String nickName = EmojiParser.parseToHtmlDecimal(userInfo.getNickName());
        bcUserWx.setAppId(appId);
        bcUserWx.setOpenid(userInfo.getOpenId());
        bcUserWx.setUnionid(userInfo.getUnionId());
        bcUserWx.setAddTime(new Date());
        bcUserWx.setNickname(nickName);
        bcUserWx.setAvatarurl(userInfo.getAvatarUrl());
        bcUserWx.setCity(userInfo.getCity());
        bcUserWx.setProvince(userInfo.getProvince());
        bcUserWx.setGender(userInfo.getGender());
        bcUserWx.setLanguage(userInfo.getLanguage());
        bcUserWx.setCountry(userInfo.getCountry());
        bcUserWxRepository.saveAndFlush(bcUserWx);
        BcUserWxView bcUserWxView = BeanViewUtils.getView(bcUserWx,BcUserWxView.class);
        return ActionResult.ok(bcUserWxView);
    }
}
