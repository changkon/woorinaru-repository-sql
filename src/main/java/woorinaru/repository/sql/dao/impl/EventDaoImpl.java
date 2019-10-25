package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.EventDao;
import woorinaru.core.model.management.administration.Event;
import woorinaru.repository.sql.adapter.EventAdapter;
import woorinaru.repository.sql.entity.management.administration.WooriClass;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.EventMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class EventDaoImpl implements EventDao {

    private static final Logger LOGGER = LogManager.getLogger(EventDao.class);

    private EntityManager em;

    public EventDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Event event) {
        LOGGER.debug("Creating an event");
        // Map file
        EventMapper mapper = EventMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.Event eventEntity = mapper.mapToEntity(event);

        em.getTransaction().begin();

        if (Objects.nonNull(event.getWooriClasses())) {
            for (woorinaru.core.model.management.administration.WooriClass wooriClassModel : event.getWooriClasses()) {
                WooriClass wooriClassEntity = em.find(WooriClass.class, wooriClassModel.getId());
                eventEntity.addWooriClass(wooriClassEntity);
            }
        }

        if (Objects.nonNull(event.getStudentReservations())) {
            for (woorinaru.core.model.user.Student studentModel : event.getStudentReservations()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                eventEntity.addStudentReservation(studentEntity);
            }
        }

        em.persist(eventEntity);
        em.getTransaction().commit();

        LOGGER.debug("Finished creating an event");
    }

    @Override
    public Optional<Event> get(int id) {
        LOGGER.debug("Retrieving an event with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(woorinaru.repository.sql.entity.management.administration.Event.class, id);

        LOGGER.debug("Event with id: %d. Found: %s", id, eventEntity == null ? "True" : "False");

        EventMapper mapper = Mappers.getMapper(EventMapper.class);

        Optional<Event> eventModel = Stream.ofNullable(eventEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return eventModel;
    }

    @Override
    public void delete(Event event) {
        LOGGER.debug("Deleting event with id: %d", event.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.Event deleteEventEntity = em.find(woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());

        if (deleteEventEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteEventEntity);
            em.getTransaction().commit();
            LOGGER.debug("Event deleted");
        } else {
            LOGGER.debug("Event with id: '%d' not found. Could not be deleted", event.getId());
        }

    }


    @Override
    public void modify(UpdateCommand<Event> updateCommand) {
        Event eventModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying event with id: %d", eventModel.getId());

        woorinaru.repository.sql.entity.management.administration.Event existingEventEntity = em.find(woorinaru.repository.sql.entity.management.administration.Event.class, eventModel.getId());

        if (existingEventEntity != null) {
            Event eventAdapter = new EventAdapter(existingEventEntity, em);
            updateCommand.setReceiver(eventAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Event with id: '%d' not found. Could not be modified", eventModel.getId());
        }

    }

    @Override
    public List<Event> getAll() {
        LOGGER.debug("Retrieving all events");
        EventMapper mapper = EventMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.Event> query = em.createQuery("SELECT e FROM Event e", woorinaru.repository.sql.entity.management.administration.Event.class);
        List<woorinaru.repository.sql.entity.management.administration.Event> eventEntities = query.getResultList();

        List<Event> events = eventEntities.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d events", events.size());

        return events;
    }
}
