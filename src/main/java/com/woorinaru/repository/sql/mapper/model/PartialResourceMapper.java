package com.woorinaru.repository.sql.mapper.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.woorinaru.repository.sql.entity.resource.Resource;

@Mapper
public interface PartialResourceMapper {

    PartialResourceMapper MAPPER = Mappers.getMapper(PartialResourceMapper.class);

    @Mapping(source="id", target="id")
    @Mapping(target="resource", ignore=true)
    @Mapping(source="description", target="description")
    Resource mapToEntity(com.woorinaru.core.model.management.administration.Resource resourceModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resource", ignore=true)
    @Mapping(source="description", target="description")
    com.woorinaru.core.model.management.administration.Resource mapToModel(Resource resourceEntity);

}
