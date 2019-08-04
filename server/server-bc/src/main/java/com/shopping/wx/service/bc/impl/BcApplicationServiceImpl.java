package com.shopping.wx.service.bc.impl;

import com.shopping.base.domain.bc.BcApplication;
import com.shopping.base.foundation.base.service.impl.BaseServiceImpl;
import com.shopping.base.foundation.dao.bc.BcAppliactionDAO;
import com.shopping.wx.service.bc.BcApplicationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hx
 * @date 2019/7/19 17:21
 */
@Log4j2
@Service("bcApplicationService")
public class BcApplicationServiceImpl  extends BaseServiceImpl<BcApplication,Long> implements BcApplicationService {

    @Autowired
    private BcAppliactionDAO bcAppliactionDAO;


    @Override
    public List<BcApplication> getApplicationList() throws Exception {
        List<BcApplication> list =this.bcAppliactionDAO.findAll();
        return list;
    }
}
