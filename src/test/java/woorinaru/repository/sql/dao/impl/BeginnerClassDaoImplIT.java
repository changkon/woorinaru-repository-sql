package woorinaru.repository.sql.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import woorinaru.core.dao.spi.BeginnerClassDao;
import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.repository.sql.dao.helper.DatabaseContainerRule;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.model.*;
import woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DatabaseContainerRule.class)
public class BeginnerClassDaoImplIT extends AbstractContainerDatabaseIT {

    @Test
    @DisplayName("Create empty beginner class")
    public void testCreateEmptyBeginnerClass() {
        // GIVEN
        BeginnerClass beginnerClassModel = new BeginnerClass();

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        BeginnerClassDao beginnerClassDao = new BeginnerClassDaoImpl(daoEm);
        beginnerClassDao.create(beginnerClassModel);

        // THEN
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query = em.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = query.getSingleResult();

        assertThat(beginnerClassEntity).isNotNull();
        assertThat(beginnerClassEntity.getId()).isEqualTo(1);
        assertThat(beginnerClassEntity.getEvent()).isNull();
        assertThat(beginnerClassEntity.getStudents()).isNullOrEmpty();
        assertThat(beginnerClassEntity.getResources()).isNullOrEmpty();
        assertThat(beginnerClassEntity.getStaff()).isNullOrEmpty();

        em.close();
    }

    @Test
    @DisplayName("Test fully populated beginner class")
    public void testFullyPopulatedBeginnerClass() {
        // GIVEN
        // Create event
        Event eventEntity = new Event();
        Resource resourceEntity = new Resource();
        Staff staffEntity = new Staff();
        Student studentEntity = new Student();

        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();
        em1.getTransaction().begin();
        em1.persist(eventEntity);
        em1.persist(resourceEntity);
        em1.persist(staffEntity);
        em1.persist(studentEntity);
        em1.getTransaction().commit();
        em1.close();

        BeginnerClass beginnerClassModel = new BeginnerClass();

        woorinaru.core.model.management.administration.Event eventModel = EventMapper.MAPPER.mapToModel(eventEntity);
        eventModel.setId(1);

        woorinaru.core.model.management.administration.Resource resourceModel = ResourceMapper.MAPPER.mapToModel(resourceEntity);
        resourceModel.setId(1);

        woorinaru.core.model.user.Staff staffModel = StaffMapper.MAPPER.mapToModel(staffEntity);
        staffModel.setId(1);

        woorinaru.core.model.user.Student studentModel = StudentMapper.MAPPER.mapToModel(studentEntity);
        studentModel.setId(2);

        beginnerClassModel.setEvent(eventModel);
        beginnerClassModel.addResource(resourceModel);
        beginnerClassModel.addStaff(staffModel);
        beginnerClassModel.addStudent(studentModel);

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        BeginnerClassDao beginnerClassDao = new BeginnerClassDaoImpl(daoEm);
        beginnerClassDao.create(beginnerClassModel);

        // THEN
        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query = em2.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        woorinaru.repository.sql.entity.management.administration.BeginnerClass retrievedBeginnerClassEntity = query.getSingleResult();

        assertThat(retrievedBeginnerClassEntity).isNotNull();
        assertThat(retrievedBeginnerClassEntity.getId()).isEqualTo(1);
        assertThat(retrievedBeginnerClassEntity.getEvent()).isNotNull();
        assertThat(retrievedBeginnerClassEntity.getStaff()).hasSize(1);
        assertThat(retrievedBeginnerClassEntity.getStudents()).hasSize(1);
        assertThat(retrievedBeginnerClassEntity.getResources()).hasSize(1);

        em2.close();
    }

    @Test
    @DisplayName("Test delete beginner class")
    public void testDeleteBeginnerClass() {
        // GIVEN
        woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity = new woorinaru.repository.sql.entity.management.administration.BeginnerClass();

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(beginnerClassEntity);
        em.getTransaction().commit();

        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query1 = em.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        assertThat(query1.getResultList()).hasSize(1);

        // WHEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        BeginnerClassDao beginnerClassDao = new BeginnerClassDaoImpl(daoEm);
        beginnerClassDao.delete(BeginnerClassMapper.MAPPER.mapToModel(beginnerClassEntity));

        // THEN
        TypedQuery<woorinaru.repository.sql.entity.management.administration.BeginnerClass> query2 = em.createQuery("SELECT b FROM BeginnerClass b", woorinaru.repository.sql.entity.management.administration.BeginnerClass.class);
        assertThat(query2.getResultList()).isEmpty();

        em.close();
    }

}
