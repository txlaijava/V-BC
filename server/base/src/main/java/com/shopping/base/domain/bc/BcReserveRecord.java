package com.shopping.base.domain.bc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 报餐预定表
 * @author user on 2019/7/2.
 */
@Data
@Table(name = "bc_reserve_record")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcReserveRecord extends IdEntity {
    /** 乐观锁 */
    private Integer revision ;
    /** 微信AppId */
    private  String appId;
    /** 预定用户id */
    private Long bcUserId ;
    /** 预定时间 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date reserveTime ;
    /** 预定时间-周几 */
    private String reserveTimeWeek ;
}
