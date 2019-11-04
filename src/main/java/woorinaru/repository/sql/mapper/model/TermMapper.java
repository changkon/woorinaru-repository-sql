package woorinaru.repository.sql.mapper.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.management.administration.Term;
import woorinaru.repository.sql.entity.user.Staff;

@Mapper
public interface TermMapper {

    TermMapper MAPPER = Mappers.getMapper(TermMapper.class);

    @Mapping(target="staffMembers", ignore=true)
    @Mapping(target="events", ignore=true)
    Term mapToEntity(woorinaru.core.model.management.administration.Term termModel);

    woorinaru.core.model.management.administration.Term mapToModel(Term termEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    Staff mapToEntity(woorinaru.core.model.user.Staff staffModel);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    woorinaru.core.model.user.Staff mapToModel(Staff staffEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="startDateTime", ignore=true)
    @Mapping(target="endDateTime", ignore=true)
    @Mapping(target="address", ignore=true)
    @Mapping(target="description", ignore=true)
    @Mapping(target="wooriClasses", ignore=true)
    @Mapping(target="studentReservations", ignore=true)
    Event mapToEntity(woorinaru.core.model.management.administration.Event eventModel);

    @Mapping(source="id", target="id")
    @Mapping(target="startDateTime", ignore=true)
    @Mapping(target="endDateTime", ignore=true)
    @Mapping(target="address", ignore=true)
    @Mapping(target="description", ignore=true)
    @Mapping(target="wooriClasses", ignore=true)
    @Mapping(target="studentReservations", ignore=true)
    woorinaru.core.model.management.administration.Event mapToModel(Event eventEntity);
}
