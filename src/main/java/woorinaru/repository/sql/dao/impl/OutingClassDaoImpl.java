package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.OutingClassDao;
import woorinaru.core.model.management.administration.OutingClass;
import woorinaru.repository.sql.adapter.OutingClassAdapter;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.OutingClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class OutingClassDaoImpl implements OutingClassDao {

    private static final Logger LOGGER = LogManager.getLogger(OutingClassDao.class);

    private EntityManager em;

    public OutingClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(OutingClass outingClass) {
        LOGGER.debug("Creating an outing class");
        // Map file
        OutingClassMapper mapper = OutingClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = mapper.mapToEntity(outingClass);

        if (Objects.nonNull(outingClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : outingClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    outingClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(outingClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : outingClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                outingClassEntity.addStaff(staffEntity);
            }
        }

        if (Objects.nonNull(outingClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : outingClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                outingClassEntity.addStudent(studentEntity);
            }
        }

        if (Objects.nonNull(outingClass.getEvent())) {
            Event eventEntity = em.find(Event.class, outingClass.getEvent().getId());

            if (Objects.nonNull(eventEntity)) {
                outingClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(outingClassEntity);

        LOGGER.debug("Finished creating an outing class");
    }

    @Override
    public Optional<OutingClass> get(int id) {
        LOGGER.debug("Retrieving an outing class with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, id);

        LOGGER.debug("Outing class with id: %d. Found: %s", id, outingClassEntity == null ? "True" : "False");

        OutingClassMapper mapper = OutingClassMapper.MAPPER;

        Optional<OutingClass> outingClassModel = Stream.ofNullable(outingClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return outingClassModel;
    }

    @Override
    public void delete(OutingClass outingClass) {
        LOGGER.debug("Deleting outing class with id: %d", outingClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.OutingClass deleteOutingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClass.getId());

        if (deleteOutingClassEntity != null) {
            em.remove(deleteOutingClassEntity);
            LOGGER.debug("Outing class deleted");
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be deleted", outingClass.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<OutingClass> updateCommand) {
        OutingClass outingClassModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying outing class with id: %d", outingClassModel.getId());

        woorinaru.repository.sql.entity.management.administration.OutingClass existingOutingClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.OutingClass.class, outingClassModel.getId());

        if (existingOutingClassEntity != null) {
            OutingClass outingClassAdapter = new OutingClassAdapter(existingOutingClassEntity, em);
            updateCommand.setReceiver(outingClassAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Outing class with id: '%d' not found. Could not be modified", outingClassModel.getId());
        }

    }

    @Override
    public List<OutingClass> getAll() {
        LOGGER.debug("Retrieving all outing classes");
        OutingClassMapper mapper = OutingClassMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.management.administration.OutingClass> query = em.createQuery("SELECT o FROM OutingClass o", woorinaru.repository.sql.entity.management.administration.OutingClass.class);
        List<woorinaru.repository.sql.entity.management.administration.OutingClass> entityOutingClasses = query.getResultList();

        List<woorinaru.core.model.management.administration.OutingClass> outingClasses = entityOutingClasses.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d outing classes", outingClasses.size());

        return outingClasses;
    }
}
