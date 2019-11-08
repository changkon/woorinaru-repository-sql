package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.Team;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.StaffRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses=PartialResourceMapper.class)
public interface StaffMapper {

    StaffMapper MAPPER = Mappers.getMapper(StaffMapper.class);

    @Mapping(target="favouriteResources", ignore=true)
    Staff mapToEntity(com.woorinaru.core.model.user.Staff staffModel);

    com.woorinaru.core.model.user.Staff mapToModel(Staff staffEntity);

    @ValueMappings({
        @ValueMapping(source="LEADER", target="LEADER"),
        @ValueMapping(source="VICE_LEADER", target="VICE_LEADER"),
        @ValueMapping(source="SUB_LEADER", target="SUB_LEADER"),
        @ValueMapping(source="TEACHER", target="TEACHER")
    })
    StaffRole mapStaffRoleModelToStaffRoleEntity(com.woorinaru.core.model.user.StaffRole staffRoleModel);

    @ValueMappings({
        @ValueMapping(source="LEADER", target="LEADER"),
        @ValueMapping(source="VICE_LEADER", target="VICE_LEADER"),
        @ValueMapping(source="SUB_LEADER", target="SUB_LEADER"),
        @ValueMapping(source="TEACHER", target="TEACHER")
    })
    com.woorinaru.core.model.user.StaffRole mapStaffRoleEntityToStaffRoleModel(StaffRole staffRoleEntity);

    @ValueMappings({
        @ValueMapping(source="PLANNING", target="PLANNING"),
        @ValueMapping(source="DESIGN", target="DESIGN"),
        @ValueMapping(source="MEDIA", target="MEDIA"),
        @ValueMapping(source="EDUCATION", target="EDUCATION")
    })
    Team mapTeamModelToTeamEntity(com.woorinaru.core.model.management.administration.Team teamModel);

    @ValueMappings({
        @ValueMapping(source="PLANNING", target="PLANNING"),
        @ValueMapping(source="DESIGN", target="DESIGN"),
        @ValueMapping(source="MEDIA", target="MEDIA"),
        @ValueMapping(source="EDUCATION", target="EDUCATION")
    })
    com.woorinaru.core.model.management.administration.Team mapTeamEntityToTeamModel(Team teamEntity);
}
