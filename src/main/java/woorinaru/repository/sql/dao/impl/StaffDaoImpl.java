package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.StaffDao;
import woorinaru.core.model.user.Staff;
import woorinaru.repository.sql.adapter.StaffAdapter;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.model.StaffMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class StaffDaoImpl implements StaffDao {

    private static final Logger LOGGER = LogManager.getLogger(StaffDao.class);

    private EntityManager em;

    public StaffDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Staff staff) {
        LOGGER.debug("Creating a staff resource");
        // Map file
        StaffMapper mapper = StaffMapper.MAPPER;
        woorinaru.repository.sql.entity.user.Staff staffEntity = mapper.mapToEntity(staff);

        em.getTransaction().begin();

        // Set resources if present
        if (Objects.nonNull(staff.getFavouriteResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : staff.getFavouriteResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    staffEntity.addFavouriteResource(resourceEntity);
                }
            }
        }

        em.persist(staffEntity);
        em.getTransaction().commit();

        LOGGER.debug("Finished creating a staff resource");
    }

    @Override
    public Optional<Staff> get(int id) {
        LOGGER.debug("Retrieving a staff resource with id: %d", id);
        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, id);

        LOGGER.debug("Staff with id: %d. Found: %s", id, staffEntity == null ? "True" : "False");

        StaffMapper mapper = StaffMapper.MAPPER;

        Optional<Staff> staffModel = Stream.ofNullable(staffEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return staffModel;
    }

    @Override
    public void delete(Staff staff) {
        LOGGER.debug("Deleting staff with id: %d", staff.getId());

        // Map file
        woorinaru.repository.sql.entity.user.Staff deleteStaffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());

        if (deleteStaffEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteStaffEntity);
            em.getTransaction().commit();
            LOGGER.debug("Staff deleted");
        } else {
            LOGGER.debug("Staff with id: '%d' not found. Could not be deleted", staff.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<Staff> updateCommand) {
        Staff staffModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying staff with id: %d", staffModel.getId());
        woorinaru.repository.sql.entity.user.Staff existingStaffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staffModel.getId());

        if (existingStaffEntity != null) {
            Staff staffAdapter = new StaffAdapter(existingStaffEntity, em);
            updateCommand.setReceiver(staffAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Staff with id: '%d' not found. Could not be modified", staffModel.getId());
        }
    }

    @Override
    public List<Staff> getAll() {
        LOGGER.debug("Retrieving all staff members");
        StaffMapper mapper = StaffMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.user.Staff> query = em.createQuery("SELECT s FROM Staff s", woorinaru.repository.sql.entity.user.Staff.class);
        List<woorinaru.repository.sql.entity.user.Staff> entityStaff = query.getResultList();

        List<Staff> staff = entityStaff.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d staff", staff.size());
        return staff;
    }
}
