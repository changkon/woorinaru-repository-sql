package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.IntermediateClassDao;
import woorinaru.core.model.management.administration.IntermediateClass;
import woorinaru.repository.sql.mapping.entity.IntermediateClassCopy;
import woorinaru.repository.sql.mapping.model.impl.IntermediateClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class IntermediateClassDaoImpl implements IntermediateClassDao {

    private static final Logger LOGGER = LogManager.getLogger(IntermediateClassDao.class);

    @Override
    public void create(IntermediateClass intermediateClass) {
        LOGGER.debug("Creating intermediate class");
        // Map file
        IntermediateClassMapper mapper = new IntermediateClassMapper();
        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = mapper.mapToEntity(intermediateClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(intermediateClassEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating intermediate class");
    }

    @Override
    public Optional<IntermediateClass> get(int id) {
        LOGGER.debug("Retrieving a intermediate class with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, id);

        LOGGER.debug("Intermediate class with id: %d. Found: %s", id, intermediateClassEntity == null ? "True" : "False");

        IntermediateClassMapper mapper = new IntermediateClassMapper();

        Optional<IntermediateClass> intermediateClassModel = Stream.ofNullable(intermediateClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return intermediateClassModel;
    }

    @Override
    public void delete(IntermediateClass intermediateClass) {
        LOGGER.debug("Deleting intermediate class with id: %d", intermediateClass.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.IntermediateClass deleteIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClass.getId());

        if (deleteIntermediateClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteIntermediateClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Intermediate class deleted");
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be deleted", intermediateClass.getId());
        }

        em.close();
    }

    @Override
    public void modify(IntermediateClass intermediateClass) {
        LOGGER.debug("Modifying intermediate class with id: %d", intermediateClass.getId());
        IntermediateClassMapper mapper = new IntermediateClassMapper();
        woorinaru.repository.sql.entity.management.administration.IntermediateClass modifiedIntermediateClassEntity = mapper.mapToEntity(intermediateClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.management.administration.IntermediateClass existingIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClass.getId());

        if (existingIntermediateClassEntity != null) {
            IntermediateClassCopy copyUtil = new IntermediateClassCopy();
            copyUtil.copy(modifiedIntermediateClassEntity, existingIntermediateClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Modified intermediate class");
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be modified", intermediateClass.getId());
        }

        em.close();
    }

    @Override
    public List<IntermediateClass> getAll() {
        LOGGER.debug("Retrieving all intermediate classes");
        IntermediateClassMapper mapper = new IntermediateClassMapper();

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.IntermediateClass> query = em.createQuery("SELECT i FROM IntermediateClass i", woorinaru.repository.sql.entity.management.administration.IntermediateClass.class);
        List<woorinaru.repository.sql.entity.management.administration.IntermediateClass> entityIntermediateClasses = query.getResultList();

        List<IntermediateClass> intermediateClasses = entityIntermediateClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d intermediate classes", intermediateClasses.size());
        em.close();
        return intermediateClasses;
    }
}
