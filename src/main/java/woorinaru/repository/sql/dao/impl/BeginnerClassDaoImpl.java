package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.BeginnerClassDao;
import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.repository.sql.adapter.BeginnerClassAdapter;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.BeginnerClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class BeginnerClassDaoImpl implements BeginnerClassDao {

    private static final Logger LOGGER = LogManager.getLogger(BeginnerClassDao.class);

    private EntityManager em;

    public BeginnerClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(BeginnerClass beginnerClass) {
        LOGGER.debug("Creating beginner class");
        // Map file
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = mapper.mapToEntity(beginnerClass);

        em.getTransaction().begin();

        if (Objects.nonNull(beginnerClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : beginnerClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    beginnerClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(beginnerClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : beginnerClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                beginnerClassEntity.addStaff(staffEntity);
            }
        }

        if (Objects.nonNull(beginnerClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : beginnerClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                beginnerClassEntity.addStudent(studentEntity);
            }
        }

        if (Objects.nonNull(beginnerClass.getEvent())) {
            Event eventEntity = em.find(Event.class, beginnerClass.getEvent().getId());

            if (Objects.nonNull(eventEntity)) {
                beginnerClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(beginnerClassEntity);
        em.getTransaction().commit();

        LOGGER.debug("Finished creating beginner class");
    }

    @Override
    public Optional<BeginnerClass> get(int id) {
        LOGGER.debug("Retrieving a beginner class with id: %d", id);
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, id);

        LOGGER.debug("Beginner class with id: %d. Found: %s", id, beginnerClassEntity == null ? "True" : "False");

        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;

        Optional<BeginnerClass> beginnerClassModel = Stream.ofNullable(beginnerClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return beginnerClassModel;
    }

    @Override
    public void delete(BeginnerClass beginnerClass) {
        LOGGER.debug("Deleting beginner class with id: %d", beginnerClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.BeginnerClass deleteBeginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (deleteBeginnerClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteBeginnerClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Beginner class deleted");
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be deleted", beginnerClass.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<BeginnerClass> updateCommand) {
        BeginnerClass beginnerClassModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying beginner class with id: %d", beginnerClassModel.getId());
        woorinaru.repository.sql.entity.management.administration.BeginnerClass existingBeginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClassModel.getId());

        if (existingBeginnerClassEntity != null) {
            BeginnerClass beginnerClassAdapter = new BeginnerClassAdapter(existingBeginnerClassEntity, em);
            updateCommand.setReceiver(beginnerClassAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be modified", beginnerClassModel.getId());
        }
    }

    @Override
    public List<BeginnerClass> getAll() {
        LOGGER.debug("Retrieving all beginner classes");
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query = em.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        List<woorinaru.repository.sql.entity.management.administration.BeginnerClass> entityBeginnerClasses = query.getResultList();

        List<BeginnerClass> beginnerClasses = entityBeginnerClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d beginner classes", beginnerClasses.size());
        return beginnerClasses;
    }
}
