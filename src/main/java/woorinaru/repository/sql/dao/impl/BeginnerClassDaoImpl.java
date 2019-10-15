package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.BeginnerClassDao;
import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.repository.sql.mapping.entity.BeginnerClassCopy;
import woorinaru.repository.sql.mapping.model.BeginnerClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class BeginnerClassDaoImpl implements BeginnerClassDao {

    private static final Logger LOGGER = LogManager.getLogger(BeginnerClassDao.class);

    @Override
    public void create(BeginnerClass beginnerClass) {
        LOGGER.debug("Creating beginner class");
        // Map file
        BeginnerClassMapper mapper = new BeginnerClassMapper();
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = mapper.mapToEntity(beginnerClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(beginnerClassEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating beginner class");
    }

    @Override
    public Optional<BeginnerClass> get(int id) {
        LOGGER.debug("Retrieving a beginner class with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, id);

        LOGGER.debug("Beginner class with id: %d. Found: %s", id, beginnerClassEntity == null ? "True" : "False");

        BeginnerClassMapper mapper = new BeginnerClassMapper();

        Optional<BeginnerClass> beginnerClassModel = Stream.ofNullable(beginnerClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return beginnerClassModel;
    }

    @Override
    public void delete(BeginnerClass beginnerClass) {
        LOGGER.debug("Deleting beginner class with id: %d", beginnerClass.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.BeginnerClass deleteBeginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (deleteBeginnerClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteBeginnerClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Beginner class deleted");
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be deleted", beginnerClass.getId());
        }

        em.close();
    }

    @Override
    public void modify(BeginnerClass beginnerClass) {
        LOGGER.debug("Modifying beginner class with id: %d", beginnerClass.getId());
        BeginnerClassMapper mapper = new BeginnerClassMapper();
        woorinaru.repository.sql.entity.management.administration.BeginnerClass modifiedBeginnerClassEntity = mapper.mapToEntity(beginnerClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.management.administration.BeginnerClass existingBeginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (existingBeginnerClassEntity != null) {
            BeginnerClassCopy copyUtil = new BeginnerClassCopy();
            copyUtil.copy(modifiedBeginnerClassEntity, existingBeginnerClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Modified beginner class");
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be modified", beginnerClass.getId());
        }

        em.close();
    }

    @Override
    public List<BeginnerClass> getAll() {
        LOGGER.debug("Retrieving all beginner classes");
        BeginnerClassMapper mapper = new BeginnerClassMapper();

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query = em.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        List<woorinaru.repository.sql.entity.management.administration.BeginnerClass> entityBeginnerClasses = query.getResultList();

        List<BeginnerClass> beginnerClasses = entityBeginnerClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d beginner classes", beginnerClasses.size());
        em.close();
        return beginnerClasses;
    }
}
