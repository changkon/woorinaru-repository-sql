package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.StudentDao;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.mapping.entity.StudentCopy;
import woorinaru.repository.sql.mapping.model.StudentMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LogManager.getLogger(StudentDao.class);

    @Override
    public void create(Student student) {
        LOGGER.debug("Creating a student resource");
        // Map file
        StudentMapper mapper = new StudentMapper();
        woorinaru.repository.sql.entity.user.Student studentEntity = mapper.mapToEntity(student);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(studentEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Finished creating a student resource");
    }

    @Override
    public Optional<Student> get(int id) {
        LOGGER.debug("Retrieving a student resource with id: %d", id);
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, id);
        em.close();

        StudentMapper mapper = new StudentMapper();
        Student studentModel = mapper.mapToModel(studentEntity);

        LOGGER.debug("Student with id: %d. Found: %s", id, studentModel == null ? "True" : "False");

        return Optional.ofNullable(studentModel);
    }

    @Override
    public void delete(Student student) {
        LOGGER.debug("Deleting student with id: %d", student.getId());

        // Map file
        StudentMapper mapper = new StudentMapper();
        woorinaru.repository.sql.entity.user.Student studentEntity = mapper.mapToEntity(student);

        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.user.Student deleteStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, studentEntity.getId());
        em.getTransaction().begin();
        em.remove(deleteStudentEntity);
        em.getTransaction().commit();
        em.close();

        LOGGER.debug("Student deleted");
    }

    @Override
    public void modify(Student student) {
        LOGGER.debug("Modifying student with id: %d", student.getId());
        StudentMapper mapper = new StudentMapper();
        woorinaru.repository.sql.entity.user.Student modifiedStudentEntity = mapper.mapToEntity(student);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        woorinaru.repository.sql.entity.user.Student existingStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());

        StudentCopy copyUtil = new StudentCopy();
        copyUtil.copy(modifiedStudentEntity, existingStudentEntity);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Retrieving all students");
        StudentMapper mapper = new StudentMapper();

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.user.Student> query = em.createQuery("SELECT u FROM USER u", woorinaru.repository.sql.entity.user.Student.class);
        List<woorinaru.repository.sql.entity.user.Student> entityStudents = query.getResultList();
        em.close();

        List<Student> students = entityStudents.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d students", students.size());

        return students;
    }
}
