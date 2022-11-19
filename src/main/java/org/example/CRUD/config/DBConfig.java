package org.example.CRUD.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan("org.example.CRUD")

@Configuration
@PropertySource({"classpath:hibernate.properties"})
public class DBConfig {
    private final Environment env;

    DBConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public Properties getHibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource =new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("hibernate.driver"));
        dataSource.setUrl(env.getRequiredProperty("hibernate.url"));
        dataSource.setUsername(env.getRequiredProperty("hibernate.username"));
        dataSource.setPassword(env.getRequiredProperty("hibernate.password"));
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.example.CRUD.model");
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager =new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
