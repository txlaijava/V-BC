package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import org.springframework.web.multipart.MultipartFile;


public interface BcConfigService extends IBaseService<BcConfig,Long> {


    /**
     * 通过AppId查找配置
     * @return
     */
   BcConfig getConfigByAppId(String appId)throws  Exception;

    /**
     * 保存或者更新配置
     * @param appId
     * @param config
     * @param imgUrlList
     * @return
     * @throws Exception
     */
   ActionResult saveOrUpdate(String appId,BcConfig config,String imgUrlList)throws  Exception;




}
