package com.shopping.wx.service.bc.impl;

import com.alibaba.fastjson.JSONArray;
import com.aliyun.oss.OSSClient;
import com.shopping.base.domain.bc.BcBanner;
import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.constant.AliOssCons;
import com.shopping.base.foundation.dao.bc.BcConfigDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcBannerRepository;
import com.shopping.base.repository.bc.BcConfigRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.oss.AliOSSUtil;
import com.shopping.framework.oss.bean.AliOSSConfig;
import com.shopping.wx.service.bc.BcConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@Log4j2
@Service("bcConfigServiceImpl")
@Transactional
public class BcConfigServiceImpl extends BaseServiceImpl<BcConfig,Long> implements BcConfigService {

    @Autowired
    private BcConfigDAO  bcConfigDAO;
    @Autowired
    private BcConfigRepository bcConfigRepository;

    @Autowired
    private BcBannerRepository bcBannerRepository;

    @Override
    public BcConfig getConfigByAppId(String appId) throws Exception {
        BcConfig cfg= this.bcConfigRepository.findByAppId(appId);
        return  cfg;
    }

    @Override
    public ActionResult saveOrUpdate(String appId,BcConfig config,String imgUrlList) throws Exception {
        config.setAppId(appId);
        BcConfig cfg =this.bcConfigRepository.findByAppIdAndId(appId,config.getId());
        //保存
        if(cfg==null){
            this.save(config);
        }else{ //更新
            this.update(config);
        }
        //保存Banner
        if(CommUtils.isNotNull(imgUrlList)){
            JSONArray arr = JSONArray.parseArray(imgUrlList);
            List<BcBanner>  list = new ArrayList<>();
            for (int i=0;i<arr.size();i++){
                String imgUrl = arr.getString(i);
                BcBanner banner =new BcBanner();
                banner.setAppId(appId);
                banner.setImgUrl(imgUrl);
                banner.setAddTime(new Date());
                list.add(banner);
            }
            if(list!=null && list.size()>0){
                this.bcBannerRepository.saveAll(list);
            }
        }

        return ActionResult.ok();
    }





}
