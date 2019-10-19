package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.IntermediateClass;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class IntermediateClassMapper implements ModelMapper<woorinaru.core.model.management.administration.IntermediateClass, IntermediateClass> {

    private WooriClassMapper wooriClassMapper;

    public IntermediateClassMapper() {
        this(new WooriClassMapper());
    }

    public IntermediateClassMapper(WooriClassMapper wooriClassMapper) {
        this.wooriClassMapper = wooriClassMapper;
    }

    @Override
    public IntermediateClass mapToEntity(woorinaru.core.model.management.administration.IntermediateClass model) {
        IntermediateClass intermediateClassEntity = new IntermediateClass();
        wooriClassMapper.mapWooriClassModelToEntity().apply(model, intermediateClassEntity);
        return intermediateClassEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.IntermediateClass mapToModel(IntermediateClass entity) {
        woorinaru.core.model.management.administration.IntermediateClass intermediateClassModel = new woorinaru.core.model.management.administration.IntermediateClass();
        wooriClassMapper.mapEntityToWooriClassModel().apply(intermediateClassModel, entity);
        return intermediateClassModel;
    }

    @Override
    public Class<IntermediateClass> getEntityClass() {
        return IntermediateClass.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.IntermediateClass> getModelClass() {
        return woorinaru.core.model.management.administration.IntermediateClass.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.IntermediateClass.class)
            .all()
            .includePartialFieldNames("event")
            .build();
        return mapperContract.equals(contract);
    }
}
