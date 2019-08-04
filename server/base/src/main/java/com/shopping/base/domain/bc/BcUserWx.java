package com.shopping.base.domain.bc;

import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author user on 2019/7/1.
 */
@Data
@Table(name = "bc_user_wx")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BcUserWx extends IdEntity {
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 微信APPID
     */
    private String appId;
    /**
     * 关联用户主表
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private BcUser bcUser;
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


  /*  public BcUserWx(){
        this.addTime = new Date();
    }*/

}
