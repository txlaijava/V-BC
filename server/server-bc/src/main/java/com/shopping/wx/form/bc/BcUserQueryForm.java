package com.shopping.wx.form.bc;

import com.shopping.base.foundation.form.PaginationForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author hx
 * @date 2019/7/25 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BcUserQueryForm extends PaginationForm {

    /** appId*/
    private String appId;

    /** 姓名*/
    private String name;

    /**  部门id */
    private Long departmentId;

    /** 手机号*/
    private String mobile;
}
