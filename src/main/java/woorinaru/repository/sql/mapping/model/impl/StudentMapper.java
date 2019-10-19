package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class StudentMapper implements ModelMapper<woorinaru.core.model.user.Student, Student> {

    private UserMapper userMapper;

    public StudentMapper() {
        this(new UserMapper());
    }

    public StudentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    public Class<woorinaru.core.model.user.Student> getModelClass() {
        return woorinaru.core.model.user.Student.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.user.Student.class)
            .all()
            .build();
        return mapperContract.equals(contract);
    }
}
