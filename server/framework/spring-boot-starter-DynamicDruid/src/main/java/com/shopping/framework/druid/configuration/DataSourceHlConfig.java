package com.shopping.framework.druid.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactorySecondary", transactionManagerRef="transactionManagerSecondary", basePackages= {"com.shopping.base.repository.hl"})
public class DataSourceHlConfig
{

    @Resource(name="hl")
    @Qualifier("secondaryDataSource")
    DataSource hl;

    /**
     * 二库jpa 实例管理器工厂配置
     */
    @Qualifier
    @Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder)
    {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(hl)
                .packages("com.shopping.base.domain.hl")
                .persistenceUnit("secondaryPersistenceUnit")
                .build();
        Properties properties = new Properties();
        properties.setProperty("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaProperties(properties);
        return em;
    }

    /**
     * 二库事务管理器配置
     */
    @Qualifier
    @Bean(name = "transactionManagerSecondary")
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder)
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactorySecondary(builder).getObject());
        return txManager;
    }
}
