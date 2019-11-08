package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.BeginnerClassDao;
import com.woorinaru.core.exception.ReferenceNotFoundException;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.management.administration.BeginnerClass;
import com.woorinaru.repository.sql.entity.management.administration.Event;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.mapper.model.BeginnerClassMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class BeginnerClassDaoImpl implements BeginnerClassDao {

    private static final Logger LOGGER = LogManager.getLogger(BeginnerClassDao.class);

    private EntityManager em;

    public BeginnerClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(BeginnerClass beginnerClass) {
        LOGGER.debug("Creating beginner class");
        // Map file
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        com.woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = mapper.mapToEntity(beginnerClass);

        if (Objects.nonNull(beginnerClass.getResources())) {
            for (com.woorinaru.core.model.management.administration.Resource resourceModel : beginnerClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    beginnerClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(beginnerClass.getStaff())) {
            for (com.woorinaru.core.model.user.Staff staffModel : beginnerClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(staffEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    beginnerClassEntity.addStaff(staffEntity);
                }
            }
        }

        if (Objects.nonNull(beginnerClass.getStudents())) {
            for (com.woorinaru.core.model.user.Student studentModel : beginnerClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(studentEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    beginnerClassEntity.addStudent(studentEntity);
                }
            }
        }

        if (Objects.nonNull(beginnerClass.getEvent())) {
            Event eventEntity = em.find(Event.class, beginnerClass.getEvent().getId());

            if (Objects.isNull(eventEntity)) {
                throw new ReferenceNotFoundException(String.format("Could not find event id: %d", beginnerClass.getEvent().getId()));
            } else {
                beginnerClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(beginnerClassEntity);
        em.flush();

        LOGGER.debug("Finished creating beginner class");

        return beginnerClassEntity.getId();
    }

    @Override
    public BeginnerClass get(int id) {
        LOGGER.debug("Retrieving a beginner class with id: %d", id);
        com.woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, id);

        LOGGER.debug("Beginner class with id: %d. Found: %s", id, beginnerClassEntity == null ? "True" : "False");

        if (Objects.isNull(beginnerClassEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find beginner class with id: %d", id));
        }

        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        BeginnerClass beginnerClassModel = mapper.mapToModel(beginnerClassEntity);

        return beginnerClassModel;
    }

    @Override
    public void delete(BeginnerClass beginnerClass) {
        LOGGER.debug("Deleting beginner class with id: %d", beginnerClass.getId());

        // Map file
        com.woorinaru.repository.sql.entity.management.administration.BeginnerClass deleteBeginnerClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (deleteBeginnerClassEntity != null) {
            em.remove(deleteBeginnerClassEntity);
            LOGGER.debug("Beginner class deleted");
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be deleted", beginnerClass.getId());
            throw new ResourceNotFoundException(String.format("Beginner class with id: '%d' not found. Could not be deleted", beginnerClass.getId()));
        }
    }

    @Override
    public void modify(BeginnerClass beginnerClass) {
        LOGGER.debug("Modifying beginner class with id: %d", beginnerClass.getId());
        com.woorinaru.repository.sql.entity.management.administration.BeginnerClass existingBeginnerClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.BeginnerClass.class, beginnerClass.getId());

        if (existingBeginnerClassEntity != null) {
            com.woorinaru.core.model.management.administration.Event eventModel = beginnerClass.getEvent();

            if (eventModel != null) {
                Event existingEventEntity = em.find(Event.class, eventModel.getId());
                if (Objects.isNull(existingEventEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                } else {
                    existingBeginnerClassEntity.setEvent(existingEventEntity);
                }
            }

            // flush the existing collections
            Optional.ofNullable(existingBeginnerClassEntity.getStudents()).ifPresentOrElse(Collection::clear, () -> existingBeginnerClassEntity.setStudents(new ArrayList<>()));
            Optional.ofNullable(existingBeginnerClassEntity.getStaff()).ifPresentOrElse(Collection::clear, () -> existingBeginnerClassEntity.setStaff(new ArrayList<>()));
            Optional.ofNullable(existingBeginnerClassEntity.getResources()).ifPresentOrElse(Collection::clear, () -> existingBeginnerClassEntity.setResources(new ArrayList<>()));

            // re-populate
            for (com.woorinaru.core.model.user.Student studentModel : beginnerClass.getStudents()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(existingStudent)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    existingBeginnerClassEntity.addStudent(existingStudent);
                }
            }

            for (com.woorinaru.core.model.user.Staff staffModel : beginnerClass.getStaff()) {
                Staff existingStaff = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(existingStaff)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    existingBeginnerClassEntity.addStaff(existingStaff);
                }
            }

            for (com.woorinaru.core.model.management.administration.Resource resourceModel : beginnerClass.getResources()) {
                Resource existingResource = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(existingResource)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    existingBeginnerClassEntity.addResource(existingResource);
                }
            }
        } else {
            LOGGER.debug("Beginner class with id: '%d' not found. Could not be modified", beginnerClass.getId());
            throw new ResourceNotFoundException(String.format("Beginner class with id: '%d' not found. Could not be modified", beginnerClass.getId()));
        }
    }

    @Override
    public List<BeginnerClass> getAll() {
        LOGGER.debug("Retrieving all beginner classes");
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;

        TypedQuery<com.woorinaru.repository.sql.entity.management.administration.BeginnerClass> query = em.createQuery("SELECT b FROM BeginnerClass b", com.woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        List<com.woorinaru.repository.sql.entity.management.administration.BeginnerClass> entityBeginnerClasses = query.getResultList();

        List<BeginnerClass> beginnerClasses = entityBeginnerClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d beginner classes", beginnerClasses.size());
        return beginnerClasses;
    }
}
