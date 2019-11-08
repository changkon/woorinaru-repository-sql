package com.woorinaru.repository.sql.mapper.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.woorinaru.repository.sql.entity.resource.Resource;

@Mapper
public interface ResourceMapper {

    ResourceMapper MAPPER = Mappers.getMapper(ResourceMapper.class);

    Resource mapToEntity(com.woorinaru.core.model.management.administration.Resource resourceModel);

    com.woorinaru.core.model.management.administration.Resource mapToModel(Resource resourceEntity);
}
