package com.shopping.framework.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Druid自动配置
 *
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class)
@EnableConfigurationProperties(DruidProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactoryPrimary", transactionManagerRef="transactionManagerPrimary", basePackages= {"com.shopping"})
public class DruidAutoConfiguration {

    @Autowired
    private DruidProperties properties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        if (properties.getInitialSize() > 0) {
            dataSource.setInitialSize(properties.getInitialSize());
        }
        if (properties.getMinIdle() > 0) {
            dataSource.setMinIdle(properties.getMinIdle());
        }
        if (properties.getMaxActive() > 0) {
            dataSource.setMaxActive(properties.getMaxActive());
        }
        if (properties.getTestOnBorrow() != null) {
            dataSource.setTestOnBorrow(properties.getTestOnBorrow());
        }
        if (properties.getMaxWait() > 0) {
            dataSource.setMaxWait(properties.getMaxWait());
        }
        if (properties.getTimeBetweenEvictionRunsMillis() > 0) {
            dataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        }
        if (properties.getMinEvictableIdleTimeMillis() > 0) {
            dataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        }
        if (properties.getValidationQuery() != null) {
            dataSource.setValidationQuery(properties.getValidationQuery());
        }
        if (properties.getTestWhileIdle() != null) {
            dataSource.setTestWhileIdle(properties.getTestWhileIdle());
        }
        if (properties.getTestOnReturn() != null) {
            dataSource.setTestOnReturn(properties.getTestOnReturn());
        }
        if (properties.getPoolPreparedStatements() != null) {
            dataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
        }
        if (properties.getMaxPoolPreparedStatementPerConnectionSize() > 0) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (properties.getFilters() != null) {
            try {
                dataSource.setFilters(properties.getFilters());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (properties.getConnectionProperties() != null) {
            dataSource.setConnectionProperties(properties.getConnectionProperties());
        }
        return dataSource;
    }

    /**
     * 主库jpa 实例管理器工厂配置
     */
    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(dataSource())
                .packages("com.shopping")
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
