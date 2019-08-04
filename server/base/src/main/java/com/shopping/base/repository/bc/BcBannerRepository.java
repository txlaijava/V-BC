package com.shopping.base.repository.bc;

import com.shopping.base.domain.bc.BcBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @anthor bin
 * @data 2019/7/19 14:05
 */
public interface BcBannerRepository extends JpaRepository<BcBanner,Long>{

}
