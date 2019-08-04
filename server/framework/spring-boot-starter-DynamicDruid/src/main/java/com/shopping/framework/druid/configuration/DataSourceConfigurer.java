package com.shopping.framework.druid.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Multiple DataSource Configurer
 */
@Configuration
public class DataSourceConfigurer {

    /**
     * master DataSource
     *
     * @return data source
     */
    @Bean("shopping")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.shopping")
    public DataSource shopping() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * slave DataSource
     *
     * @return data source
     */
    @Bean("hl")
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.hl")
    public DataSource hl() {
        return DruidDataSourceBuilder.create().build();
    }


    /**
     * Dynamic data source.
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("shopping", shopping());
        dataSourceMap.put("hl", hl());

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(shopping());
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());
        return dynamicRoutingDataSource;
    }

    /**
     * Transaction manager platform transaction manager.
     * Here should be config if using Transaction
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}

