package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.StaffDao;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.management.administration.Resource;
import com.woorinaru.core.model.management.administration.Team;
import com.woorinaru.core.model.user.Staff;
import com.woorinaru.core.model.user.StaffRole;
import com.woorinaru.repository.sql.dao.helper.DatabaseContainerRule;
import com.woorinaru.repository.sql.mapper.model.ResourceMapper;
import com.woorinaru.repository.sql.mapper.model.StaffMapper;
import com.woorinaru.repository.sql.util.EntityManagerFactoryUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(DatabaseContainerRule.class)
public class StaffDaoImplIT extends AbstractContainerDatabaseIT {

    private static final LocalDateTime signupDateTime = LocalDateTime.of(LocalDate.of(2018, 5, 10), LocalTime.of(23, 40, 10));

    @Test
    @DisplayName("Test staff create")
    public void testStaffCreate() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        Staff staffModel = new Staff();
        staffModel.setName("Changkon Han");
        staffModel.setNationality("New Zealand");
        staffModel.setEmail("random@domain.com");
        staffModel.setSignUpDateTime(signupDateTime);
        staffModel.setTeam(Team.DESIGN);
        staffModel.setStaffRole(StaffRole.TEACHER);

        // WHEN
        executeInTransaction().accept(daoEm, () -> {
            int generatedId = staffDao.create(staffModel);
            assertThat(generatedId).isEqualTo(1);
        });

        // THEN
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em.createQuery("SELECT s FROM Staff s", com.woorinaru.repository.sql.entity.user.Staff.class);
        List<com.woorinaru.repository.sql.entity.user.Staff> staffEntities = query.getResultList();

        assertThat(staffEntities).hasSize(1);
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = staffEntities.get(0);
        assertThat(staffEntity.getName()).isEqualTo("Changkon Han");
        assertThat(staffEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(staffEntity.getEmail()).isEqualTo("random@domain.com");
        Assertions.assertThat(staffEntity.getFavouriteResources()).isEmpty();
        assertThat(staffEntity.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(staffEntity.getTeam()).isEqualTo(com.woorinaru.repository.sql.entity.management.administration.Team.DESIGN);
        assertThat(staffEntity.getStaffRole()).isEqualTo(com.woorinaru.repository.sql.entity.user.StaffRole.TEACHER);

        em.close();
    }

    @Test
    @DisplayName("Test staff create with resource")
    public void testStaffCreateWithResource() {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        Staff staffModel = new Staff();
        staffModel.setName("Changkon Han");
        staffModel.setNationality("New Zealand");
        staffModel.setEmail("random@domain.com");
        staffModel.setSignUpDateTime(signupDateTime);
        staffModel.setTeam(Team.DESIGN);
        staffModel.setStaffRole(StaffRole.TEACHER);

        // Create resource
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();
        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity = new com.woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity.setDescription("Test resource description");
        resourceEntity.setResource("Test resource".getBytes());
        // Send resource to db
        em1.getTransaction().begin();
        em1.persist(resourceEntity);
        em1.getTransaction().commit();
        em1.close();

        Resource resourceModel = ResourceMapper.MAPPER.mapToModel(resourceEntity);
        resourceModel.setId(1);

        staffModel.setFavouriteResources(List.of(resourceModel));

        // WHEN
        executeInTransaction().accept(daoEm, () -> {
            int generatedId = staffDao.create(staffModel);
            assertThat(generatedId).isEqualTo(1);
        });

        // THEN
        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em2.createQuery("SELECT s FROM Staff s", com.woorinaru.repository.sql.entity.user.Staff.class);
        List<com.woorinaru.repository.sql.entity.user.Staff> staffEntities = query.getResultList();

        assertThat(staffEntities).hasSize(1);
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = staffEntities.get(0);
        assertThat(staffEntity.getName()).isEqualTo("Changkon Han");
        assertThat(staffEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(staffEntity.getEmail()).isEqualTo("random@domain.com");
        Assertions.assertThat(staffEntity.getFavouriteResources()).hasSize(1);
        com.woorinaru.repository.sql.entity.resource.Resource retrievedResourceEntity = staffEntity.getFavouriteResources().iterator().next();
        assertThat(retrievedResourceEntity.getId()).isEqualTo(1);
        assertThat(retrievedResourceEntity.getResource()).isEqualTo("Test resource".getBytes());
        assertThat(retrievedResourceEntity.getDescription()).isEqualTo("Test resource description");

        assertThat(staffEntity.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(staffEntity.getTeam()).isEqualTo(com.woorinaru.repository.sql.entity.management.administration.Team.DESIGN);
        assertThat(staffEntity.getStaffRole()).isEqualTo(com.woorinaru.repository.sql.entity.user.StaffRole.TEACHER);

        em2.close();
    }

    @Test
    @DisplayName("Test retrieve staff by id")
    public void testStudentGet() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = createStaffEntity("Alan Foo", "Australia", "test@domain.com");
        em.persist(staffEntity);
        em.getTransaction().commit();
        em.close();

        // WHEN
        Staff retrievedStaffModel = staffDao.get(1);

        // THEN
        assertThat(retrievedStaffModel.getId()).isEqualTo(1);
        assertThat(retrievedStaffModel.getName()).isEqualTo("Alan Foo");
        assertThat(retrievedStaffModel.getNationality()).isEqualTo("Australia");
        assertThat(retrievedStaffModel.getEmail()).isEqualTo("test@domain.com");
        assertThat(retrievedStaffModel.getFavouriteResources()).isEmpty();
        assertThat(retrievedStaffModel.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(retrievedStaffModel.getTeam()).isEqualTo(Team.DESIGN);
        assertThat(retrievedStaffModel.getStaffRole()).isEqualTo(StaffRole.TEACHER);
    }

    @Test
    @DisplayName("Test retrieve staff with favourite resources by id")
    public void testStudentWithFavouriteResourcesGet() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = createStaffEntity("Alan Foo", "Australia", "test@domain.com");

        // Create staff favourite resources
        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity1 = new com.woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity1.setDescription("test resource 1");
        resourceEntity1.setResource("test 1".getBytes());

        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity2 = new com.woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity2.setDescription("test resource 2");
        resourceEntity2.setResource("test 2".getBytes());

        em.persist(resourceEntity1);
        em.persist(resourceEntity2);

        staffEntity.addFavouriteResource(resourceEntity1);
        staffEntity.addFavouriteResource(resourceEntity2);

        em.persist(staffEntity);
        em.getTransaction().commit();
        em.close();

        // WHEN
        Staff retrievedStaffModel = staffDao.get(1);

        // THEN
        assertThat(retrievedStaffModel.getId()).isEqualTo(1);
        assertThat(retrievedStaffModel.getName()).isEqualTo("Alan Foo");
        assertThat(retrievedStaffModel.getNationality()).isEqualTo("Australia");
        assertThat(retrievedStaffModel.getEmail()).isEqualTo("test@domain.com");
        assertThat(retrievedStaffModel.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(retrievedStaffModel.getTeam()).isEqualTo(Team.DESIGN);
        assertThat(retrievedStaffModel.getStaffRole()).isEqualTo(StaffRole.TEACHER);

        assertThat(retrievedStaffModel.getFavouriteResources()).hasSize(2);
        Iterator<Resource> iter = retrievedStaffModel.getFavouriteResources().iterator();
        Resource resourceModel1 = iter.next();
        Resource resourceModel2 = iter.next();

        assertThat(resourceModel1.getId()).isEqualTo(1);
        assertThat(resourceModel1.getDescription()).isEqualTo("test resource 1");
        assertThat(resourceModel1.getResource()).isNull();

        assertThat(resourceModel2.getId()).isEqualTo(2);
        assertThat(resourceModel2.getDescription()).isEqualTo("test resource 2");
        assertThat(resourceModel2.getResource()).isNull();
    }

    @Test
    @DisplayName("Test retrieving non-existent staff id")
    public void testGetNonExistentStaffId() {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);

        // WHEN
        Throwable thrown = catchThrowable(() -> staffDao.get(111));

        // THEN
        assertThat(thrown).isInstanceOf(ResourceNotFoundException.class);
        assertThat(thrown).hasMessage("Could not find staff with id: 111");
    }

    @Test
    @DisplayName("Test staff delete")
    public void testStudentDelete() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();
        em1.getTransaction().begin();
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = createStaffEntity("Alice Wonderland", "USA", "alice@test.com");
        em1.persist(staffEntity);
        em1.getTransaction().commit();

        // Confirm that inserted query exists
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em1.createQuery("SELECT s FROM Staff s WHERE id='1'", com.woorinaru.repository.sql.entity.user.Staff.class);
        com.woorinaru.repository.sql.entity.user.Staff retrievedStaffEntity = query.getSingleResult();

        assertThat(retrievedStaffEntity).isNotNull();
        StaffMapper mapper = StaffMapper.MAPPER;
        Staff staffModel = mapper.mapToModel(retrievedStaffEntity);

        em1.close();

        // WHEN
        executeInTransaction().accept(daoEm, () -> staffDao.delete(staffModel));

        // THEN
        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> secondQuery = em2.createQuery("SELECT s FROM Staff s WHERE id='1'", com.woorinaru.repository.sql.entity.user.Staff.class);
        assertThat(secondQuery.getResultList()).isEmpty();
        em2.close();
    }

    @Test
    @DisplayName("Test deleting staff with favourite resources is not cascaded")
    public void testStaffDeleteDoesNotCascadeResourceDelete() {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();
        em1.getTransaction().begin();
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = createStaffEntity("Alice Wonderland", "USA", "alice@test.com");

        // Create staff favourite resources
        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity1 = new com.woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity1.setDescription("test resource 1");
        resourceEntity1.setResource("test 1".getBytes());

        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity2 = new com.woorinaru.repository.sql.entity.resource.Resource();
        resourceEntity2.setDescription("test resource 2");
        resourceEntity2.setResource("test 2".getBytes());

        em1.persist(resourceEntity1);
        em1.persist(resourceEntity2);

        staffEntity.addFavouriteResource(resourceEntity1);
        staffEntity.addFavouriteResource(resourceEntity2);

        em1.persist(staffEntity);
        em1.getTransaction().commit();

        // Confirm that inserted query exists
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em1.createQuery("SELECT s FROM Staff s WHERE id='1'", com.woorinaru.repository.sql.entity.user.Staff.class);
        com.woorinaru.repository.sql.entity.user.Staff retrievedStaffEntity = query.getSingleResult();

        assertThat(retrievedStaffEntity).isNotNull();
        StaffMapper mapper = StaffMapper.MAPPER;
        Staff staffModel = mapper.mapToModel(retrievedStaffEntity);

        em1.close();

        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        Query userResourceQuery1 = em2.createNativeQuery("SELECT * FROM USER_RESOURCE");
        int userResourceSize1 = userResourceQuery1.getResultList().size();
        assertThat(userResourceSize1).isEqualTo(2);

        // WHEN
        executeInTransaction().accept(daoEm, () -> staffDao.delete(staffModel));

        // THEN
        EntityManager em3 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> secondQuery = em3.createQuery("SELECT s FROM Staff s WHERE id='1'", com.woorinaru.repository.sql.entity.user.Staff.class);
        assertThat(secondQuery.getResultList()).isEmpty();
        em3.close();

        EntityManager em4 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<com.woorinaru.repository.sql.entity.resource.Resource> thirdQuery = em4.createQuery("SELECT r FROM Resource r", com.woorinaru.repository.sql.entity.resource.Resource.class);
        assertThat(thirdQuery.getResultList()).hasSize(2);
        em4.close();

        EntityManager em5 = EntityManagerFactoryUtil.getEntityManager();
        Query userResourceQuery2 = em5.createNativeQuery("SELECT * FROM USER_RESOURCE");
        int userResourceSize2 = userResourceQuery2.getResultList().size();
        assertThat(userResourceSize2).isZero();
    }

    @Test
    @DisplayName("Test retrieve multiple staff")
    public void testGetAllStaff() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        com.woorinaru.repository.sql.entity.user.Staff studentEntity1 = createStaffEntity("Josh Howard", "USA", "josh@test.com");
        com.woorinaru.repository.sql.entity.user.Staff studentEntity2 = createStaffEntity("Hayley Low", "Canada", "hayley@test.com");
        em.persist(studentEntity1);
        em.persist(studentEntity2);
        em.getTransaction().commit();

        // Retrieve all students
        // WHEN
        List<Staff> retrievedStaff = staffDao.getAll();

        // THEN
        Assertions.assertThat(retrievedStaff).isNotEmpty();
        Assertions.assertThat(retrievedStaff).hasSize(2);

        Staff retrievedStaff1 = retrievedStaff.get(0);
        assertThat(retrievedStaff1.getId()).isEqualTo(1);
        assertThat(retrievedStaff1.getName()).isEqualTo("Josh Howard");
        assertThat(retrievedStaff1.getNationality()).isEqualTo("USA");
        assertThat(retrievedStaff1.getEmail()).isEqualTo("josh@test.com");
        assertThat(retrievedStaff1.getFavouriteResources()).isEmpty();
        assertThat(retrievedStaff1.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(retrievedStaff1.getTeam()).isEqualTo(Team.DESIGN);
        assertThat(retrievedStaff1.getStaffRole()).isEqualTo(StaffRole.TEACHER);

        Staff retrievedStaff2 = retrievedStaff.get(1);
        assertThat(retrievedStaff2.getId()).isEqualTo(2);
        assertThat(retrievedStaff2.getName()).isEqualTo("Hayley Low");
        assertThat(retrievedStaff2.getNationality()).isEqualTo("Canada");
        assertThat(retrievedStaff2.getEmail()).isEqualTo("hayley@test.com");
        assertThat(retrievedStaff2.getFavouriteResources()).isEmpty();
        assertThat(retrievedStaff2.getSignUpDateTime()).isEqualTo(signupDateTime);
        assertThat(retrievedStaff2.getTeam()).isEqualTo(Team.DESIGN);
        assertThat(retrievedStaff2.getStaffRole()).isEqualTo(StaffRole.TEACHER);

        em.close();
    }

    @Test
    @DisplayName("Test retrieve returns empty list when there are no staff")
    public void testEmptyListIsReturnedWhenNoStaff() throws SQLException {
        // GIVEN
        EntityManager daoEm = EntityManagerFactoryUtil.getEntityManager();
        StaffDao staffDao = new StaffDaoImpl(daoEm);

        // WHEN
        List<Staff> staff = staffDao.getAll();

        // THEN
        Assertions.assertThat(staff).isEmpty();
    }

    @Test
    @DisplayName("Test modify staff")
    public void testModifyStaff() {
        // GIVEN
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = createStaffEntity("Test staff name", "New Zealand", "test@test.com");
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();

        em.getTransaction().begin();
        em.persist(staffEntity);
        em.getTransaction().commit();

        Staff modifyStaffModel = new Staff();
        modifyStaffModel.setId(1);
        modifyStaffModel.setName("Modified name");
        modifyStaffModel.setNationality("South Korea");
        modifyStaffModel.setTeam(Team.PLANNING);
        modifyStaffModel.setStaffRole(StaffRole.LEADER);

        // WHEN
        StaffDao staffDao = new StaffDaoImpl(em);
        executeInTransaction().accept(em, () -> staffDao.modify(modifyStaffModel));

        TypedQuery<com.woorinaru.repository.sql.entity.user.Staff> query = em.createQuery("SELECT s FROM Staff s", com.woorinaru.repository.sql.entity.user.Staff.class);
        com.woorinaru.repository.sql.entity.user.Staff returnedStaffEntity = query.getSingleResult();

        // THEN
        assertThat(returnedStaffEntity.getName()).isEqualTo("Modified name");
        assertThat(returnedStaffEntity.getNationality()).isEqualTo("South Korea");
        assertThat(returnedStaffEntity.getTeam()).isEqualTo(com.woorinaru.repository.sql.entity.management.administration.Team.PLANNING);
        assertThat(returnedStaffEntity.getStaffRole()).isEqualTo(com.woorinaru.repository.sql.entity.user.StaffRole.LEADER);
        assertThat(returnedStaffEntity.getSignUpDateTime()).isNull();
        Assertions.assertThat(returnedStaffEntity.getFavouriteResources()).isNullOrEmpty();

        em.close();
    }

    private com.woorinaru.repository.sql.entity.user.Staff createStaffEntity(String name, String nationality, String email) {
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = new com.woorinaru.repository.sql.entity.user.Staff();
        staffEntity.setName(name);
        staffEntity.setNationality(nationality);
        staffEntity.setEmail(email);
        staffEntity.setSignUpDateTime(signupDateTime);
        staffEntity.setTeam(com.woorinaru.repository.sql.entity.management.administration.Team.DESIGN);
        staffEntity.setStaffRole(com.woorinaru.repository.sql.entity.user.StaffRole.TEACHER);
        return staffEntity;
    }
}
