package com.shopping.wx.controller.bc;

import com.shopping.wx.service.bc.BcUserWxService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/bc/{appid}/userWx")
public class BcUserWxController  {
    @Autowired
    private BcUserWxService bcUserWxService;

}
