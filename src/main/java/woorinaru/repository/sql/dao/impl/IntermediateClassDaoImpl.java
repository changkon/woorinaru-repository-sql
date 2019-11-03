package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.IntermediateClassDao;
import woorinaru.core.exception.ReferenceNotFoundException;
import woorinaru.core.exception.ResourceNotFoundException;
import woorinaru.core.model.management.administration.IntermediateClass;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapper.model.IntermediateClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class IntermediateClassDaoImpl implements IntermediateClassDao {

    private static final Logger LOGGER = LogManager.getLogger(IntermediateClassDao.class);

    private EntityManager em;

    public IntermediateClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(IntermediateClass intermediateClass) {
        LOGGER.debug("Creating intermediate class");
        // Map file
        IntermediateClassMapper mapper = IntermediateClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = mapper.mapToEntity(intermediateClass);

        if (Objects.nonNull(intermediateClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : intermediateClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    intermediateClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(intermediateClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : intermediateClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(staffEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    intermediateClassEntity.addStaff(staffEntity);
                }
            }
        }

        if (Objects.nonNull(intermediateClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : intermediateClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(studentEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    intermediateClassEntity.addStudent(studentEntity);
                }
            }
        }

        if (Objects.nonNull(intermediateClass.getEvent())) {
            Event eventEntity = em.find(Event.class, intermediateClass.getEvent().getId());

            if (Objects.isNull(eventEntity)) {
                throw new ReferenceNotFoundException(String.format("Could not find event id: %d", intermediateClass.getEvent().getId()));
            } else {
                intermediateClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(intermediateClassEntity);
        em.flush();

        LOGGER.debug("Finished creating intermediate class");

        return intermediateClassEntity.getId();
    }

    @Override
    public IntermediateClass get(int id) {
        LOGGER.debug("Retrieving a intermediate class with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, id);

        LOGGER.debug("Intermediate class with id: %d. Found: %s", id, intermediateClassEntity == null ? "True" : "False");

        if (Objects.isNull(intermediateClassEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find intermediate class with id: %d", id));
        }

        IntermediateClassMapper mapper = IntermediateClassMapper.MAPPER;
        IntermediateClass intermediateClassModel = mapper.mapToModel(intermediateClassEntity);

        return intermediateClassModel;
    }

    @Override
    public void delete(IntermediateClass intermediateClass) {
        LOGGER.debug("Deleting intermediate class with id: %d", intermediateClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.IntermediateClass deleteIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClass.getId());

        if (deleteIntermediateClassEntity != null) {
            em.remove(deleteIntermediateClassEntity);
            LOGGER.debug("Intermediate class deleted");
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be deleted", intermediateClass.getId());
            throw new ResourceNotFoundException(String.format("Intermediate class with id: '%d' not found. Could not be deleted", intermediateClass.getId()));
        }
    }

    @Override
    public void modify(IntermediateClass intermediateClass) {
        LOGGER.debug("Modifying intermediate class with id: %d", intermediateClass.getId());
        woorinaru.repository.sql.entity.management.administration.IntermediateClass existingIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClass.getId());

        if (existingIntermediateClassEntity != null) {
            woorinaru.core.model.management.administration.Event eventModel = intermediateClass.getEvent();
            if (eventModel != null) {
                Event existingEventEntity = em.find(Event.class, eventModel.getId());
                if (Objects.isNull(existingEventEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                } else {
                    existingIntermediateClassEntity.setEvent(existingEventEntity);
                }
            }

            // flush the existing collections
            Optional.ofNullable(existingIntermediateClassEntity.getStudents()).ifPresentOrElse(Collection::clear, () -> existingIntermediateClassEntity.setStudents(new ArrayList<>()));
            Optional.ofNullable(existingIntermediateClassEntity.getStaff()).ifPresentOrElse(Collection::clear, () -> existingIntermediateClassEntity.setStaff(new ArrayList<>()));
            Optional.ofNullable(existingIntermediateClassEntity.getResources()).ifPresentOrElse(Collection::clear, () -> existingIntermediateClassEntity.setResources(new ArrayList<>()));

            // re-populate
            for (woorinaru.core.model.user.Student studentModel : intermediateClass.getStudents()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(existingStudent)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    existingIntermediateClassEntity.addStudent(existingStudent);
                }
            }

            for (woorinaru.core.model.user.Staff staffModel : intermediateClass.getStaff()) {
                Staff existingStaff = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(existingStaff)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    existingIntermediateClassEntity.addStaff(existingStaff);
                }
            }

            for (woorinaru.core.model.management.administration.Resource resourceModel : intermediateClass.getResources()) {
                Resource existingResource = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(existingResource)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    existingIntermediateClassEntity.addResource(existingResource);
                }
            }
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be modified", intermediateClass.getId());
            throw new ResourceNotFoundException(String.format("Intermediate class with id: '%d' not found. Could not be modified", intermediateClass.getId()));
        }
    }

    @Override
    public List<IntermediateClass> getAll() {
        LOGGER.debug("Retrieving all intermediate classes");
        IntermediateClassMapper mapper = IntermediateClassMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.IntermediateClass> query = em.createQuery("SELECT i FROM IntermediateClass i", woorinaru.repository.sql.entity.management.administration.IntermediateClass.class);
        List<woorinaru.repository.sql.entity.management.administration.IntermediateClass> entityIntermediateClasses = query.getResultList();

        List<IntermediateClass> intermediateClasses = entityIntermediateClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d intermediate classes", intermediateClasses.size());

        return intermediateClasses;
    }
}
