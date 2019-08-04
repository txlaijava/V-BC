package com.shopping.wx.service.bc.impl;

import com.aliyun.oss.OSSClient;
import com.shopping.base.domain.bc.BcBanner;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.constant.AliOssCons;
import com.shopping.base.foundation.dao.bc.BcBannerDAO;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.base.repository.bc.BcBannerRepository;
import com.shopping.base.utils.CommUtils;
import com.shopping.framework.oss.AliOSSUtil;
import com.shopping.framework.oss.bean.AliOSSConfig;
import com.shopping.wx.service.bc.BcBannerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @anthor bin
 * @data 2019/7/19 13:50
 */
@Log4j2
@Service(value = "BcBanner")
@Transactional(rollbackFor = Exception.class)
public class BcBannerServiceImpl extends BaseServiceImpl<BcBanner,Long> implements BcBannerService{
    @Autowired
    BcBannerDAO bcBannerDAO;
    @Autowired
    BcBannerRepository bcBannerRepository;
    @Resource
    private AliOSSConfig aliOSSConfig;

    @Override
    public List<BcBanner> getBcBannerList(String appId) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("appId",appId);
        String sql ="select id,img_url as imgUrl,img_link as imgLink from bc_banner  where app_id =:appId";
        List<BcBanner>  list = this.queryByNativeSQL(sql,map);
        return  list;
    }

    @Override
    public ActionResult upload(String appId, MultipartFile file) throws Exception {
        File toFile = null;
        if(!file.isEmpty()){
            InputStream ins =  new ByteArrayInputStream(file.getBytes());
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            if(toFile.length()>0){
                String oldFileName = toFile.getName();
                String suffix =oldFileName.substring(oldFileName.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString()+suffix;
                String folder = "upload/banner/"+appId+"/";
                OSSClient ossClient = new OSSClient(aliOSSConfig.getEndPoint(),aliOSSConfig.getAccessKey(),aliOSSConfig.getSecretKey());
                AliOSSUtil.uploadByFile(ossClient,toFile, AliOssCons.bucket_rblc,folder+fileName);
                String path = aliOSSConfig.getHttpPath()+folder+fileName;
                return ActionResult.ok(path);
            }
        }else{
            return ActionResult.error("文件为空!");
        }
        return null;
    }

    /**
     * inputStream转File
     * @param ins
     * @param file
     */
    public  void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ActionResult deleteImg(Long id, String imgUrl) throws Exception {
        if(CommUtils.isNotNull(id)){
            this.bcBannerDAO.deleteById(id);
        }
        if(CommUtils.isNotNull(imgUrl)){
            String key = imgUrl.substring(aliOSSConfig.getHttpPath().length());
            OSSClient ossClient = new OSSClient(aliOSSConfig.getEndPoint(),aliOSSConfig.getAccessKey(),aliOSSConfig.getSecretKey());
            AliOSSUtil.deleteFile(ossClient,aliOSSConfig.getBucket(),key);
        }
        return ActionResult.ok();
    }

}
