package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.AdminDao;
import woorinaru.core.model.user.Admin;
import woorinaru.repository.sql.adapter.AdminAdapter;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.model.AdminMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class AdminDaoImpl implements AdminDao {

    private static final Logger LOGGER = LogManager.getLogger(AdminDao.class);

    private EntityManager em;

    public AdminDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Admin admin) {
        LOGGER.debug("Creating an admin resource");
        // Map file
        AdminMapper mapper = Mappers.getMapper(AdminMapper.class);
        woorinaru.repository.sql.entity.user.Admin adminEntity = mapper.mapToEntity(admin);

        // Set resources if present
        if (Objects.nonNull(admin.getFavouriteResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : admin.getFavouriteResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    adminEntity.addFavouriteResource(resourceEntity);
                }
            }
        }

        em.persist(adminEntity);

        LOGGER.debug("Finished creating an admin resource");
    }

    @Override
    public Optional<Admin> get(int id) {
        LOGGER.debug("Retrieving an admin resource with id: %d", id);
        woorinaru.repository.sql.entity.user.Admin adminEntity = em.find(woorinaru.repository.sql.entity.user.Admin.class, id);

        LOGGER.debug("Admin with id: %d. Found: %s", id, adminEntity == null ? "True" : "False");

        AdminMapper mapper = Mappers.getMapper(AdminMapper.class);

        Optional<Admin> adminModel = Stream.ofNullable(adminEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return adminModel;
    }

    @Override
    public void delete(Admin admin) {
        LOGGER.debug("Deleting admin with id: %d", admin.getId());

        // Map file
        woorinaru.repository.sql.entity.user.Admin deleteAdminEntity = em.find(woorinaru.repository.sql.entity.user.Admin.class, admin.getId());

        if (deleteAdminEntity != null) {
            em.remove(deleteAdminEntity);
            LOGGER.debug("Admin deleted");
        } else {
            LOGGER.debug("Admin with id: '%d' not found. Could not be deleted", admin.getId());
        }
    }

    @Override
    public void modify(Admin admin) {
        LOGGER.debug("Modifying admin with id: %d", admin.getId());
        woorinaru.repository.sql.entity.user.Admin existingAdminEntity = em.find(woorinaru.repository.sql.entity.user.Admin.class, admin.getId());

        if (existingAdminEntity != null) {
            existingAdminEntity.setName(admin.getName());
            existingAdminEntity.setNationality(admin.getNationality());
            existingAdminEntity.setEmail(admin.getEmail());
            existingAdminEntity.setSignUpDateTime(admin.getSignUpDateTime());
            existingAdminEntity.setFavouriteResources(new ArrayList<>());

            // re add resources
            for (woorinaru.core.model.management.administration.Resource resourceModel : admin.getFavouriteResources()) {
                int resourceModelId = resourceModel.getId();
                Resource existingResourceEntity = em.find(Resource.class, resourceModelId);
                existingAdminEntity.addFavouriteResource(existingResourceEntity);
            }
            em.merge(existingAdminEntity);
            LOGGER.debug("Finished modifying admin");
        } else {
            LOGGER.debug("Could not find admin with id: %d. Did not modify", admin.getId());
        }
    }

    @Override
    public List<Admin> getAll() {
        LOGGER.debug("Retrieving all admins");
        AdminMapper mapper = Mappers.getMapper(AdminMapper.class);

        TypedQuery<woorinaru.repository.sql.entity.user.Admin> query = em.createQuery("SELECT a FROM Admin a", woorinaru.repository.sql.entity.user.Admin.class);
        List<woorinaru.repository.sql.entity.user.Admin> entityAdmins = query.getResultList();

        List<Admin> admins = entityAdmins.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d admins", admins.size());

        return admins;
    }
}
