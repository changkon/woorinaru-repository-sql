package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.user.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {

    AdminMapper MAPPER = Mappers.getMapper(AdminMapper.class);

    Admin mapToEntity(com.woorinaru.core.model.user.Admin adminModel);

    com.woorinaru.core.model.user.Admin mapToModel(Admin adminEntity);
}
