package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demo.repos.h2",
        entityManagerFactoryRef = "h2EntityManagerFactory",
        transactionManagerRef = "h2TransactionManager"
)

public class H2Config {

    @Primary
    @Bean(name = "h2DataSourceProperties")
    @ConfigurationProperties(prefix="spring.datasource.h2")
    public DataSourceProperties  dataSourceProperties() {
    	return new DataSourceProperties();
    }
    /*
     public DataSource h2DataSource() {
     
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:sanat")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("")
                .build();
    }
    */
    
    @Primary
    @Bean(name = "h2DataSource")
    @ConfigurationProperties("spring.datasource.h2")
    public DataSource h2DataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
    
    @Primary
    @Bean(name = "h2JpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, 
            @Qualifier("h2DataSource") DataSource dataSource,
            @Qualifier("h2JpaProperties")JpaProperties jpaProperties 
    		) {
    	
        return builder
                .dataSource(dataSource)
                .packages("com.example.demo.models") // H2 Entities
                .persistenceUnit("h2")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Primary
    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
