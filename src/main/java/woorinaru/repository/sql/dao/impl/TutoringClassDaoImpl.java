package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.TutoringClassDao;
import woorinaru.core.model.management.administration.TutoringClass;
import woorinaru.repository.sql.adapter.TutoringClassAdapter;
import woorinaru.repository.sql.mapping.model.TutoringClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class TutoringClassDaoImpl implements TutoringClassDao {

    private static final Logger LOGGER = LogManager.getLogger(TutoringClassDao.class);

    @Override
    public void create(TutoringClass tutoringClass) {
        LOGGER.debug("Creating tutoring class");
        // Map file
        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = mapper.mapToEntity(tutoringClass);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(tutoringClassEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating tutoring class");
    }

    @Override
    public Optional<TutoringClass> get(int id) {
        LOGGER.debug("Retrieving a tutoring class with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, id);

        LOGGER.debug("Tutoring class with id: %d. Found: %s", id, tutoringClassEntity == null ? "True" : "False");

        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;

        Optional<TutoringClass> tutoringClassModel = Stream.ofNullable(tutoringClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return tutoringClassModel;
    }

    @Override
    public void delete(TutoringClass tutoringClass) {
        LOGGER.debug("Deleting tutoring class with id: %d", tutoringClass.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.TutoringClass deleteTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClass.getId());

        if (deleteTutoringClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteTutoringClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Tutoring class deleted");
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be deleted", tutoringClass.getId());
        }

        em.close();
    }

    @Override
    public void modify(UpdateCommand<TutoringClass> updateCommand) {
        TutoringClass tutoringClassModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying tutoring class with id: %d", tutoringClassModel.getId());
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.management.administration.TutoringClass existingTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClassModel.getId());

        if (existingTutoringClassEntity != null) {
            TutoringClass beginnerClassAdapter = new TutoringClassAdapter(existingTutoringClassEntity);
            updateCommand.setReceiver(beginnerClassAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be modified", tutoringClassModel.getId());
        }

        em.close();
    }

    @Override
    public List<TutoringClass> getAll() {
        LOGGER.debug("Retrieving all tutoring classes");
        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.TutoringClass> query = em.createQuery("SELECT t FROM TutoringClass t", woorinaru.repository.sql.entity.management.administration.TutoringClass.class);
        List<woorinaru.repository.sql.entity.management.administration.TutoringClass> entityTutoringClasses = query.getResultList();

        List<TutoringClass> tutoringClasses = entityTutoringClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d intermediate classes", tutoringClasses.size());
        em.close();
        return tutoringClasses;
    }
}
