package com.woorinaru.repository.sql.dao.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Template copied from testcontainers-java project to help with database container testing
 */
public abstract class AbstractContainerDatabaseIT {

    private final Set<HikariDataSource> datasourcesForCleanup = new HashSet<>();

    public Optional<ResultSet> performQuery(JdbcDatabaseContainer container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();
        return Optional.ofNullable(resultSet);
    }

    public DataSource getDataSource(JdbcDatabaseContainer container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());

        final HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        datasourcesForCleanup.add(dataSource);

        return dataSource;
    }

    @After
    public void teardown() {
        datasourcesForCleanup.forEach(HikariDataSource::close);
    }

    protected BiConsumer<EntityManager, Runnable> executeInTransaction() {
        return (em, runnable) -> {
            try {
                em.getTransaction().begin();
                runnable.run();
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        };
    }
}
