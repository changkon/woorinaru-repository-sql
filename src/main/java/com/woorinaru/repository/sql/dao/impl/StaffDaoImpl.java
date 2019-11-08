package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.StaffDao;
import com.woorinaru.core.exception.ReferenceNotFoundException;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.user.Staff;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.mapper.model.StaffMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class StaffDaoImpl implements StaffDao {

    private static final Logger LOGGER = LogManager.getLogger(StaffDao.class);

    private EntityManager em;

    public StaffDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(Staff staff) {
        LOGGER.debug("Creating a staff resource");
        // Map file
        StaffMapper mapper = StaffMapper.MAPPER;
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = mapper.mapToEntity(staff);

        // Set resources if present
        if (Objects.nonNull(staff.getFavouriteResources())) {
            for (com.woorinaru.core.model.management.administration.Resource resourceModel : staff.getFavouriteResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    staffEntity.addFavouriteResource(resourceEntity);
                }
            }
        }

        em.persist(staffEntity);
        em.flush();

        LOGGER.debug("Finished creating a staff resource");
        return staffEntity.getId();
    }

    @Override
    public Staff get(int id) {
        LOGGER.debug("Retrieving a staff resource with id: %d", id);
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(com.woorinaru.repository.sql.entity.user.Staff.class, id);

        LOGGER.debug("Staff with id: %d. Found: %s", id, staffEntity == null ? "True" : "False");

        if (Objects.isNull(staffEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find staff with id: %d", id));
        }

        StaffMapper mapper = StaffMapper.MAPPER;

        Staff staffModel = mapper.mapToModel(staffEntity);

        return staffModel;
    }

    @Override
    public void delete(Staff staff) {
        LOGGER.debug("Deleting staff with id: %d", staff.getId());

        // Map file
        com.woorinaru.repository.sql.entity.user.Staff deleteStaffEntity = em.find(com.woorinaru.repository.sql.entity.user.Staff.class, staff.getId());

        if (deleteStaffEntity != null) {
            em.remove(deleteStaffEntity);
            LOGGER.debug("Staff deleted");
        } else {
            LOGGER.debug("Staff with id: '%d' not found. Could not be deleted", staff.getId());
            throw new ResourceNotFoundException(String.format("Staff with id: '%d' not found. Could not be deleted", staff.getId()));
        }
    }

    @Override
    public void modify(Staff staff) {
        LOGGER.debug("Modifying staff with id: %d", staff.getId());
        com.woorinaru.repository.sql.entity.user.Staff existingStaffEntity = em.find(com.woorinaru.repository.sql.entity.user.Staff.class, staff.getId());

        if (existingStaffEntity != null) {
            existingStaffEntity.setName(staff.getName());
            existingStaffEntity.setNationality(staff.getNationality());
            existingStaffEntity.setEmail(staff.getEmail());
            existingStaffEntity.setSignUpDateTime(staff.getSignUpDateTime());
            Optional.ofNullable(existingStaffEntity.getFavouriteResources()).ifPresentOrElse(Collection::clear, () -> existingStaffEntity.setFavouriteResources(new ArrayList<>()));

            // re add resources
            if (Objects.nonNull(staff.getFavouriteResources())) {
                for (com.woorinaru.core.model.management.administration.Resource resourceModel : staff.getFavouriteResources()) {
                    Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                    if (Objects.isNull(resourceEntity)) {
                        throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                    } else {
                        existingStaffEntity.addFavouriteResource(resourceEntity);
                    }
                }
            }

            existingStaffEntity.setTeam(StaffMapper.MAPPER.mapTeamModelToTeamEntity(staff.getTeam()));
            existingStaffEntity.setStaffRole(StaffMapper.MAPPER.mapStaffRoleModelToStaffRoleEntity(staff.getStaffRole()));

            em.merge(existingStaffEntity);
            LOGGER.debug("Finished modifying staff");
        } else {
            LOGGER.debug("Could not find staff with id: %d. Did not modify", staff.getId());
            throw new ResourceNotFoundException(String.format("Could not find staff with id: %d. Did not modify", staff.getId()));
        }
    }

    @Override
    public List<Staff> getAll() {
        LOGGER.debug("Retrieving all staff members");
        StaffMapper mapper = StaffMapper.MAPPER;

        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em.createQuery("SELECT s FROM Staff s", com.woorinaru.repository.sql.entity.user.Staff.class);
        List<com.woorinaru.repository.sql.entity.user.Staff> entityStaff = query.getResultList();

        List<Staff> staff = entityStaff.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d staff", staff.size());
        return staff;
    }
}
