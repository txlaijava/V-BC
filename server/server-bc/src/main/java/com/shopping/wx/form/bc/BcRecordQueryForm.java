package com.shopping.wx.form.bc;

import com.shopping.base.foundation.form.PaginationForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author hx
 * @date 2019/7/20 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BcRecordQueryForm extends PaginationForm {

    private String appId;

    private String name;

    private String mobile;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 就餐时间
     */
    private String dinTime;
    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 开始时间
     */
    String startTime;

    /**
     * 结束时间
     */
    String endTime;
}
