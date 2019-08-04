package com.shopping.base.foundation.view.bc;

import com.shopping.base.domain.bc.BcUserDepartment;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @anthor bin
 * @data 2019/7/2 22:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BCUserDepartmentView extends BeanView<BcUserDepartment> implements Serializable {

    public Long id;

    /** 部门名称 */
    private String name ;

    /** 部门人数数量 */
    private  Integer num;
}
