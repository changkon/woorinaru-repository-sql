package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.BeginnerClassDao;
import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.BeginnerClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            em.remove(deleteBeginnerClassEntity);
            LOGGER.debug("Beginner class deleted");
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be deleted", beginnerClass.getId());
        }
    }

    @Override
    public void modify(BeginnerClass beginnerClass) {
        LOGGER.debug("Modifying beginner class with id: %d", beginnerClass.getId());
        woorinaru.repository.sql.entity.management.administration.BeginnerClass existingBeginnerClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (existingBeginnerClassEntity != null) {
            woorinaru.core.model.management.administration.Event eventModel = beginnerClass.getEvent();
            if (eventModel != null) {
                Event existingEventEntity = em.find(Event.class, eventModel.getId());
                existingBeginnerClassEntity.setEvent(existingEventEntity);
            }

            // flush the existing collections
            existingBeginnerClassEntity.setStudents(new ArrayList<>());
            existingBeginnerClassEntity.setStaff(new ArrayList<>());
            existingBeginnerClassEntity.setResources(new ArrayList<>());

            // re-populate
            for (woorinaru.core.model.user.Student studentModel : beginnerClass.getStudents()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());
                existingBeginnerClassEntity.addStudent(existingStudent);
            }

            for (woorinaru.core.model.user.Staff staffModel : beginnerClass.getStaff()) {
                Staff existingStaff = em.find(Staff.class, staffModel.getId());
                existingBeginnerClassEntity.addStaff(existingStaff);
            }

            for (woorinaru.core.model.management.administration.Resource resourceModel : beginnerClass.getResources()) {
                Resource existingResource = em.find(Resource.class, resourceModel.getId());
                existingBeginnerClassEntity.addResource(existingResource);
            }
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be modified", beginnerClass.getId());
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
