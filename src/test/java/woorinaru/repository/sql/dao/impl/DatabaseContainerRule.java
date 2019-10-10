package woorinaru.repository.sql.dao.impl;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseContainerRule implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        // Close EntityManagerFactoryUtil
        EntityManagerFactoryUtil.close();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        // Get JDBC Connection Details
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("woorinaru-repository-test");
        EntityManagerFactoryUtil.setEntityManagerFactory(emf);
    }
}
