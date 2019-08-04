package com.shopping.wx.form.bc;

        import com.shopping.base.foundation.form.PaginationForm;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.EqualsAndHashCode;
        import lombok.NoArgsConstructor;

/**
 * @anthor bin
 * @data 2019/7/2 21:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor      //无参数构造方法
@EqualsAndHashCode(callSuper = false)
public class BcUserDepartmentQueryFrom extends PaginationForm {

    public Long id;
    /** 微信AppId */
    private  String appId;
    /** 部门名称 */
    private String name ;
}
