package com.shopping.wx.form.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 类描述: 用户新增
 */
@Data
@AllArgsConstructor
@NoArgsConstructor      //无参数构造方法
@EqualsAndHashCode(callSuper = false)
public class UserAddForm {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String trueName;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色 buyer_seller buyer admin
     */
    private String userRole;



}
