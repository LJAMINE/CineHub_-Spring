package com.cinehub.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig cfg = new HikariConfig();
        cfg.setDriverClassName(env.getProperty("JDBC_DRIVER", "com.mysql.cj.jdbc.Driver"));
        cfg.setJdbcUrl(env.getProperty("JDBC_URL", "jdbc:mysql://mysql-db:3306/cinehub?useSSL=false&serverTimezone=UTC"));
        cfg.setUsername(env.getProperty("JDBC_USERNAME", "cinehub"));
        cfg.setPassword(env.getProperty("JDBC_PASSWORD", "cinehubpass"));

        cfg.setMaximumPoolSize(Integer.parseInt(env.getProperty("HIKARI_MAX_POOL_SIZE", "10")));
        cfg.setMinimumIdle(Integer.parseInt(env.getProperty("HIKARI_MIN_IDLE", "2")));
        cfg.setPoolName("cinehub-hikari");

        return new HikariDataSource(cfg);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.cinehub.model"); // adjust if needed
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProps = new HashMap<>();
        jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty("HIBERNATE_DDL_AUTO", "update"));
        jpaProps.put("hibernate.dialect", env.getProperty("HIBERNATE_DIALECT", "org.hibernate.dialect.MySQL8Dialect"));
        jpaProps.put("hibernate.show_sql", env.getProperty("HIBERNATE_SHOW_SQL", "false"));
        emf.setJpaPropertyMap(jpaProps);

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(emf);
        return tm;
    }
}