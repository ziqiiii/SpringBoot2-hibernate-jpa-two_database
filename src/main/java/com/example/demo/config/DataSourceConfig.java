package com.example.demo.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * create by ziqi.zhang on 2018/9/10
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSourcePropertiesDemo")  // config/server.properties
    @ConfigurationProperties("demo.datasource")
    public DataSourceProperties mnoDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "dataSourceDemo")
    @ConfigurationProperties("demo.datasource.hikari")  // resources/application.yaml
    public DataSource mnoDataSource() {
        return mnoDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
