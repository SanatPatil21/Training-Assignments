package com.example.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.demo.repos.postgres2",
        entityManagerFactoryRef = "postgresEntityManagerFactoryTwo",
        transactionManagerRef = "postgresTransactionManagerTwo"
)
public class PostgresConfig2 {
	/*
	 * @Bean(name = "postgresDataSourceTwo")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/secdb")
                .driverClassName("org.postgresql.Driver")
                .username("postgres")
                .password("tiger")
                .build();
    }
	 
	 */
	
	@Bean(name="postgresDataSourceTwoProperties")
	@ConfigurationProperties(prefix="spring.datasource.postgresql.secdb")
	public DataSourceProperties  dataSourceProperties() {
    	return new DataSourceProperties();
    }
	
    @Bean(name = "postgresDataSourceTwo")
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSource h2DataSource(@Qualifier("postgresDataSourceTwoProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }
    
    @Bean(name = "postgresJpaPropertiesTwo")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }
    


    @Bean(name = "postgresEntityManagerFactoryTwo")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, 
            @Qualifier("postgresDataSourceTwo") DataSource dataSource,
            @Qualifier("postgresJpaPropertiesTwo") JpaProperties jpaProperties
    		) {
    	
    	
    	
    	
        return builder
                .dataSource(dataSource)
                .packages("com.example.demo.models") // PostgreSQL Entities
                .persistenceUnit("postgres")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean(name = "postgresTransactionManagerTwo")
    public PlatformTransactionManager transactionManager(
            @Qualifier("postgresEntityManagerFactoryTwo") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
