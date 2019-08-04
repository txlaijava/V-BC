package com.shopping.base.repository.bc;

import com.shopping.base.domain.bc.BcConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BcConfigRepository extends JpaRepository<BcConfig,Long> {

    /**
     * 根据appId 获取配置
     * @param appId
     * @return
     */
    BcConfig  findByAppId(String appId);

    /**
     * 根据appId 和 id  查找 配置
     * @param appId
     * @param id
     * @return
     */
    BcConfig  findByAppIdAndId(String appId,Long id);
}
