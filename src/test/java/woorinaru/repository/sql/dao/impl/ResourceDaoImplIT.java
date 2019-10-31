package woorinaru.repository.sql.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import woorinaru.core.dao.spi.ResourceDao;
import woorinaru.core.exception.ResourceNotFoundException;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.repository.sql.dao.helper.DatabaseContainerRule;
import woorinaru.repository.sql.mapper.model.ResourceMapper;
import woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(DatabaseContainerRule.class)
public class ResourceDaoImplIT extends AbstractContainerDatabaseIT {

    @Test
    @DisplayName("Test create resource")
    public void testCreateResource() {
        // GIVEN
        Resource resourceModel = new Resource();
        resourceModel.setDescription("resource description");
        resourceModel.setResource("resource".getBytes());

        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        ResourceDao resourceDao = new ResourceDaoImpl(daoEm);

        // WHEN
        executeInTransaction().accept(daoEm, () -> {
            int generatedId = resourceDao.create(resourceModel);
            assertThat(generatedId).isEqualTo(1);
        });

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.resource.Resource> query = em.createQuery("SELECT r FROM Resource r", woorinaru.repository.sql.entity.resource.Resource.class);
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = query.getSingleResult();

        assertThat(resourceEntity).isNotNull();
        assertThat(resourceEntity.getId()).isEqualTo(1);
        assertThat(resourceEntity.getDescription()).isEqualTo("resource description");
        assertThat(resourceEntity.getResource()).isEqualTo("resource".getBytes());
    }

    @Test
    @DisplayName("Test delete resource")
    public void testDeleteResource() {
        // GIVEN
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = new woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity.setDescription("resource entity description");
        resourceEntity.setResource("resource".getBytes());

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(resourceEntity);
        em.getTransaction().commit();

        TypedQuery<woorinaru.repository.sql.entity.resource.Resource> query1 = em.createQuery("SELECT r FROM Resource r", woorinaru.repository.sql.entity.resource.Resource.class);
        assertThat(query1.getResultList()).hasSize(1);

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        ResourceDao resourceDao = new ResourceDaoImpl(daoEm);
        ResourceMapper mapper = ResourceMapper.MAPPER;
        executeInTransaction().accept(daoEm, () -> resourceDao.delete(mapper.mapToModel(resourceEntity)));

        // THEN
        TypedQuery<woorinaru.repository.sql.entity.resource.Resource> query2 = em.createQuery("SELECT r FROM Resource r", woorinaru.repository.sql.entity.resource.Resource.class);
        assertThat(query2.getResultList()).isEmpty();

        em.close();
    }

    // TODO modify resource

    @Test
    @DisplayName("Test get resource")
    public void testGetResource() {
        // GIVEN
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = new woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity.setDescription("resource description");
        resourceEntity.setResource("random resource".getBytes());

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(resourceEntity);
        em.getTransaction().commit();
        em.close();

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        ResourceDao resourceDao = new ResourceDaoImpl(daoEm);
        Resource resourceModel = resourceDao.get(1);

        // THEN
        assertThat(resourceModel.getId()).isEqualTo(1);
        assertThat(resourceModel.getDescription()).isEqualTo("resource description");
        assertThat(resourceModel.getResource()).isEqualTo("random resource".getBytes());
    }

    @Test
    @DisplayName("Test get non-existent id returns empty")
    public void testGetNonExistentResourceIsEmpty() {
        // GIVEN
        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        ResourceDao resourceDao = new ResourceDaoImpl(daoEm);
        Throwable thrown = catchThrowable(() -> resourceDao.get(100));

        // THEN
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
        assertThat(thrown).hasMessage("Could not find resource with id: 100");
    }

    @Test
    @DisplayName("Test get all resources")
    public void testGetAllResources() {
        // GIVEN
        woorinaru.repository.sql.entity.resource.Resource resourceEntity1 = new woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity1.setDescription("description 1");
        resourceEntity1.setResource("resource 1".getBytes());

        woorinaru.repository.sql.entity.resource.Resource resourceEntity2 = new woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity2.setDescription("description 2");
        resourceEntity2.setResource("resource 2".getBytes());

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(resourceEntity1);
        em.persist(resourceEntity2);
        em.getTransaction().commit();
        em.close();

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        ResourceDao resourceDao = new ResourceDaoImpl(daoEm);
        List<Resource> resources = resourceDao.getAll();

        // THEN
        assertThat(resources).hasSize(2);
        Resource resourceModel1 = resources.get(0);
        Resource resourceModel2 = resources.get(1);

        assertThat(resourceModel1.getId()).isEqualTo(1);
        assertThat(resourceModel1.getDescription()).isEqualTo("description 1");
        assertThat(resourceModel1.getResource()).isEqualTo("resource 1".getBytes());

        assertThat(resourceModel2.getId()).isEqualTo(2);
        assertThat(resourceModel2.getDescription()).isEqualTo("description 2");
        assertThat(resourceModel2.getResource()).isEqualTo("resource 2".getBytes());
    }
}
