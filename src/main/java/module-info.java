module com.woorinaru.repository.sql {
    // woorinaru models
    requires com.woorinaru.core;
    // JPA
    requires java.persistence;
    requires java.sql;
    // Logger
    requires org.apache.logging.log4j;
    // Commons
    requires org.apache.commons.lang3;

    // byte buddy
    requires net.bytebuddy;

    // jaxb
    requires java.xml.bind;

    // mapstruct
    requires org.mapstruct;

    opens com.woorinaru.repository.sql.entity.resource to org.hibernate.orm.core;
    opens com.woorinaru.repository.sql.entity.user to org.hibernate.orm.core;
    opens com.woorinaru.repository.sql.entity.management.administration to org.hibernate.orm.core;

    // exports for mapping
    exports com.woorinaru.repository.sql.mapper.model to org.mapstruct;
    exports com.woorinaru.repository.sql.dao.impl;
}