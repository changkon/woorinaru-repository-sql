package woorinaru.repository.sql.mapper.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.resource.Resource;

@Mapper
public interface ResourceMapper {

    ResourceMapper MAPPER = Mappers.getMapper(ResourceMapper.class);

    Resource mapToEntity(woorinaru.core.model.management.administration.Resource resourceModel);

    woorinaru.core.model.management.administration.Resource mapToModel(Resource resourceEntity);
}
