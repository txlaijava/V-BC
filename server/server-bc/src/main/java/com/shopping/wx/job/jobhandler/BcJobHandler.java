package com.shopping.wx.job.jobhandler;

import com.shopping.wx.service.bc.BcReserveRecordService;
import com.shopping.wx.service.bc.BcUserDepartmentService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述:
 * 商户结算,商户收益统计,商户提现归零
 * @author yj
 * @date 2019/2/21 17:48
 */
@JobHandler(value="BcJobHandler")
@Component
public class BcJobHandler extends IJobHandler {

    @Autowired
    private BcReserveRecordService bcReserveRecordService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        bcReserveRecordService.bctchBcReserveRecordByReserveTime(param);
         return SUCCESS;
    }
}
