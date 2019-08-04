package com.shopping.base.foundation.view.bc;

import com.shopping.base.domain.bc.BcUser;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BcUserInfoView extends BeanView<BcUser> implements Serializable {

    private Integer status;

    private String name;

    private String mobile;

    /** 所属部门 */
    private Long userDepartmentId ;
}
