package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcBanner;
import com.shopping.base.foundation.base.service.IBaseService;
import com.shopping.base.foundation.result.ActionResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * 广告图片接口
 *
 * @author bin
 * @date 2019/7/19
 */
public interface BcBannerService extends IBaseService<BcBanner,Long>{
    /**
     * 获取广告图片列表
     * @param appId
     * @return
     * @throws Exception
     */
    List<BcBanner> getBcBannerList(String appId)throws Exception;

    /**
     * 上传图片
     * @param appId
     * @param file
     * @return
     * @throws Exception
     */
    ActionResult upload(String appId, MultipartFile file)throws  Exception;

    /**
     * 删除图片(数据库及oss上的)
     * @param id
     * @param imgUrl
     * @return
     * @throws Exception
     */
    ActionResult deleteImg(Long id, String imgUrl) throws  Exception;
}
