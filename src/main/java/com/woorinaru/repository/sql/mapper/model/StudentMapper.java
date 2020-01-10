package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.user.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper MAPPER = Mappers.getMapper(StudentMapper.class);

    Student mapToEntity(com.woorinaru.core.model.user.Student studentModel);

    com.woorinaru.core.model.user.Student mapToModel(Student studentEntity);
}
