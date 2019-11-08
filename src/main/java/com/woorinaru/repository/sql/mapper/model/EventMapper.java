package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.*;
import com.woorinaru.repository.sql.entity.user.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {

    EventMapper MAPPER = Mappers.getMapper(EventMapper.class);

    @Mapping(target="wooriClasses", ignore=true)
    @Mapping(target="studentReservations", ignore=true)
    Event mapToEntity(com.woorinaru.core.model.management.administration.Event eventModel);

    com.woorinaru.core.model.management.administration.Event mapToModel(Event eventEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    Student mapToEntity(com.woorinaru.core.model.user.Student studentModel);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    com.woorinaru.core.model.user.Student mapToModel(Student studentEntity);

    default WooriClass mapToEntity(com.woorinaru.core.model.management.administration.WooriClass wooriClassModel) {
        if (wooriClassModel instanceof com.woorinaru.core.model.management.administration.BeginnerClass) {
            return mapToEntity((com.woorinaru.core.model.management.administration.BeginnerClass) wooriClassModel);
        }

        if (wooriClassModel instanceof com.woorinaru.core.model.management.administration.TutoringClass) {
            return mapToEntity((com.woorinaru.core.model.management.administration.TutoringClass) wooriClassModel);
        }

        if (wooriClassModel instanceof com.woorinaru.core.model.management.administration.OutingClass) {
            return mapToEntity((com.woorinaru.core.model.management.administration.OutingClass) wooriClassModel);
        }

        if (wooriClassModel instanceof com.woorinaru.core.model.management.administration.IntermediateClass) {
            return mapToEntity((com.woorinaru.core.model.management.administration.IntermediateClass) wooriClassModel);
        }

        return null;
    }

    default com.woorinaru.core.model.management.administration.WooriClass mapToModel(WooriClass wooriClassEntity) {
        if (wooriClassEntity instanceof BeginnerClass) {
            return mapToModel((BeginnerClass) wooriClassEntity);
        }

        if (wooriClassEntity instanceof TutoringClass) {
            return mapToModel((TutoringClass) wooriClassEntity);
        }

        if (wooriClassEntity instanceof OutingClass) {
            return mapToModel((OutingClass) wooriClassEntity);
        }

        if (wooriClassEntity instanceof IntermediateClass) {
            return mapToModel((IntermediateClass) wooriClassEntity);
        }

        return null;
    }

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    BeginnerClass mapToEntity(com.woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    com.woorinaru.core.model.management.administration.BeginnerClass mapToModel(BeginnerClass beginnerClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    TutoringClass mapToEntity(com.woorinaru.core.model.management.administration.TutoringClass tutoringClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    com.woorinaru.core.model.management.administration.TutoringClass mapToModel(TutoringClass tutoringClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    OutingClass mapToEntity(com.woorinaru.core.model.management.administration.OutingClass outingClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    com.woorinaru.core.model.management.administration.OutingClass mapToModel(OutingClass outingClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    IntermediateClass mapToEntity(com.woorinaru.core.model.management.administration.IntermediateClass intermediateClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    com.woorinaru.core.model.management.administration.IntermediateClass mapToModel(IntermediateClass intermediateClassEntity);
}
