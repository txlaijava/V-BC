package com.shopping.base.foundation.view.bc;

import com.shopping.base.domain.bc.BcConfig;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class BcConfigView  extends BeanView<BcConfig>  implements Serializable {

    public Long id;

    private String appId;
    /** 用户是否需要审核 */
    private boolean userNeedApprove ;
    /** 周六是否可报餐 */
    private boolean saturdayCanDiner ;
    /** 周日是否可报餐 */
    private boolean sundayCanDiner ;
    /** 午餐报餐时间 */
    private String lunchOrderTime;
    /** 晚餐报餐时间 */
    private String dinnerOrderTime;
    /**  午餐是否可报餐*/
    private boolean lunchCanMeal;
    /** 晚餐是否可报餐 */
    private boolean dinnerCanMeal;
}
