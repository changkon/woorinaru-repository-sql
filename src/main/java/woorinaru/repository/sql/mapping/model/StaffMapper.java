package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.management.administration.Team;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.StaffRole;

@Mapper(uses=PartialResourceMapper.class)
public interface StaffMapper {

    StaffMapper MAPPER = Mappers.getMapper(StaffMapper.class);

    @Mapping(target="favouriteResources", ignore=true)
    Staff mapToEntity(woorinaru.core.model.user.Staff staffModel);

    woorinaru.core.model.user.Staff mapToModel(Staff staffEntity);

    @ValueMappings({
        @ValueMapping(source="LEADER", target="LEADER"),
        @ValueMapping(source="VICE_LEADER", target="VICE_LEADER"),
        @ValueMapping(source="SUB_LEADER", target="SUB_LEADER"),
        @ValueMapping(source="TEACHER", target="TEACHER")
    })
    StaffRole mapStaffRoleModelToStaffRoleEntity(woorinaru.core.model.user.StaffRole staffRoleModel);

    @ValueMappings({
        @ValueMapping(source="LEADER", target="LEADER"),
        @ValueMapping(source="VICE_LEADER", target="VICE_LEADER"),
        @ValueMapping(source="SUB_LEADER", target="SUB_LEADER"),
        @ValueMapping(source="TEACHER", target="TEACHER")
    })
    woorinaru.core.model.user.StaffRole mapStaffRoleEntityToStaffRoleModel(StaffRole staffRoleEntity);

    @ValueMappings({
        @ValueMapping(source="PLANNING", target="PLANNING"),
        @ValueMapping(source="DESIGN", target="DESIGN"),
        @ValueMapping(source="MEDIA", target="MEDIA"),
        @ValueMapping(source="EDUCATION", target="EDUCATION")
    })
    Team mapTeamModelToTeamEntity(woorinaru.core.model.management.administration.Team teamModel);

    @ValueMappings({
        @ValueMapping(source="PLANNING", target="PLANNING"),
        @ValueMapping(source="DESIGN", target="DESIGN"),
        @ValueMapping(source="MEDIA", target="MEDIA"),
        @ValueMapping(source="EDUCATION", target="EDUCATION")
    })
    woorinaru.core.model.management.administration.Team mapTeamEntityToTeamModel(Team teamEntity);
}
