package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.user.Student;

public class StudentMapper implements ModelMapper<woorinaru.core.model.user.Student, Student> {

    private UserMapper userMapper;

    public StudentMapper() {
        userMapper = new UserMapper();
    }

    @Override
    public Student mapToEntity(woorinaru.core.model.user.Student model) {
        Student studentEntity = new Student();
        userMapper.mapUserModelToEntity().apply(model, studentEntity);
        return studentEntity;
    }

    @Override
    public woorinaru.core.model.user.Student mapToModel(Student entity) {
        woorinaru.core.model.user.Student studentModel = new woorinaru.core.model.user.Student();
        userMapper.mapEntityToUserModel().apply(studentModel, entity);
        return studentModel;
    }
}
