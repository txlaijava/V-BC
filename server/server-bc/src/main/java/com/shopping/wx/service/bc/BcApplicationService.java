package com.shopping.wx.service.bc;

import com.shopping.base.domain.bc.BcApplication;
import com.shopping.base.foundation.base.service.IBaseService;

import java.util.List;

/**
 * @author hx
 * @date 2019/7/19 17:18
 */
public interface BcApplicationService extends IBaseService<BcApplication,Long> {


     List<BcApplication>  getApplicationList() throws  Exception;

}
