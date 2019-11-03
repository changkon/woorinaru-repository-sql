package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.TutoringClassDao;
import woorinaru.core.exception.ReferenceNotFoundException;
import woorinaru.core.exception.ResourceNotFoundException;
import woorinaru.core.model.management.administration.TutoringClass;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapper.model.TutoringClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TutoringClassDaoImpl implements TutoringClassDao {

    private static final Logger LOGGER = LogManager.getLogger(TutoringClassDao.class);

    private EntityManager em;

    public TutoringClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(TutoringClass tutoringClass) {
        LOGGER.debug("Creating tutoring class");
        // Map file
        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = mapper.mapToEntity(tutoringClass);

        if (Objects.nonNull(tutoringClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : tutoringClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    tutoringClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(tutoringClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : tutoringClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(staffEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    tutoringClassEntity.addStaff(staffEntity);
                }
            }
        }

        if (Objects.nonNull(tutoringClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : tutoringClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(studentEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    tutoringClassEntity.addStudent(studentEntity);
                }
            }
        }

        if (Objects.nonNull(tutoringClass.getEvent())) {
            Event eventEntity = em.find(Event.class, tutoringClass.getEvent().getId());

            if (Objects.isNull(eventEntity)) {
                throw new ReferenceNotFoundException(String.format("Could not find event id: %d", tutoringClass.getEvent().getId()));
            } else {
                tutoringClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(tutoringClassEntity);
        em.flush();

        LOGGER.debug("Finished creating tutoring class");
        return tutoringClassEntity.getId();
    }

    @Override
    public TutoringClass get(int id) {
        LOGGER.debug("Retrieving a tutoring class with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, id);

        LOGGER.debug("Tutoring class with id: %d. Found: %s", id, tutoringClassEntity == null ? "True" : "False");

        if (Objects.isNull(tutoringClassEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find tutoring class with id: %d", id));
        }

        TutoringClassMapper mapper = TutoringClassMapper.MAPPER;

        TutoringClass tutoringClassModel = mapper.mapToModel(tutoringClassEntity);

        return tutoringClassModel;
    }

    @Override
    public void delete(TutoringClass tutoringClass) {
        LOGGER.debug("Deleting tutoring class with id: %d", tutoringClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.TutoringClass deleteTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClass.getId());

        if (deleteTutoringClassEntity != null) {
            em.remove(deleteTutoringClassEntity);
            LOGGER.debug("Tutoring class deleted");
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be deleted", tutoringClass.getId());
            throw new ResourceNotFoundException(String.format("Tutoring class with id: '%d' not found. Could not be deleted", tutoringClass.getId()));
        }
    }

    @Override
    public void modify(TutoringClass tutoringClass) {
        LOGGER.debug("Modifying tutoring class with id: %d", tutoringClass.getId());
        woorinaru.repository.sql.entity.management.administration.TutoringClass existingTutoringClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.TutoringClass.class, tutoringClass.getId());

        if (existingTutoringClassEntity != null) {
            woorinaru.core.model.management.administration.Event eventModel = tutoringClass.getEvent();
            if (eventModel != null) {
                Event existingEventEntity = em.find(Event.class, eventModel.getId());
                if (Objects.isNull(existingEventEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                } else {
                    existingTutoringClassEntity.setEvent(existingEventEntity);
                }
            }

            // flush the existing collections
            Optional.ofNullable(existingTutoringClassEntity.getStudents()).ifPresentOrElse(Collection::clear, () -> existingTutoringClassEntity.setStudents(new ArrayList<>()));
            Optional.ofNullable(existingTutoringClassEntity.getStaff()).ifPresentOrElse(Collection::clear, () -> existingTutoringClassEntity.setStaff(new ArrayList<>()));
            Optional.ofNullable(existingTutoringClassEntity.getResources()).ifPresentOrElse(Collection::clear, () -> existingTutoringClassEntity.setResources(new ArrayList<>()));

            // re-populate
            for (woorinaru.core.model.user.Student studentModel : tutoringClass.getStudents()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(existingStudent)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    existingTutoringClassEntity.addStudent(existingStudent);
                }
            }

            for (woorinaru.core.model.user.Staff staffModel : tutoringClass.getStaff()) {
                Staff existingStaff = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(existingStaff)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    existingTutoringClassEntity.addStaff(existingStaff);
                }
            }

            for (woorinaru.core.model.management.administration.Resource resourceModel : tutoringClass.getResources()) {
                Resource existingResource = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(existingResource)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    existingTutoringClassEntity.addResource(existingResource);
                }
            }
        } else {
            LOGGER.debug("Tutoring class with id: '%d' not found. Could not be modified", tutoringClass.getId());
            throw new ResourceNotFoundException(String.format("Tutoring class with id: '%d' not found. Could not be modified", tutoringClass.getId()));
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
