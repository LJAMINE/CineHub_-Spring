package com.cinehub.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.cinehub.repository")
@ComponentScan(basePackages = { "com.cinehub.service", "com.cinehub.repository" })
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        HikariConfig cfg = new HikariConfig();

        String jdbcUrl = env.getProperty("JDBC_URL",
                env.getProperty("jdbc.url",
                        "jdbc:mysql://mysql-db:3306/cinehub?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"));
        String username = env.getProperty("JDBC_USERNAME", env.getProperty("jdbc.username", "cinehub"));
        String password = env.getProperty("JDBC_PASSWORD", env.getProperty("jdbc.password", "cinehubpass"));

        cfg.setDriverClassName(env.getProperty("JDBC_DRIVER", "com.mysql.cj.jdbc.Driver"));
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(username);
        cfg.setPassword(password);

        cfg.setMaximumPoolSize(Integer.parseInt(env.getProperty("HIKARI_MAX_POOL_SIZE", "10")));
        cfg.setMinimumIdle(Integer.parseInt(env.getProperty("HIKARI_MIN_IDLE", "2")));
        cfg.setPoolName(env.getProperty("HIKARI_POOL_NAME", "cinehub-hikari"));

        return new HikariDataSource(cfg);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.cinehub.model");
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