package com.example.demo.jpa;

import com.example.demo.config.DataSourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;


/**
 * create by ziqi.zhang on 2018/9/10
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryDemo",
        transactionManagerRef = "transactionManagerDemo",
        basePackages = {"com.example.demo.repository"})//设置dao（repo）所在位置
@Import({DataSourceConfig.class})
@AutoConfigureAfter(DataSourceConfig.class)
public class RepositoryConfig {

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    @Qualifier("dataSourceDemo")
    private DataSource dataSource;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.determineDatabase(dataSource));
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }

    @Bean
    public EntityManagerFactoryBuilder builder() {
        return new EntityManagerFactoryBuilder(jpaVendorAdapter(), jpaProperties.getProperties(), null);
    }

    @Bean(name = "entityManagerFactoryDemo")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource)
                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                .packages("com.example.demo.entity") //设置实体类所在位置
                .persistenceUnit("demoPersistenceUnit")
                .build();
    }

    @Bean(name = "transactionManagerDemo")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }


    @Bean(name = "entityManagerDemo")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactory(builder).getObject().createEntityManager();
    }
}

