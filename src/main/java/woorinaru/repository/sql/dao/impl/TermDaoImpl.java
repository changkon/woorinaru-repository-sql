package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.TermDao;
import woorinaru.core.exception.ReferenceNotFoundException;
import woorinaru.core.exception.ResourceNotFoundException;
import woorinaru.core.model.management.administration.Term;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.mapper.model.TermMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class TermDaoImpl implements TermDao {

    private static final Logger LOGGER = LogManager.getLogger(TermDao.class);

    private EntityManager em;

    public TermDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(Term term) {
        LOGGER.debug("Creating a term resource");
        // Map file
        TermMapper mapper = TermMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.Term termEntity = mapper.mapToEntity(term);

        // Add staff if present
        if (Objects.nonNull(term.getStaffMembers())) {
            for (woorinaru.core.model.user.Staff staffModel : term.getStaffMembers()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                if (Objects.isNull(staffEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                } else {
                    termEntity.addStaff(staffEntity);
                }
            }
        }

        if (Objects.nonNull(term.getEvents())) {
            for (woorinaru.core.model.management.administration.Event eventModel : term.getEvents()) {
                Event eventEntity = em.find(Event.class, eventModel.getId());
                if (Objects.isNull(eventEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                } else {
                    termEntity.addEvent(eventEntity);
                }
            }
        }

        em.persist(termEntity);
        em.flush();

        LOGGER.debug("Finished creating a term resource");
        return termEntity.getId();
    }

    @Override
    public Term get(int id) {
        LOGGER.debug("Retrieving a term with id: %d", id);
        woorinaru.repository.sql.entity.management.administration.Term termEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, id);

        LOGGER.debug("Term with id: %d. Found: %s", id, termEntity == null ? "True" : "False");

        if (Objects.isNull(termEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find term id: %d", id));
        }

        TermMapper mapper = TermMapper.MAPPER;

        Term termModel = mapper.mapToModel(termEntity);

        return termModel;
    }

    @Override
    public void delete(Term term) {
        LOGGER.debug("Deleting term with id: %d", term.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.Term deleteTermEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, term.getId());

        if (deleteTermEntity != null) {
            em.remove(deleteTermEntity);
            LOGGER.debug("Term deleted");
        } else {
            LOGGER.debug("Term with id: '%d' not found. Could not be deleted", term.getId());
            throw new ResourceNotFoundException(String.format("Term with id: '%d' not found. Could not be deleted", term.getId()));
        }
    }

    @Override
    public void modify(Term term) {
        LOGGER.debug("Modifying term with id: %d", term.getId());
        woorinaru.repository.sql.entity.management.administration.Term existingTermEntity = em.find(woorinaru.repository.sql.entity.management.administration.Term.class, term.getId());

        if (existingTermEntity != null) {
            existingTermEntity.setTerm(term.getTerm());
            existingTermEntity.setStartDate(term.getStartDate());
            existingTermEntity.setEndDate(term.getEndDate());

            // flush existing collections
            Optional.ofNullable(existingTermEntity.getEvents()).ifPresentOrElse(Collection::clear, () -> existingTermEntity.setEvents(new ArrayList<>()));
            Optional.ofNullable(existingTermEntity.getStaffMembers()).ifPresentOrElse(Collection::clear, () -> existingTermEntity.setStaffMembers(new ArrayList<>()));

            // re-populate
            if (Objects.nonNull(term.getEvents())) {
                for (woorinaru.core.model.management.administration.Event eventModel : term.getEvents()) {
                    Event existingEvent = em.find(Event.class, eventModel.getId());
                    if (Objects.isNull(existingEvent)) {
                        throw new ReferenceNotFoundException(String.format("Could not find event id: %d", eventModel.getId()));
                    } else {
                        existingTermEntity.addEvent(existingEvent);
                    }
                }
            }

            if (Objects.nonNull(term.getStaffMembers())) {
                for (woorinaru.core.model.user.Staff staffModel : term.getStaffMembers()) {
                    Staff existingStaffMember = em.find(Staff.class, staffModel.getId());

                    if (Objects.isNull(existingStaffMember)) {
                        throw new ReferenceNotFoundException(String.format("Could not find staff id: %d", staffModel.getId()));
                    } else {
                        existingTermEntity.addStaff(existingStaffMember);
                    }
                }
            }
        } else {
            LOGGER.debug("Term with id: '%d' not found. Could not be modified", term.getId());
            throw new ResourceNotFoundException(String.format("Term with id: '%d' not found. Could not be modified", term.getId()));
        }
    }

    @Override
    public List<Term> getAll() {
        LOGGER.debug("Retrieving all terms");
        TermMapper mapper = TermMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.Term> query = em.createQuery("SELECT t FROM Term t", woorinaru.repository.sql.entity.management.administration.Term.class);
        List<woorinaru.repository.sql.entity.management.administration.Term> termEntities = query.getResultList();

        List<Term> terms = termEntities.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d terms", terms.size());
        return terms;
    }
}
