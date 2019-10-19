package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.*;
import woorinaru.repository.sql.entity.user.Student;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventMapper implements ModelMapper<woorinaru.core.model.management.administration.Event, Event> {

    private StudentMapper studentMapper;
    private WooriClassMapperProxy wooriClassMapperProxy;

    public EventMapper() {
        this(new StudentMapper());
    }

    public EventMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
        this.wooriClassMapperProxy = new WooriClassMapperProxy();
    }

    @Override
    public Event mapToEntity(woorinaru.core.model.management.administration.Event model) {
        Event eventEntity = new Event();
        eventEntity.setId(model.getId());
        eventEntity.setAddress(model.getAddress());
        eventEntity.setStartDateTime(model.getStartDateTime());
        eventEntity.setEndDateTime(model.getEndDateTime());
        eventEntity.setDescription(model.getDescription());

        Collection<Student> studentEntities = model.getStudentReservations()
            .stream()
            .map(studentMapper::mapToEntity)
            .collect(Collectors.toList());

        eventEntity.setStudentReservations(studentEntities);

        Collection<WooriClass> wooriClasses = model.getWooriClasses()
            .stream()
            .map(wooriClassMapperProxy::mapToEntity)
            .collect(Collectors.toList());

        eventEntity.setWooriClasses(wooriClasses);

        return null;
    }

    @Override
    public woorinaru.core.model.management.administration.Event mapToModel(Event entity) {

        woorinaru.core.model.management.administration.Event eventModel = new woorinaru.core.model.management.administration.Event();
        eventModel.setId(entity.getId());
        eventModel.setAddress(entity.getAddress());
        eventModel.setStartDateTime(entity.getStartDateTime());
        eventModel.setEndDateTime(entity.getEndDateTime());
        eventModel.setDescription(entity.getDescription());

        Collection<woorinaru.core.model.user.Student> studentModels = entity.getStudentReservations()
            .stream()
            .map(studentMapper::mapToModel)
            .collect(Collectors.toList());

        eventModel.setStudentReservations(studentModels);

        Collection<woorinaru.core.model.management.administration.WooriClass> wooriClassModels = entity.getWooriClasses()
            .stream()
            .map(wooriClassMapperProxy::mapToModel)
            .collect(Collectors.toList());

        eventModel.setWooriClasses(wooriClassModels);

        return null;
    }

    @Override
    public Class<Event> getEntityClass() {
        return Event.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.Event> getModelClass() {
        return woorinaru.core.model.management.administration.Event.class;
    }

    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.Event.class)
            .all()
            .includePartialFieldNames("wooriClasses")
            .build();
        return mapperContract.equals(contract);
    }

    private class WooriClassMapperProxy implements ModelMapper<woorinaru.core.model.management.administration.WooriClass, WooriClass> {

        private Collection<Function<woorinaru.core.model.management.administration.WooriClass, WooriClass>> entityMapFunctions;
        private Collection<Function<WooriClass, woorinaru.core.model.management.administration.WooriClass>> modelMapFunctions;

        public WooriClassMapperProxy() {
            this.entityMapFunctions = initialiseEntityMapFunctions();
            this.modelMapFunctions = initialiseModelMapFunctions();
        }

        private Collection<Function<woorinaru.core.model.management.administration.WooriClass, WooriClass>> initialiseEntityMapFunctions() {
            Collection<Function<woorinaru.core.model.management.administration.WooriClass, WooriClass>> functionCollection = new ArrayList<>();

            functionCollection.add(c -> c.getClass().isInstance(woorinaru.core.model.management.administration.BeginnerClass.class) ? (new BeginnerClassMapper()).mapToEntity(woorinaru.core.model.management.administration.BeginnerClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(woorinaru.core.model.management.administration.IntermediateClass.class) ? (new IntermediateClassMapper()).mapToEntity(woorinaru.core.model.management.administration.IntermediateClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(woorinaru.core.model.management.administration.OutingClass.class) ? (new OutingClassMapper()).mapToEntity(woorinaru.core.model.management.administration.OutingClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(woorinaru.core.model.management.administration.TutoringClass.class) ? (new TutoringClassMapper()).mapToEntity(woorinaru.core.model.management.administration.TutoringClass.class.cast(c)) : null);

            return functionCollection;
        }

        private Collection<Function<WooriClass, woorinaru.core.model.management.administration.WooriClass>> initialiseModelMapFunctions() {
            Collection<Function<WooriClass, woorinaru.core.model.management.administration.WooriClass>> functionCollection = new ArrayList<>();

            functionCollection.add(c -> c.getClass().isInstance(BeginnerClass.class) ? (new BeginnerClassMapper()).mapToModel(BeginnerClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(IntermediateClass.class) ? (new IntermediateClassMapper()).mapToModel(IntermediateClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(OutingClass.class) ? (new OutingClassMapper()).mapToModel(OutingClass.class.cast(c)) : null);
            functionCollection.add(c -> c.getClass().isInstance(TutoringClass.class) ? (new TutoringClassMapper()).mapToModel(TutoringClass.class.cast(c)) : null);

            return functionCollection;
        }

        @Override
        public WooriClass mapToEntity(woorinaru.core.model.management.administration.WooriClass model) {

            return this.entityMapFunctions.stream()
                .map(f -> f.apply(model))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        }

        @Override
        public woorinaru.core.model.management.administration.WooriClass mapToModel(WooriClass entity) {
            return this.modelMapFunctions.stream()
                .map(f -> f.apply(entity))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        }

        @Override
        public Class<WooriClass> getEntityClass() {
            return WooriClass.class;
        }

        @Override
        public Class<woorinaru.core.model.management.administration.WooriClass> getModelClass() {
            return woorinaru.core.model.management.administration.WooriClass.class;
        }

        @Override
        public boolean isSatisfiedBy(ModelContract contract) {
            return true;
        }
    }
}
