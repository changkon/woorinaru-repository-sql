package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.ResourceDao;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.repository.sql.adapter.ResourceAdapter;
import woorinaru.repository.sql.mapping.model.ResourceMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class ResourceDaoImpl implements ResourceDao {

    private static final Logger LOGGER = LogManager.getLogger(ResourceDao.class);

    private EntityManager em;

    public ResourceDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Resource resource) {
        LOGGER.debug("Creating a resource");
        // Map file
        ResourceMapper mapper = ResourceMapper.MAPPER;
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = mapper.mapToEntity(resource);

        em.persist(resourceEntity);

        LOGGER.debug("Finished creating a resource");
    }

    @Override
    public Optional<Resource> get(int id) {
        LOGGER.debug("Retrieving a resource with id: %d", id);

        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, id);

        LOGGER.debug("Resource with id: %d. Found: %s", id, resourceEntity == null ? "True" : "False");

        ResourceMapper mapper = ResourceMapper.MAPPER;

        Optional<Resource> resourceModel = Stream.ofNullable(resourceEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return resourceModel;
    }

    @Override
    public void delete(Resource resource) {
        LOGGER.debug("Deleting resource with id: %d", resource.getId());

        // Map file
        woorinaru.repository.sql.entity.resource.Resource deleteResourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());

        if (deleteResourceEntity != null) {
            em.remove(deleteResourceEntity);
            LOGGER.debug("Resource deleted");
        } else {
            LOGGER.debug("Resource with id: '%d' not found. Could not be deleted", resource.getId());
        }
    }

    @Override
    public void modify(UpdateCommand<Resource> updateCommand) {
        Resource resourceModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying resource with id: %d", resourceModel.getId());

        woorinaru.repository.sql.entity.resource.Resource existingResourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resourceModel.getId());

        if (existingResourceEntity != null) {
            Resource resourceAdapter = new ResourceAdapter(existingResourceEntity);
            updateCommand.setReceiver(resourceAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Resource with id: '%d' not found. Could not be modified", resourceModel.getId());
        }
    }

    @Override
    public List<Resource> getAll() {
        LOGGER.debug("Retrieving all resources");
        ResourceMapper mapper = ResourceMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.resource.Resource> query = em.createQuery("SELECT r FROM Resource r", woorinaru.repository.sql.entity.resource.Resource.class);
        List<woorinaru.repository.sql.entity.resource.Resource> entityResources = query.getResultList();

        List<Resource> resources = entityResources.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d resource", resources.size());
        return resources;
    }
}
