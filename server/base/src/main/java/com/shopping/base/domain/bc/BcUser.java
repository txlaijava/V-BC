package com.shopping.base.domain.bc;

import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户
 * @author user on 2019/7/1.
 */
@Data
@Table(name = "bc_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcUser extends IdEntity {
    /** 乐观锁 */
    private Integer revision ;
    /** 微信AppId */
    private  String appId;
    /** 用户姓名 */
    private String name ;
    /** 所属部门 */
    private Long userDepartmentId ;
    /** 手机号 */
    private String mobile ;
    /** 状态;0:未激活  1:激活 */
    private Integer status ;
}
