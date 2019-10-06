package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.entity.user.User;

import java.util.stream.Stream;

public class StudentMapper implements ModelMapper<woorinaru.core.model.user.Student, Student> {

    private UserMapper userMapper;

    public StudentMapper() {
        userMapper = new UserMapper();
    }

    @Override
    public Student mapToEntity(woorinaru.core.model.user.Student model) {
        Student student = new Student();
        userMapper.mapUserModelToEntity().apply(model, student);
        return student;
    }

    @Override
    public woorinaru.core.model.user.Student mapToModel(Student entity) {
        woorinaru.core.model.user.Student studentModel = new woorinaru.core.model.user.Student();
        userMapper.mapEntityToUserModel().apply(studentModel, entity);
        return studentModel;
    }
}
