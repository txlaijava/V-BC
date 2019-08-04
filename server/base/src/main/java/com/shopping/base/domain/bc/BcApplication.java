package com.shopping.base.domain.bc;

import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 报餐应用表
 * @author hx
 * @date 2019/7/19 16:52
 */
@Data
@Table(name = "bc_application")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcApplication extends IdEntity {

    /** 乐观锁 */
    private Integer revision ;
    /** 应用名称 */
    private  String  appName;
    /** 应用id */
    private  String appId;
}
