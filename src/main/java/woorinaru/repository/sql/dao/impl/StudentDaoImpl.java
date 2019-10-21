package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.command.UpdateCommand;
import woorinaru.core.dao.spi.StudentDao;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.adapter.StudentAdapter;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.model.StudentMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static woorinaru.repository.sql.util.EntityManagerFactoryUtil.getEntityManager;

public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LogManager.getLogger(StudentDao.class);

    @Override
    public void create(Student student) {
        LOGGER.debug("Creating a student resource");
        // Map file
        StudentMapper mapper = StudentMapper.MAPPER;
        woorinaru.repository.sql.entity.user.Student studentEntity = mapper.mapToEntity(student);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        // Set resources if present
        if (Objects.nonNull(student.getFavouriteResources())) {
            for (woorinaru.core.model.management.administration.Resource resourceModel : student.getFavouriteResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.nonNull(resourceEntity)) {
                    studentEntity.addFavouriteResource(resourceEntity);
                }
            }
        }

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

        LOGGER.debug("Student with id: %d. Found: %s", id, studentEntity == null ? "True" : "False");

        StudentMapper mapper = StudentMapper.MAPPER;

        Optional<Student> studentModel = Stream.ofNullable(studentEntity)
            .map(mapper::mapToModel)
            .findFirst();

        em.close();

        return studentModel;
    }

    @Override
    public void delete(Student student) {
        LOGGER.debug("Deleting student with id: %d", student.getId());

        // Map file
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.user.Student deleteStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());

        if (deleteStudentEntity != null) {
            em.getTransaction().begin();
            em.remove(deleteStudentEntity);
            em.getTransaction().commit();
            LOGGER.debug("Student deleted");
        } else {
            LOGGER.debug("Student with id: '%d' not found. Could not be deleted", student.getId());
        }

        em.close();
    }

    @Override
    public void modify(UpdateCommand<Student> updateCommand) {
        Student studentModel = updateCommand.getReceiver();
        LOGGER.debug("Modifying student with id: %d", studentModel.getId());
        EntityManager em = getEntityManager();
        woorinaru.repository.sql.entity.user.Student existingStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, studentModel.getId());

        if (existingStudentEntity != null) {
            Student staffAdapter = new StudentAdapter(existingStudentEntity, em);
            updateCommand.setReceiver(staffAdapter);
            updateCommand.execute();
        } else {
            LOGGER.debug("Student with id: '%d' not found. Could not be modified", studentModel.getId());
        }

        em.close();
    }

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Retrieving all students");
        StudentMapper mapper = StudentMapper.MAPPER;

        EntityManager em = getEntityManager();
        TypedQuery<woorinaru.repository.sql.entity.user.Student> query = em.createQuery("SELECT s FROM Student s", woorinaru.repository.sql.entity.user.Student.class);
        List<woorinaru.repository.sql.entity.user.Student> entityStudents = query.getResultList();

        List<Student> students = entityStudents.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d students", students.size());
        em.close();
        return students;
    }
}
