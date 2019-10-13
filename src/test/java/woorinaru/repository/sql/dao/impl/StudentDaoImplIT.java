package woorinaru.repository.sql.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import woorinaru.core.dao.spi.StudentDao;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.model.StudentMapper;
import woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DatabaseContainerRule.class)
public class StudentDaoImplIT extends AbstractContainerDatabaseIT {

    private static final LocalDateTime signupDateTime = LocalDateTime.of(LocalDate.of(2018, 5, 10), LocalTime.of(23, 40, 10));

    @Test
    @DisplayName("Test student create")
    public void testStudentCreate() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();
        Student student = new Student();
        student.setName("Changkon Han");
        student.setNationality("New Zealand");
        student.setEmail("random@domain.com");
        student.setSignupDateTime(signupDateTime);

        // WHEN
        studentDao.create(student);

        // THEN
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.user.Student> query = em.createQuery("SELECT s FROM Student s", woorinaru.repository.sql.entity.user.Student.class);
        List<woorinaru.repository.sql.entity.user.Student> studentEntities = query.getResultList();

        assertThat(studentEntities).hasSize(1);
        woorinaru.repository.sql.entity.user.Student studentEntity = studentEntities.get(0);
        assertThat(studentEntity.getName()).isEqualTo("Changkon Han");
        assertThat(studentEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(studentEntity.getEmail()).isEqualTo("random@domain.com");
        assertThat(studentEntity.getFavouriteResources()).isEmpty();
        assertThat(studentEntity.getSignUpDateTime()).isEqualTo(signupDateTime);

        em.close();
    }

    @Test
    @DisplayName("Test retrieve student by id")
    public void testStudentGet() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.user.Student studentEntity = createStudentEntity("Alan Foo", "Australia", "test@domain.com");
        em.persist(studentEntity);
        em.getTransaction().commit();
        em.close();

        // WHEN
        Optional<Student> retrievedStudentModelOptional = studentDao.get(1);

        // THEN
        assertThat(retrievedStudentModelOptional).isPresent();
        Student retrievedStudentModel = retrievedStudentModelOptional.get();

        assertThat(retrievedStudentModel.getId()).isEqualTo(1);
        assertThat(retrievedStudentModel.getName()).isEqualTo("Alan Foo");
        assertThat(retrievedStudentModel.getNationality()).isEqualTo("Australia");
        assertThat(retrievedStudentModel.getEmail()).isEqualTo("test@domain.com");
        assertThat(retrievedStudentModel.getFavouriteResources()).isEmpty();
        assertThat(retrievedStudentModel.getSignupDateTime()).isEqualTo(signupDateTime);
    }

    @Test
    @DisplayName("Test retrieving non-existent student id")
    public void testGetNonExistentStudentId() {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();

        // WHEN
        Optional<Student> studentModelOptional = studentDao.get(111);

        // THEN
        assertThat(studentModelOptional).isEmpty();
    }

    @Test
    @DisplayName("Test student delete")
    public void testStudentDelete() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();
        EntityManager em1 = EntityManagerFactoryUtil.getEntityManager();
        em1.getTransaction().begin();
        woorinaru.repository.sql.entity.user.Student studentEntity = createStudentEntity("Alice Wonderland", "USA", "alice@test.com");
        em1.persist(studentEntity);
        em1.getTransaction().commit();

        // Confirm that inserted query exists
        TypedQuery<woorinaru.repository.sql.entity.user.Student> query = em1.createQuery("SELECT s FROM Student s WHERE id='1'", woorinaru.repository.sql.entity.user.Student.class);
        woorinaru.repository.sql.entity.user.Student retrievedStudentEntity = query.getSingleResult();

        assertThat(retrievedStudentEntity).isNotNull();
        StudentMapper mapper = new StudentMapper();
        Student studentModel = mapper.mapToModel(retrievedStudentEntity);

        em1.close();

        // WHEN
        studentDao.delete(studentModel);

        // THEN
        EntityManager em2 = EntityManagerFactoryUtil.getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.user.Student> secondQuery = em2.createQuery("SELECT s FROM Student s WHERE id='1'", woorinaru.repository.sql.entity.user.Student.class);
        assertThat(secondQuery.getResultList()).isEmpty();
        em2.close();
    }

    @Test
    @DisplayName("Test retrieve multiple students")
    public void testGetAllStudents() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.user.Student studentEntity1 = createStudentEntity("Josh Howard", "USA", "josh@test.com");
        woorinaru.repository.sql.entity.user.Student studentEntity2 = createStudentEntity("Hayley Low", "Canada", "hayley@test.com");
        em.persist(studentEntity1);
        em.persist(studentEntity2);
        em.getTransaction().commit();

        // Retrieve all students
        // WHEN
        List<Student> retrievedStudents = studentDao.getAll();

        // THEN
        assertThat(retrievedStudents).isNotEmpty();
        assertThat(retrievedStudents).hasSize(2);

        Student retrievedStudent1 = retrievedStudents.get(0);
        assertThat(retrievedStudent1.getId()).isEqualTo(1);
        assertThat(retrievedStudent1.getName()).isEqualTo("Josh Howard");
        assertThat(retrievedStudent1.getNationality()).isEqualTo("USA");
        assertThat(retrievedStudent1.getEmail()).isEqualTo("josh@test.com");
        assertThat(retrievedStudent1.getFavouriteResources()).isEmpty();
        assertThat(retrievedStudent1.getSignupDateTime()).isEqualTo(signupDateTime);

        Student retrievedStudent2 = retrievedStudents.get(1);
        assertThat(retrievedStudent2.getId()).isEqualTo(2);
        assertThat(retrievedStudent2.getName()).isEqualTo("Hayley Low");
        assertThat(retrievedStudent2.getNationality()).isEqualTo("Canada");
        assertThat(retrievedStudent2.getEmail()).isEqualTo("hayley@test.com");
        assertThat(retrievedStudent2.getFavouriteResources()).isEmpty();
        assertThat(retrievedStudent2.getSignupDateTime()).isEqualTo(signupDateTime);

        em.close();
    }

    @Test
    @DisplayName("Test retrieve returns empty list when there are no students")
    public void testEmptyListIsReturnedWhenNoStudents() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();

        // WHEN
        List<Student> students = studentDao.getAll();

        // THEN
        assertThat(students).isEmpty();
    }

    @Test
    @DisplayName("Test student with favourite resources returned")
    public void testStudentWithFavouriteResources() throws SQLException {
        // GIVEN
        StudentDao studentDao = new StudentDaoImpl();
        EntityManager em = EntityManagerFactoryUtil.getEntityManager();
        em.getTransaction().begin();

        String resource = new String("resource");

        Resource resourceContainer = new Resource();
        resourceContainer.setResource(resource.getBytes());
        resourceContainer.setDescription("test resource");
        em.persist(resourceContainer);
        em.refresh(resourceContainer);

        woorinaru.repository.sql.entity.user.Student studentEntity = createStudentEntity("Alan Foster", "UK", "alan@test.com");
        studentEntity.setFavouriteResources(List.of(resourceContainer));

        em.persist(studentEntity);
        em.getTransaction().commit();

        // WHEN
        Optional<Student> studentModelOptional = studentDao.get(1);
        assertThat(studentModelOptional).isNotEmpty();
        Student studentModel = studentModelOptional.get();
        assertThat(studentModel.getId()).isEqualTo(1);
        assertThat(studentModel.getName()).isEqualTo("Alan Foster");
        assertThat(studentModel.getNationality()).isEqualTo("UK");
        assertThat(studentModel.getEmail()).isEqualTo("alan@test.com");
        assertThat(studentModel.getFavouriteResources()).isNotEmpty();
        assertThat(studentModel.getFavouriteResources()).hasSize(1);
        assertThat(studentModel.getSignupDateTime()).isEqualTo(signupDateTime);

        woorinaru.core.model.management.administration.Resource resourceModel = studentModel.getFavouriteResources().iterator().next();
        assertThat(resourceModel.getId()).isEqualTo(1);
        assertThat(resourceModel.getResource()).isEqualTo(resource.getBytes());
        assertThat(resourceModel.getDescription()).isEqualTo("test resource");

        em.close();
    }

    private woorinaru.repository.sql.entity.user.Student createStudentEntity(String name, String nationality, String email) {
        woorinaru.repository.sql.entity.user.Student studentEntity = new woorinaru.repository.sql.entity.user.Student();
        studentEntity.setName(name);
        studentEntity.setNationality(nationality);
        studentEntity.setEmail(email);
        studentEntity.setSignUpDateTime(signupDateTime);
        return studentEntity;
    }
}
