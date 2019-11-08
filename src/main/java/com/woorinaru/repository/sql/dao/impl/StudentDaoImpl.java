package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.StudentDao;
import com.woorinaru.core.exception.ReferenceNotFoundException;
import com.woorinaru.core.exception.ResourceNotFoundException;
import com.woorinaru.core.model.user.Student;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.mapper.model.StudentMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LogManager.getLogger(StudentDao.class);

    private EntityManager em;

    public StudentDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public int create(Student student) {
        LOGGER.debug("Creating a student resource");
        // Map file
        StudentMapper mapper = StudentMapper.MAPPER;
        com.woorinaru.repository.sql.entity.user.Student studentEntity = mapper.mapToEntity(student);

        // Set resources if present
        if (Objects.nonNull(student.getFavouriteResources())) {
            for (com.woorinaru.core.model.management.administration.Resource resourceModel : student.getFavouriteResources()) {
                Resource resourceEntity = em.find(Resource.class, resourceModel.getId());
                if (Objects.isNull(resourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    studentEntity.addFavouriteResource(resourceEntity);
                }
            }
        }

        em.persist(studentEntity);
        em.flush();

        LOGGER.debug("Finished creating a student resource");
        return studentEntity.getId();
    }

    @Override
    public Student get(int id) {
        LOGGER.debug("Retrieving a student resource with id: %d", id);

        com.woorinaru.repository.sql.entity.user.Student studentEntity = em.find(com.woorinaru.repository.sql.entity.user.Student.class, id);

        LOGGER.debug("Student with id: %d. Found: %s", id, studentEntity == null ? "True" : "False");

        if (Objects.isNull(studentEntity)) {
            throw new ResourceNotFoundException(String.format("Could not find student with id: %d", id));
        }

        StudentMapper mapper = StudentMapper.MAPPER;

        Student studentModel = mapper.mapToModel(studentEntity);

        return studentModel;
    }

    @Override
    public void delete(Student student) {
        LOGGER.debug("Deleting student with id: %d", student.getId());

        // Map file
        com.woorinaru.repository.sql.entity.user.Student deleteStudentEntity = em.find(com.woorinaru.repository.sql.entity.user.Student.class, student.getId());

        if (deleteStudentEntity != null) {
            em.remove(deleteStudentEntity);
            LOGGER.debug("Student deleted");
        } else {
            LOGGER.debug("Student with id: '%d' not found. Could not be deleted", student.getId());
            throw new ResourceNotFoundException(String.format("Student with id: '%d' not found. Could not be deleted", student.getId()));
        }
    }

    @Override
    public void modify(Student student) {
        LOGGER.debug("Modifying student with id: %d", student.getId());
        com.woorinaru.repository.sql.entity.user.Student existingStudentEntity = em.find(com.woorinaru.repository.sql.entity.user.Student.class, student.getId());

        if (existingStudentEntity != null) {
            existingStudentEntity.setName(student.getName());
            existingStudentEntity.setNationality(student.getNationality());
            existingStudentEntity.setEmail(student.getEmail());
            existingStudentEntity.setSignUpDateTime(student.getSignUpDateTime());
            Optional.ofNullable(existingStudentEntity.getFavouriteResources()).ifPresentOrElse(Collection::clear, () -> existingStudentEntity.setFavouriteResources(new ArrayList<>()));

            // re add resources
            for (com.woorinaru.core.model.management.administration.Resource resourceModel : student.getFavouriteResources()) {
                int resourceModelId = resourceModel.getId();
                Resource existingResourceEntity = em.find(Resource.class, resourceModelId);
                if (Objects.isNull(existingResourceEntity)) {
                    throw new ReferenceNotFoundException(String.format("Could not find resource id: %d", resourceModel.getId()));
                } else {
                    existingStudentEntity.addFavouriteResource(existingResourceEntity);
                }
            }
            em.merge(existingStudentEntity);
            LOGGER.debug("Finished modifying student");
        } else {
            LOGGER.debug("Could not find student with id: %d. Did not modify", student.getId());
            throw new ResourceNotFoundException(String.format("Could not find student with id: %d. Did not modify", student.getId()));
        }
    }

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Retrieving all students");
        StudentMapper mapper = StudentMapper.MAPPER;

        TypedQuery<com.woorinaru.repository.sql.entity.user.Student> query = em.createQuery("SELECT s FROM Student s", com.woorinaru.repository.sql.entity.user.Student.class);
        List<com.woorinaru.repository.sql.entity.user.Student> entityStudents = query.getResultList();

        List<Student> students = entityStudents.stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());

        LOGGER.debug("Retrieved %d students", students.size());
        return students;
    }
}
