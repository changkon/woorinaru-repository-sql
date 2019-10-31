package woorinaru.repository.sql.mapper.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.user.Student;

@Mapper(uses=PartialResourceMapper.class)
public interface StudentMapper {

    StudentMapper MAPPER = Mappers.getMapper(StudentMapper.class);

    @Mapping(target="favouriteResources", ignore=true)
    Student mapToEntity(woorinaru.core.model.user.Student studentModel);

    woorinaru.core.model.user.Student mapToModel(Student studentEntity);
}
