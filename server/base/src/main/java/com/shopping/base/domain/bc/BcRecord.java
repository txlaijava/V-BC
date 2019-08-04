package com.shopping.base.domain.bc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 报餐记录表
 * @author user on 2019/7/1.
 */
@Data
@Table(name = "bc_record")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcRecord extends IdEntity {
    /** 乐观锁 */
    private Integer revision ;
    /** 微信AppId */
    private  String appId;
    /** 报餐用户id */
    private Long userId ;
    /** 报餐类型;0：早餐  1：中餐 2：晚餐 */
    private Integer bcType ;
    /** 报餐渠道;0:手动报餐  1:预约报餐  2:补报 */
    private Integer bcChannel ;
    /** 就餐时间*/
    @Column(name = "dintime")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date dinTime;

    /** 是否就餐;0:未就餐 1:就餐 */
    private Integer hadEat;
}
