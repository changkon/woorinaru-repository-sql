package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.resource.Resource;

@Mapper
public interface ResourceMapper {

    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    Resource mapToEntity(woorinaru.core.model.management.administration.Resource resourceModel);

    woorinaru.core.model.management.administration.Resource mapToModel(Resource resourceEntity);
}
