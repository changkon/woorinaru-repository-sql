package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.ResourceDao;
import woorinaru.core.exception.ResourceNotFoundException;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.repository.sql.mapper.model.ResourceMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceDaoImpl implements ResourceDao {

    private static final Logger LOGGER = LogManager.getLogger(ResourceDao.class);

    private EntityManager em;

    public ResourceDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(Resource resource) {
        LOGGER.debug("Creating a resource");
        // Map file
        ResourceMapper mapper = ResourceMapper.MAPPER;
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = mapper.mapToEntity(resource);

        em.persist(resourceEntity);
        em.flush();

        LOGGER.debug("Finished creating a resource");
        return resourceEntity.getId();
    }

    @Override
    public Resource get(int id) {
        LOGGER.debug("Retrieving a resource with id: %d", id);

        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, id);

        LOGGER.debug("Resource with id: %d. Found: %s", id, resourceEntity == null ? "True" : "False");

        if (Objects.isNull(resourceEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find resource with id: %d", id));
        }

        ResourceMapper mapper = ResourceMapper.MAPPER;
        Resource resourceModel = mapper.mapToModel(resourceEntity);

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
            throw new ResourceNotFoundException(String.format("Resource with id: '%d' not found. Could not be deleted", resource.getId()));
        }
    }

    @Override
    public void modify(Resource resource) {
        LOGGER.debug("Modifying resource with id: %d", resource.getId());
        woorinaru.repository.sql.entity.resource.Resource existingResourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());

        if (existingResourceEntity != null) {
            existingResourceEntity.setDescription(resource.getDescription());
            existingResourceEntity.setResource(resource.getResource());
            em.merge(existingResourceEntity);
            LOGGER.debug("Completed modifying resource with id: %d", resource.getId());
        } else {
            LOGGER.debug("Could not find resource with id: %d. Did not modify", resource.getId());
            throw new ResourceNotFoundException(String.format("Could not find resource with id: %d. Did not modify", resource.getId()));
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
