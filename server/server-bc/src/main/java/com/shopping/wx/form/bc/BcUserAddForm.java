package com.shopping.wx.form.bc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @anthor bin
 * @data 2019/7/5 11:52
 * 类描述：报餐用户新增
 */
@Data
@AllArgsConstructor
@NoArgsConstructor      //无参数构造方法
@EqualsAndHashCode(callSuper = false)
public class BcUserAddForm {
    /**
     * 报餐用户名
     */
    private String name;
    /**
     * 所属部门
     */
    private String userDepartmentId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户openid
     */
    private String openid;
}
