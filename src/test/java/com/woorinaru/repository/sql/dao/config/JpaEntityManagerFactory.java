package com.woorinaru.repository.sql.dao.config;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.*;
import java.util.stream.Collectors;

public class JpaEntityManagerFactory {

    private final String DB_URL;
    private final String DB_USER_NAME = "username";
    private final String DB_PASSWORD = "password";
    private final String DB_DRIVER;
    private final Class[] entityClasses;

    public JpaEntityManagerFactory(JdbcDatabaseContainer databaseContainer, Class[] entityClasses) {
        this.DB_URL = databaseContainer.getJdbcUrl();
        this.DB_DRIVER = databaseContainer.getDriverClassName();
        this.entityClasses = entityClasses;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
            .build();
    }

    protected HibernatePersistenceUnitInfo getPersistenceUnitInfo(String name) {
        return new HibernatePersistenceUnitInfo(name, getEntityClassNames(), getProperties());
    }

    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
            .stream()
            .map(Class::getName)
            .collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("javax.persistence.jdbc.driver", DB_DRIVER);
        properties.put("javax.persistence.jdbc.url", DB_URL);
        properties.put("hibernate.dialect", DatabaseConfig.HIBERNATE_DIALECT);
//        properties.put("hibernate.id.new_generator_mappings", false);
//        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }

    protected Class[] getEntities() {
        return entityClasses;
    }

//    protected DataSource getMysqlDataSource() {
//        MysqlDataSource mysqlDataSource = new MysqlDataSource();
//        mysqlDataSource.setURL(DB_URL);
//        mysqlDataSource.setUser(DB_USER_NAME);
//        mysqlDataSource.setPassword(DB_PASSWORD);
//        return mysqlDataSource;
//    }
}
