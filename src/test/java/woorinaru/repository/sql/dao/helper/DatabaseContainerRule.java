package woorinaru.repository.sql.dao.helper;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import woorinaru.repository.sql.dao.config.DatabaseConfig;
import woorinaru.repository.sql.dao.config.JpaEntityManagerFactory;
import woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseContainerRule implements BeforeEachCallback, AfterEachCallback {

    private MySQLContainer mySQLContainer;

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
//        if (this.mySQLContainer != null) {
//            this.mySQLContainer.close();
//        }
        // Close EntityManagerFactoryUtil
        EntityManagerFactoryUtil.close();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        // Startup mysql container
//        this.mySQLContainer = (MySQLContainer) new MySQLContainer(DatabaseConfig.DB_VERSION)
//            .withConfigurationOverride("META-INF/my.cnf")
//            .withInitScript("META-INF/sql/woorinaru_create.sql");
//        this.mySQLContainer.start();

//        EntityManagerFactory emf = new JpaEntityManagerFactory(this.mySQLContainer, DatabaseConfig.ENTITY_CLASSES).getEntityManagerFactory();

        // Get JDBC Connection Details
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("woorinaru-repository-test");
        EntityManagerFactoryUtil.setEntityManagerFactory(emf);
    }
}
