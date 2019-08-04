package com.shopping.framework.druid.configuration;


import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryPrimary", transactionManagerRef="transactionManagerPrimary", basePackages= {"com.shopping.base.repository.shopping"})
public class DataSourcePrimaryConfig {

    @Resource(name = "shopping")
    DataSource shopping;

    /**
     * 主库jpa 实例管理器工厂配置
     */
    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(shopping)
                .packages("com.shopping.base.domain")
                .persistenceUnit("primaryPersistenceUnit")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * 主库事务管理器配置
     */
    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactoryPrimary(builder).getObject());
        return txManager;
    }
}