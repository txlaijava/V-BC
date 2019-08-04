package com.shopping.base.foundation.view.bc;

import com.shopping.base.domain.bc.BcApplication;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author hx
 * @date 2019/7/19 17:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BcApplicationView  extends BeanView<BcApplication> implements Serializable {


    private String appName;

    private String appId;

}
