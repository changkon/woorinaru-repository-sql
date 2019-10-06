package woorinaru.repository.sql.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryUtil {

    private static final EntityManagerFactory emFactory;

    static {
        // NOTE: This must be the same value as the one referenced in persistence.xml
        emFactory = Persistence.createEntityManagerFactory("woorinaru-repository");
    }

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public static void close() {
        emFactory.close();
    }

}
