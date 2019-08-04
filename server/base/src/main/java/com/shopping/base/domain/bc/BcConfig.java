package com.shopping.base.domain.bc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 报餐配置表
 * @author user on 2019/7/1.
 */
@Data
@Table(name = "bc_config")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcConfig {
    /** id;id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Long id;

    /** 微信AppId */
    private  String appId;
    /** 用户是否需要审核 */
    private boolean userNeedApprove ;
    /** 周六是否可报餐 */
    private boolean saturdayCanDiner ;
    /** 周日是否可报餐 */
    private boolean sundayCanDiner ;
    /** 午餐报餐时间*/
    private String lunchOrderTime;
    /** 晚餐报餐时间 */
    private String dinnerOrderTime;
    /**  午餐是否可报餐*/
    private boolean lunchCanMeal;
    /** 晚餐是否可报餐 */
    private boolean dinnerCanMeal;
}
