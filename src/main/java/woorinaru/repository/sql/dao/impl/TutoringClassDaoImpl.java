package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.TutoringClassDao;
import woorinaru.core.model.management.administration.TutoringClass;
import woorinaru.repository.sql.adapter.TutoringClassAdapter;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.TutoringClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class TutoringClassDaoImpl implements TutoringClassDao {

    private static final Logger LOGGER = LogManager.getLogger(TutoringClassDao.class);

    private EntityManager em;

    public TutoringClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(TutoringClass tutoringClass) {
        LOGGER.debug("Creating tutoring class");
        // Map file
        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = mapper.mapToEntity(tutoringClass);

        em.getTransaction().begin();

        if (Objects.nonNull(tutoringClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : tutoringClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    tutoringClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(tutoringClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : tutoringClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                tutoringClassEntity.addStaff(staffEntity);
            }
        }

        if (Objects.nonNull(tutoringClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : tutoringClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                tutoringClassEntity.addStudent(studentEntity);
            }
        }

        if (Objects.nonNull(tutoringClass.getEvent())) {
            Event eventEntity = em.find(Event.class, tutoringClass.getEvent().getId());

            if (Objects.nonNull(eventEntity)) {
                tutoringClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(tutoringClassEntity);
        em.getTransaction().commit();

        LOGGER.debug("Finished creating tutoring class");
    }

    @Override
    public Optional<TutoringClass> get(int id) {
        LOGGER.debug("Retrieving a tutoring class with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, id);

        LOGGER.debug("Tutoring class with id: %d. Found: %s", id, tutoringClassEntity == null ? "True" : "False");

        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;

        Optional<TutoringClass> tutoringClassModel = Stream.ofNullable(tutoringClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return tutoringClassModel;
    }

    @Override
    public void delete(TutoringClass tutoringClass) {
        LOGGER.debug("Deleting tutoring class with id: %d", tutoringClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.TutoringClass deleteTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClass.getId());

        if (deleteTutoringClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteTutoringClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Tutoring class deleted");
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be deleted", tutoringClass.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<TutoringClass> updateCommand) {
        TutoringClass tutoringClassModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying tutoring class with id: %d", tutoringClassModel.getId());

        woorinaru.repository.sql.entity.management.administration.TutoringClass existingTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClassModel.getId());

        if (existingTutoringClassEntity != null) {
            TutoringClass tutoringClassAdapter = new TutoringClassAdapter(existingTutoringClassEntity, em);
            updateCommand.setReceiver(tutoringClassAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be modified", tutoringClassModel.getId());
        }
    }

    @Override
    public List<TutoringClass> getAll() {
        LOGGER.debug("Retrieving all tutoring classes");
        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.TutoringClass> query = em.createQuery("SELECT t FROM TutoringClass t", woorinaru.repository.sql.entity.management.administration.TutoringClass.class);
        List<woorinaru.repository.sql.entity.management.administration.TutoringClass> entityTutoringClasses = query.getResultList();

        List<TutoringClass> tutoringClasses = entityTutoringClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d intermediate classes", tutoringClasses.size());
        return tutoringClasses;
    }
}
