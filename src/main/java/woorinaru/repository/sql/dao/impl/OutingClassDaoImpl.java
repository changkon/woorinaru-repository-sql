package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.OutingClassDao;
import woorinaru.core.model.management.administration.OutingClass;
import woorinaru.repository.sql.mapping.entity.OutingClassCopy;
import woorinaru.repository.sql.mapping.model.impl.OutingClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class OutingClassDaoImpl implements OutingClassDao {

    private static final Logger LOGGER = LogManager.getLogger(OutingClassDao.class);

    @Override
    public void create(OutingClass outingClass) {
        LOGGER.debug("Creating an outing class");
        // Map file
        OutingClassMapper mapper = new OutingClassMapper();
        woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = mapper.mapToEntity(outingClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(outingClassEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating an outing class");
    }

    @Override
    public Optional<OutingClass> get(int id) {
        LOGGER.debug("Retrieving an outing class with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, id);

        LOGGER.debug("Outing class with id: %d. Found: %s", id, outingClassEntity == null ? "True" : "False");

        OutingClassMapper mapper = new OutingClassMapper();

        Optional<OutingClass> outingClassModel = Stream.ofNullable(outingClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return outingClassModel;
    }

    @Override
    public void delete(OutingClass outingClass) {
        LOGGER.debug("Deleting outing class with id: %d", outingClass.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.OutingClass deleteOutingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClass.getId());

        if (deleteOutingClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteOutingClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Outing class deleted");
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be deleted", outingClass.getId());
        }

        em.close();
    }

    @Override
    public void modify(OutingClass outingClass) {
        LOGGER.debug("Modifying outing class with id: %d", outingClass.getId());
        OutingClassMapper mapper = new OutingClassMapper();
        woorinaru.repository.sql.entity.management.administration.OutingClass modifiedOutingClassEntity = mapper.mapToEntity(outingClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.management.administration.OutingClass existingOutingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClass.getId());

        if (existingOutingClassEntity != null) {
            OutingClassCopy copyUtil = new OutingClassCopy();
            copyUtil.copy(modifiedOutingClassEntity, existingOutingClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Modified outing class");
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be modified", outingClass.getId());
        }

        em.close();
    }

    @Override
    public List<OutingClass> getAll() {
        LOGGER.debug("Retrieving all outing classes");
        OutingClassMapper mapper = new OutingClassMapper();

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.OutingClass> query = em.createQuery("SELECT o FROM OutingClass o", woorinaru.repository.sql.entity.management.administration.OutingClass.class);
        List<woorinaru.repository.sql.entity.management.administration.OutingClass> entityOutingClasses = query.getResultList();

        List<woorinaru.core.model.management.administration.OutingClass> outingClasses = entityOutingClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d outing classes", outingClasses.size());
        em.close();
        return outingClasses;
    }
}
