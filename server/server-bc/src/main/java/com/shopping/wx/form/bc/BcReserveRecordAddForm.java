package com.shopping.wx.form.bc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @anthor bin
 * @data 2019/7/9 16:02
 * 类描述：预约报餐记录新增
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BcReserveRecordAddForm {
    /** 预定时间 */
    private String reserveTime ;
    /** 预定时间-周几 */
    private String reserveTimeWeek ;
}
