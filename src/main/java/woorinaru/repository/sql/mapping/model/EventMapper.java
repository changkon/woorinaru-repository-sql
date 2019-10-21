package woorinaru.repository.sql.mapping.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import woorinaru.repository.sql.entity.management.administration.*;
import woorinaru.repository.sql.entity.user.Student;

@Mapper
public interface EventMapper {

    EventMapper MAPPER = Mappers.getMapper(EventMapper.class);

    Event mapToEntity(woorinaru.core.model.management.administration.Event eventModel);

    woorinaru.core.model.management.administration.Event mapToModel(Event eventEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    Student mapToEntity(woorinaru.core.model.user.Student studentModel);

    @Mapping(source="id", target="id")
    @Mapping(target="name", ignore=true)
    @Mapping(target="nationality", ignore=true)
    @Mapping(target="email", ignore=true)
    @Mapping(target="favouriteResources", ignore=true)
    @Mapping(target="signUpDateTime", ignore=true)
    woorinaru.core.model.user.Student mapToEntity(Student studentEntity);

    default WooriClass mapToEntity(woorinaru.core.model.management.administration.WooriClass wooriClassModel) {
        if (wooriClassModel instanceof woorinaru.core.model.management.administration.BeginnerClass) {
            return mapToEntity((woorinaru.core.model.management.administration.BeginnerClass) wooriClassModel);
        }

        if (wooriClassModel instanceof woorinaru.core.model.management.administration.TutoringClass) {
            return mapToEntity((woorinaru.core.model.management.administration.TutoringClass) wooriClassModel);
        }

        if (wooriClassModel instanceof woorinaru.core.model.management.administration.OutingClass) {
            return mapToEntity((woorinaru.core.model.management.administration.OutingClass) wooriClassModel);
        }

        if (wooriClassModel instanceof woorinaru.core.model.management.administration.IntermediateClass) {
            return mapToEntity((woorinaru.core.model.management.administration.IntermediateClass) wooriClassModel);
        }

        return null;
    }

    default woorinaru.core.model.management.administration.WooriClass mapToModel(WooriClass wooriClassEntity) {
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
    BeginnerClass mapToEntity(woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    woorinaru.core.model.management.administration.BeginnerClass mapToModel(BeginnerClass beginnerClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    TutoringClass mapToEntity(woorinaru.core.model.management.administration.TutoringClass tutoringClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    woorinaru.core.model.management.administration.TutoringClass mapToModel(TutoringClass tutoringClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    OutingClass mapToEntity(woorinaru.core.model.management.administration.OutingClass outingClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    woorinaru.core.model.management.administration.OutingClass mapToModel(OutingClass outingClassEntity);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    IntermediateClass mapToEntity(woorinaru.core.model.management.administration.IntermediateClass intermediateClassModel);

    @Mapping(source="id", target="id")
    @Mapping(target="resources", ignore=true)
    @Mapping(target="staff", ignore=true)
    @Mapping(target="students", ignore=true)
    @Mapping(target="event", ignore=true)
    woorinaru.core.model.management.administration.IntermediateClass mapToModel(IntermediateClass intermediateClassEntity);
}
