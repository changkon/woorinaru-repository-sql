package com.woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import com.woorinaru.core.dao.spi.EventDao;
import com.woorinaru.core.exception.ReferenceNotFoundException;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.management.administration.Event;
import com.woorinaru.repository.sql.entity.management.administration.WooriClass;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.mapper.model.EventMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class EventDaoImpl implements EventDao {

    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);

    private EntityManager em;

    public EventDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(Event event) {
        LOGGER.debug("Creating an event");
        // Map file
        EventMapper mapper = EventMapper.MAPPER;
        com.woorinaru.repository.sql.entity.management.administration.Event eventEntity = mapper.mapToEntity(event);

        if (Objects.nonNull(event.getWooriClasses())) {
            for (com.woorinaru.core.model.management.administration.WooriClass wooriClassModel : event.getWooriClasses()) {
                WooriClass wooriClassEntity = em.find(WooriClass.class, wooriClassModel.getId());
                if (Objects.isNull(wooriClassEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not woori class id: %d", wooriClassModel.getId()));
                } else {
                    eventEntity.addWooriClass(wooriClassEntity);
                }
            }
        }

        if (Objects.nonNull(event.getStudentReservations())) {
            for (com.woorinaru.core.model.user.Student studentModel : event.getStudentReservations()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                if (Objects.isNull(studentEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    eventEntity.addStudentReservation(studentEntity);
                }
            }
        }

        em.persist(eventEntity);
        em.flush();

        LOGGER.debug("Finished creating an event");
        return eventEntity.getId();
    }

    @Override
    public Event get(int id) {
        LOGGER.debug("Retrieving an event with id: %d", id);

        com.woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.Event.class, id);

        LOGGER.debug("Event with id: %d. Found: %s", id, eventEntity == null ? "True" : "False");

        if (Objects.isNull(eventEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find event id: %d", id));
        }

        EventMapper mapper = Mappers.getMapper(EventMapper.class);

        Event eventModel = mapper.mapToModel(eventEntity);

        return eventModel;
    }

    @Override
    public void delete(Event event) {
        LOGGER.debug("Deleting event with id: %d", event.getId());

        // Map file
        com.woorinaru.repository.sql.entity.management.administration.Event deleteEventEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());

        if (deleteEventEntity != null) {
            em.remove(deleteEventEntity);
            LOGGER.debug("Event deleted");
        } else {
            LOGGER.debug("Event with id: '%d' not found. Could not be deleted", event.getId());
            throw new ResourceNotFoundException(String.format("Event with id: '%d' not found. Could not be deleted", event.getId()));
        }

    }


    @Override
    public void modify(Event event) {
        LOGGER.debug("Modifying event with id: %d", event.getId());

        com.woorinaru.repository.sql.entity.management.administration.Event existingEventEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());

        if (existingEventEntity != null) {
            existingEventEntity.setStartDateTime(event.getStartDateTime());
            existingEventEntity.setEndDateTime(event.getEndDateTime());
            existingEventEntity.setDescription(event.getDescription());
            existingEventEntity.setAddress(event.getAddress());

            // flush existing collections
            Optional.ofNullable(existingEventEntity.getStudentReservations()).ifPresentOrElse(Collection::clear, () -> existingEventEntity.setStudentReservations(new ArrayList<>()));
            Optional.ofNullable(existingEventEntity.getWooriClasses()).ifPresentOrElse(Collection::clear, () -> existingEventEntity.setWooriClasses(new ArrayList<>()));

            // re-populate
            for (com.woorinaru.core.model.user.Student studentModel : event.getStudentReservations()) {
                Student existingStudent = em.find(Student.class, studentModel.getId());

                if (Objects.isNull(existingStudent)) {
                    throw new ReferenceNotFoundException(String.format("Could not find student id: %d", studentModel.getId()));
                } else {
                    existingEventEntity.addStudentReservation(existingStudent);
                }
            }

            for (com.woorinaru.core.model.management.administration.WooriClass wooriClassModel : event.getWooriClasses()) {
                WooriClass existingWooriClass = em.find(WooriClass.class, wooriClassModel.getId());
                if (Objects.isNull(existingWooriClass)) {
                    throw new ReferenceNotFoundException(String.format("Could not woori class id: %d", wooriClassModel.getId()));
                } else {
                    existingEventEntity.addWooriClass(existingWooriClass);
                }
            }

        } else {
            LOGGER.debug("Event with id: '%d' not found. Could not be modified", event.getId());
            throw new ResourceNotFoundException(String.format("Event with id: '%d' not found. Could not be modified", event.getId()));
        }

    }

    @Override
    public List<Event> getAll() {
        LOGGER.debug("Retrieving all events");
        EventMapper mapper = EventMapper.MAPPER;

        TypedQuery<com.woorinaru.repository.sql.entity.management.administration.Event> query = em.createQuery("SELECT e FROM Event e", com.woorinaru.repository.sql.entity.management.administration.Event.class);
        List<com.woorinaru.repository.sql.entity.management.administration.Event> eventEntities = query.getResultList();

        List<Event> events = eventEntities.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d events", events.size());

        return events;
    }
}
