package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses={StudentMapper.class, StaffMapper.class})
public interface BeginnerClassMapper {

    BeginnerClassMapper MAPPER = Mappers.getMapper(BeginnerClassMapper.class);

    @Mapping(source="event.id", target="event.id")
    @Mapping(target="event.startDateTime", ignore=true)
    @Mapping(target="event.endDateTime", ignore=true)
    @Mapping(target="event.address", ignore=true)
    @Mapping(target="event.description", ignore=true)
    @Mapping(target="event.wooriClasses", ignore=true)
    @Mapping(target="event.studentReservations", ignore=true)
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    BeginnerClass mapToEntity(com.woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel);

    @Mapping(source="event.id", target="event.id")
    @Mapping(target="event.startDateTime", ignore=true)
    @Mapping(target="event.endDateTime", ignore=true)
    @Mapping(target="event.address", ignore=true)
    @Mapping(target="event.description", ignore=true)
    @Mapping(target="event.wooriClasses", ignore=true)
    @Mapping(target="event.studentReservations", ignore=true)
    com.woorinaru.core.model.management.administration.BeginnerClass mapToModel(BeginnerClass beginnerClassEntity);
}
