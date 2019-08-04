package com.shopping.base.foundation.view.bc;


import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BcUserCacheView  extends BeanView<BcUser> implements Serializable {

    private Integer status;

    private String name;

    private String mobilePhone;

    /** 所属部门 */
    private Long userDepartmentId ;
}
