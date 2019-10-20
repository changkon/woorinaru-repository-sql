package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.user.Student;

@Mapper(uses=PartialResourceMapper.class)
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student mapToEntity(woorinaru.core.model.user.Student studentModel);

    woorinaru.core.model.user.Student mapToModel(Student studentEntity);
}
