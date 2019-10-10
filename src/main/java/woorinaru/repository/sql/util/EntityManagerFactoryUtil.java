package woorinaru.repository.sql.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryUtil {

    private static EntityManagerFactory emFactory;

    private EntityManagerFactoryUtil() {}

    public static void setEntityManagerFactory(EntityManagerFactory theEmFactory) {
        if (emFactory != null) {
            emFactory.close();
        }
        emFactory = theEmFactory;
    }

    public static EntityManager getEntityManager() {
        if (emFactory == null) {
            // NOTE: This must be the same value as the one referenced in persistence.xml
            emFactory = Persistence.createEntityManagerFactory("woorinaru-repository");
        }
        return emFactory.createEntityManager();
    }

    public static void close() {
        emFactory.close();
    }

}
