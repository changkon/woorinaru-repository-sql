package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.OutingClassDao;
import com.woorinaru.core.exception.ReferenceNotFoundException;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.management.administration.OutingClass;
import com.woorinaru.repository.sql.entity.management.administration.Event;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.mapper.model.OutingClassMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class OutingClassDaoImpl implements OutingClassDao {

    private static final Logger LOGGER = LogManager.getLogger(OutingClassDao.class);

    private EntityManager em;

    public OutingClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(OutingClass outingClass) {
        LOGGER.debug("Creating an outing class");
        // Map file
        OutingClassMapper mapper = OutingClassMapper.MAPPER;
        com.woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = mapper.mapToEntity(outingClass);

        if (Objects.nonNull(outingClass.getResources())) {
            for (com.woorinaru.core.model.management.administration.Resource resourceModel : outingClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    outingClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(outingClass.getStaff())) {
            for (com.woorinaru.core.model.user.Staff staffModel : outingClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(staffEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    outingClassEntity.addStaff(staffEntity);
                }
            }
        }

        if (Objects.nonNull(outingClass.getStudents())) {
            for (com.woorinaru.core.model.user.Student studentModel : outingClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(studentEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    outingClassEntity.addStudent(studentEntity);
                }
            }
        }

        if (Objects.nonNull(outingClass.getEvent())) {
            Event eventEntity = em.find(Event.class, outingClass.getEvent().getId());

            if (Objects.isNull(eventEntity)) {
                throw new ReferenceNotFoundException(String.format("Could not find event id: %d", outingClass.getEvent().getId()));
            } else {
                outingClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(outingClassEntity);
        em.flush();

        LOGGER.debug("Finished creating an outing class");
        return outingClassEntity.getId();
    }

    @Override
    public OutingClass get(int id) {
        LOGGER.debug("Retrieving an outing class with id: %d", id);

        com.woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.OutingClass.class, id);

        LOGGER.debug("Outing class with id: %d. Found: %s", id, outingClassEntity == null ? "True" : "False");

        if (Objects.isNull(outingClassEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find outing class with id: %d", id));
        }

        OutingClassMapper mapper = OutingClassMapper.MAPPER;

        OutingClass outingClassModel = mapper.mapToModel(outingClassEntity);

        return outingClassModel;
    }

    @Override
    public void delete(OutingClass outingClass) {
        LOGGER.debug("Deleting outing class with id: %d", outingClass.getId());

        // Map file
        com.woorinaru.repository.sql.entity.management.administration.OutingClass deleteOutingClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClass.getId());

        if (deleteOutingClassEntity != null) {
            em.remove(deleteOutingClassEntity);
            LOGGER.debug("Outing class deleted");
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be deleted", outingClass.getId());
            throw new ResourceNotFoundException(String.format("Outing class with id: '%d' not found. Could not be deleted", outingClass.getId()));
        }
    }

    @Override
    public void modify(OutingClass outingClass) {
        LOGGER.debug("Modifying outing class with id: %d", outingClass.getId());
        com.woorinaru.repository.sql.entity.management.administration.OutingClass existingOutingClassEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClass.getId());

        if (existingOutingClassEntity != null) {
            com.woorinaru.core.model.management.administration.Event eventModel = outingClass.getEvent();
            if (eventModel != null) {
                Event existingEventEntity = em.find(Event.class, eventModel.getId());
                if (Objects.isNull(existingEventEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                } else {
                    existingOutingClassEntity.setEvent(existingEventEntity);
                }
            }

            // flush the existing collections
            Optional.ofNullable(existingOutingClassEntity.getStudents()).ifPresentOrElse(Collection::clear, () -> existingOutingClassEntity.setStudents(new ArrayList<>()));
            Optional.ofNullable(existingOutingClassEntity.getStaff()).ifPresentOrElse(Collection::clear, () -> existingOutingClassEntity.setStaff(new ArrayList<>()));
            Optional.ofNullable(existingOutingClassEntity.getResources()).ifPresentOrElse(Collection::clear, () -> existingOutingClassEntity.setResources(new ArrayList<>()));

            // re-populate
            for (com.woorinaru.core.model.user.Student studentModel : outingClass.getStudents()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(existingStudent)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    existingOutingClassEntity.addStudent(existingStudent);
                }
            }

            for (com.woorinaru.core.model.user.Staff staffModel : outingClass.getStaff()) {
                Staff existingStaff = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(existingStaff)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    existingOutingClassEntity.addStaff(existingStaff);
                }
            }

            for (com.woorinaru.core.model.management.administration.Resource resourceModel : outingClass.getResources()) {
                Resource existingResource = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(existingResource)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    existingOutingClassEntity.addResource(existingResource);
                }
            }
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be modified", outingClass.getId());
            throw new ResourceNotFoundException(String.format("Outing class with id: '%d' not found. Could not be modified", outingClass.getId()));
        }

    }

    @Override
    public List<OutingClass> getAll() {
        LOGGER.debug("Retrieving all outing classes");
        OutingClassMapper mapper = OutingClassMapper.MAPPER;

        TypedQuery<com.woorinaru.repository.sql.entity.management.administration.OutingClass> query = em.createQuery("SELECT o FROM OutingClass o", com.woorinaru.repository.sql.entity.management.administration.OutingClass.class);
        List<com.woorinaru.repository.sql.entity.management.administration.OutingClass> entityOutingClasses = query.getResultList();

        List<OutingClass> outingClasses = entityOutingClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d outing classes", outingClasses.size());

        return outingClasses;
    }
}
