package woorinaru.repository.sql.mapping.model.factory;

import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.model.*;
import woorinaru.repository.sql.mapping.model.impl.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperFactory {

    Collection<ModelMapper> modelMappers;

    public ModelMapperFactory() {
        modelMappers = new ArrayList<>();
        // add all mappers
        modelMappers.addAll(getAllClassMappers());
        modelMappers.addAll(getAllUserMappers());
        modelMappers.addAll(getAllTermMappers());
        modelMappers.addAll(getAllResourceMappers());
        modelMappers.addAll(getAllTeamMappers());
        modelMappers.addAll(getAllEventMappers());
    }

    private Collection<ModelMapper> getAllClassMappers() {
        return List.of(new BeginnerClassMapper(), new IntermediateClassMapper(), new OutingClassMapper(), new TutoringClassMapper());
    }

    private Collection<ModelMapper> getAllUserMappers() {
        return List.of(new AdminMapper(), new StaffMapper(), new StudentMapper());
    }

    private Collection<ModelMapper> getAllTermMappers() {
        return List.of(new TermMapper());
    }

    private Collection<ModelMapper> getAllResourceMappers() {
        return List.of(new ResourceMapper());
    }

    private Collection<ModelMapper> getAllTeamMappers() {
        return List.of(new TeamMapper());
    }

    private Collection<ModelMapper> getAllEventMappers() {
        return List.of(new EventMapper());
    }

    <M extends ModelMapper<?, ?>> M getModelMapper(Class<M> classType, ModelContract contract) {
        Collection<M> filteredModelMappers = modelMappers.stream()
            .filter(classType::isInstance)
            .map(classType::cast)
            .collect(Collectors.toList());
        return filteredModelMappers.stream()
            .filter(mapper -> mapper.isSatisfiedBy(contract))
            .findFirst()
            .orElse(null);
    }

}
