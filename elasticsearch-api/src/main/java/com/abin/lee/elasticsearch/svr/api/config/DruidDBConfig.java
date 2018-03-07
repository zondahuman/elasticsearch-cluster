package com.abin.lee.elasticsearch.svr.api.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/10.
 */
@Configuration
public class DruidDBConfig {


    private static final Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.durid.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.durid.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.durid.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.durid.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.durid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.durid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.durid.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.durid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.durid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.durid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.durid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.durid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.durid.filters}")
    private String filters;

    @Value("{spring.datasource.durid.connectionProperties}")
    private String connectionProperties;


    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }




}
