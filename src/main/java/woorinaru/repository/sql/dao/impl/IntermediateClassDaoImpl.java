package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.IntermediateClassDao;
import woorinaru.core.model.management.administration.IntermediateClass;
import woorinaru.repository.sql.adapter.IntermediateClassAdapter;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.IntermediateClassMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class IntermediateClassDaoImpl implements IntermediateClassDao {

    private static final Logger LOGGER = LogManager.getLogger(IntermediateClassDao.class);

    private EntityManager em;

    public IntermediateClassDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(IntermediateClass intermediateClass) {
        LOGGER.debug("Creating intermediate class");
        // Map file
        IntermediateClassMapper mapper = IntermediateClassMapper.MAPPER;
        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = mapper.mapToEntity(intermediateClass);

        em.getTransaction().begin();

        if (Objects.nonNull(intermediateClass.getResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : intermediateClass.getResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    intermediateClassEntity.addResource(resourceEntity);
                }
            }
        }

        if (Objects.nonNull(intermediateClass.getStaff())) {
            for (woorinaru.core.model.user.Staff staffModel : intermediateClass.getStaff()) {
                Staff staffEntity = em.find(Staff.class, staffModel.getId());
                intermediateClassEntity.addStaff(staffEntity);
            }
        }

        if (Objects.nonNull(intermediateClass.getStudents())) {
            for (woorinaru.core.model.user.Student studentModel : intermediateClass.getStudents()) {
                Student studentEntity = em.find(Student.class, studentModel.getId());
                intermediateClassEntity.addStudent(studentEntity);
            }
        }

        if (Objects.nonNull(intermediateClass.getEvent())) {
            Event eventEntity = em.find(Event.class, intermediateClass.getEvent().getId());

            if (Objects.nonNull(eventEntity)) {
                intermediateClassEntity.setEvent(eventEntity);
            }
        }

        em.persist(intermediateClassEntity);
        em.getTransaction().commit();

        LOGGER.debug("Finished creating intermediate class");
    }

    @Override
    public Optional<IntermediateClass> get(int id) {
        LOGGER.debug("Retrieving a intermediate class with id: %d", id);

        woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, id);

        LOGGER.debug("Intermediate class with id: %d. Found: %s", id, intermediateClassEntity == null ? "True" : "False");

        IntermediateClassMapper mapper = IntermediateClassMapper.MAPPER;

        Optional<IntermediateClass> intermediateClassModel = Stream.ofNullable(intermediateClassEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return intermediateClassModel;
    }

    @Override
    public void delete(IntermediateClass intermediateClass) {
        LOGGER.debug("Deleting intermediate class with id: %d", intermediateClass.getId());

        // Map file
        woorinaru.repository.sql.entity.management.administration.IntermediateClass deleteIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClass.getId());

        if (deleteIntermediateClassEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteIntermediateClassEntity);
            em.getTransaction().commit();
            LOGGER.debug("Intermediate class deleted");
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be deleted", intermediateClass.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<IntermediateClass> updateCommand) {
        IntermediateClass intermediateClassModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying intermediate class with id: %d", intermediateClassModel.getId());

        woorinaru.repository.sql.entity.management.administration.IntermediateClass existingIntermediateClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.IntermediateClass.class, intermediateClassModel.getId());

        if (existingIntermediateClassEntity != null) {
            IntermediateClass intermediateClassAdapter = new IntermediateClassAdapter(existingIntermediateClassEntity, em);
            updateCommand.setReceiver(intermediateClassAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Intermediate class with id: '%d' not found. Could not be modified", intermediateClassModel.getId());
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
