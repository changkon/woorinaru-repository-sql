package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.management.administration.IntermediateClass;

@Mapper(uses={PartialResourceMapper.class, StudentMapper.class, StaffMapper.class})
public interface IntermediateClassMapper {

    IntermediateClassMapper MAPPER = Mappers.getMapper(IntermediateClassMapper.class);

    @Mapping(source="event.id", target="event.id")
    @Mapping(target="event.startDateTime", ignore=true)
    @Mapping(target="event.endDateTime", ignore=true)
    @Mapping(target="event.address", ignore=true)
    @Mapping(target="event.description", ignore=true)
    @Mapping(target="event.wooriClasses", ignore=true)
    @Mapping(target="event.studentReservations", ignore=true)
    IntermediateClass mapToEntity(woorinaru.core.model.management.administration.IntermediateClass intermediateClassModel);

    @Mapping(source="event.id", target="event.id")
    @Mapping(target="event.startDateTime", ignore=true)
    @Mapping(target="event.endDateTime", ignore=true)
    @Mapping(target="event.address", ignore=true)
    @Mapping(target="event.description", ignore=true)
    @Mapping(target="event.wooriClasses", ignore=true)
    @Mapping(target="event.studentReservations", ignore=true)
    woorinaru.core.model.management.administration.IntermediateClass mapToModel(IntermediateClass intermediateClassEntity);

}
