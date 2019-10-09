package woorinaru.repository.sql.dao.impl;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import woorinaru.core.dao.spi.StudentDao;
import woorinaru.repository.sql.entity.user.Student;

@RunWith(Arquillian.class)
public class StudentDaoImplIT {

    @Inject
    private StudentDao studentDao;

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addClasses(Student.class, woorinaru.core.model.user.Student.class, StudentDao.class, StudentDaoImpl.class)
            .addPackages(true, "woorinaru.repository.sql.mapping");
    }

}
