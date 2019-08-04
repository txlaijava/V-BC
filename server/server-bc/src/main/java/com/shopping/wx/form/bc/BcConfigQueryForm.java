package com.shopping.wx.form.bc;

import com.shopping.base.foundation.form.PaginationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BcConfigQueryForm  extends PaginationForm {
    public Long id;
    /** 用户是否需要审核 */
    private boolean userNeedApprove ;
    /** 周六是否可报餐 */
    private boolean saturdayCanDiner ;
    /** 周日是否可报餐 */
    private boolean sundayCanDiner ;
    /** 午餐报餐时间  */
    private String lunchOrderTime;
    /** 晚餐报餐时间 */
    private String dinnerOrderTime;
    /**  午餐是否可报餐*/
    private boolean lunchCanMeal;
    /** 晚餐是否可报餐 */
    private boolean dinnerCanMeal;

    private String appId;


}
