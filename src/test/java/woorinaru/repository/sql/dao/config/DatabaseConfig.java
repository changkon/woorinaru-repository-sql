package woorinaru.repository.sql.dao.config;

import woorinaru.repository.sql.entity.management.administration.*;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Admin;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.entity.user.User;

public class DatabaseConfig {

    public static final String DB_VERSION = "mysql:5.7";
    public static final String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL8Dialect";

    public static final Class[] ENTITY_CLASSES = new Class[] {
        Resource.class,
        User.class,
        Admin.class,
        Student.class,
        Staff.class,
        WooriClass.class,
        BeginnerClass.class,
        IntermediateClass.class,
        OutingClass.class,
        TutoringClass.class,
        Event.class,
        Term.class
    };
}
