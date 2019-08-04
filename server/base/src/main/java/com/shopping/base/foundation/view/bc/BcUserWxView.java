package com.shopping.base.foundation.view.bc;

import com.shopping.base.domain.bc.BcUserWx;
import com.shopping.base.foundation.view.BeanView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class BcUserWxView extends BeanView<BcUserWx> implements Serializable {

    public Long id;
    public Date addTime;

    /**
     * 乐观锁
     */
    private Integer revision;

    /**
     * openid
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private String gender;
    /**
     * 语言
     */
    private String language;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 头像链接
     */
    private String avatarurl;

    /**
     * unionId
     */
    private String unionid;
}
