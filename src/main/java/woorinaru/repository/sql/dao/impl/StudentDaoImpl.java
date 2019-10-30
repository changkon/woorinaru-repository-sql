package woorinaru.repository.sql.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import woorinaru.core.dao.spi.StudentDao;
import woorinaru.core.model.user.Student;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.model.StudentMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LogManager.getLogger(StudentDao.class);

    private EntityManager em;

    public StudentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Student student) {
        LOGGER.debug("Creating a student resource");
        // Map file
        StudentMapper mapper = StudentMapper.MAPPER;
        woorinaru.repository.sql.entity.user.Student studentEntity = mapper.mapToEntity(student);

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

        LOGGER.debug("Finished creating a student resource");
    }

    @Override
    public Optional<Student> get(int id) {
        LOGGER.debug("Retrieving a student resource with id: %d", id);

        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, id);

        LOGGER.debug("Student with id: %d. Found: %s", id, studentEntity == null ? "True" : "False");

        StudentMapper mapper = StudentMapper.MAPPER;

        Optional<Student> studentModel = Stream.ofNullable(studentEntity)
            .map(mapper::mapToModel)
            .findFirst();

        return studentModel;
    }

    @Override
    public void delete(Student student) {
        LOGGER.debug("Deleting student with id: %d", student.getId());

        // Map file
        woorinaru.repository.sql.entity.user.Student deleteStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());

        if (deleteStudentEntity != null) {
            em.remove(deleteStudentEntity);
            LOGGER.debug("Student deleted");
        } else {
            LOGGER.debug("Student with id: '%d' not found. Could not be deleted", student.getId());
        }
    }

    @Override
    public void modify(Student student) {
        LOGGER.debug("Modifying student with id: %d", student.getId());
        woorinaru.repository.sql.entity.user.Student existingStudentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());

        if (existingStudentEntity != null) {
            existingStudentEntity.setName(student.getName());
            existingStudentEntity.setNationality(student.getNationality());
            existingStudentEntity.setEmail(student.getEmail());
            existingStudentEntity.setSignUpDateTime(student.getSignUpDateTime());
            existingStudentEntity.setFavouriteResources(new ArrayList<>());

            // re add resources
            for (woorinaru.core.model.management.administration.Resource resourceModel : student.getFavouriteResources()) {
                int resourceModelId = resourceModel.getId();
                Resource existingResourceEntity = em.find(Resource.class, resourceModelId);
                existingStudentEntity.addFavouriteResource(existingResourceEntity);
            }
            em.merge(existingStudentEntity);
            LOGGER.debug("Finished modifying student");
        } else {
            LOGGER.debug("Could not find student with id: %d. Did not modify", student.getId());
        }
    }

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Retrieving all students");
        StudentMapper mapper = StudentMapper.MAPPER;

        TypedQuery<woorinaru.repository.sql.entity.user.Student> query = em.createQuery("SELECT s FROM Student s", woorinaru.repository.sql.entity.user.Student.class);
        List<woorinaru.repository.sql.entity.user.Student> entityStudents = query.getResultList();

        List<Student> students = entityStudents.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d students", students.size());
        return students;
    }
}
