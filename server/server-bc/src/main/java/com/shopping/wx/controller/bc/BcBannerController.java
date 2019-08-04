package com.shopping.wx.controller.bc;

import com.shopping.base.domain.bc.BcBanner;
import com.shopping.base.foundation.result.ActionResult;
import com.shopping.wx.service.bc.BcBannerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @anthor bin
 * @data 2019/7/19 14:16
 */
@Log4j2
@RestController
@RequestMapping("bc/{appid}/BcBanner")
public class BcBannerController {
    @Autowired
    BcBannerService bcBannerService;

    @ApiOperation(value = "查询广告图片列表",tags = {"BcBanner"},notes = "")
    @RequestMapping(value = "/bcBannerList",method = RequestMethod.GET)
    public ActionResult getBcBannerList(@PathVariable String appid){
        try {
            List<BcBanner> bcBannerList = bcBannerService.getBcBannerList(appid);
            return ActionResult.ok(bcBannerList);
        } catch (Exception e) {
            log.error("查询广告图片列表失败",e);
        }
        return ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "上传图片",tags = {"BcBanner"},notes = "")
    @PostMapping("/upload")
    public  ActionResult upload(HttpServletRequest request, @PathVariable("appid")String appid, @RequestParam("file") MultipartFile file){
        try{
            return  this.bcBannerService.upload(appid,file);
        }catch (Exception e){
            log.error("上传异常",e);
        }
        return  ActionResult.error("服务器异常");
    }

    @ApiOperation(value = "删除图片",tags = {"BcBanner"},notes = "")
    @DeleteMapping("/deleteImg")
    public ActionResult deleteImg(Long id,String imgUrl){
        try{
            return  this.bcBannerService.deleteImg(id,imgUrl);
        }catch (Exception e){
            log.error("删除图片异常",e);
        }
        return  ActionResult.ok("服务器异常");
    }
}
