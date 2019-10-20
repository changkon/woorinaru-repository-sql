package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.user.Admin;

@Mapper(uses=PartialResourceMapper.class)
public interface AdminMapper {

    AdminMapper MAPPER = Mappers.getMapper(AdminMapper.class);

    Admin mapToEntity(woorinaru.core.model.user.Admin adminModel);

    woorinaru.core.model.user.Admin mapToModel(Admin adminEntity);
}
