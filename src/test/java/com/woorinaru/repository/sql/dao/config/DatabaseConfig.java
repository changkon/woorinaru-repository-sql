package com.woorinaru.repository.sql.dao.config;

import com.woorinaru.repository.sql.entity.management.administration.*;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.entity.user.Admin;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.entity.user.User;

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
