package com.shopping.base.domain.bc;

import com.shopping.base.domain.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 广告图片表
 * @anthor bin
 * @data 2019/7/19 11:58
 */
@Data
@Entity
@Table(name="bc_banner")
@NoArgsConstructor
@AllArgsConstructor
public class BcBanner extends IdEntity {

    /**微信appId*/
    private String appId;
    /**图片URL*/
    private String imgUrl;
    /**图片超链接*/
    private String imgLink;
}
