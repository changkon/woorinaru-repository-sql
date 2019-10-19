package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class BeginnerClassMapper implements ModelMapper<woorinaru.core.model.management.administration.BeginnerClass, BeginnerClass> {

    private WooriClassMapper mapper;

    public BeginnerClassMapper() {
        this(new WooriClassMapper());
    }

    public BeginnerClassMapper(WooriClassMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeginnerClass mapToEntity(woorinaru.core.model.management.administration.BeginnerClass model) {
        BeginnerClass beginnerClassEntity = new BeginnerClass();
        mapper.mapWooriClassModelToEntity().apply(model, beginnerClassEntity);
        return beginnerClassEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.BeginnerClass mapToModel(BeginnerClass entity) {
        woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = new woorinaru.core.model.management.administration.BeginnerClass();
        mapper.mapEntityToWooriClassModel().apply(beginnerClassModel, entity);
        return beginnerClassModel;
    }

    @Override
    public Class<BeginnerClass> getEntityClass() {
        return BeginnerClass.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.BeginnerClass> getModelClass() {
        return woorinaru.core.model.management.administration.BeginnerClass.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.BeginnerClass.class)
            .all()
            .includePartialFieldNames("event")
            .build();
        return mapperContract.equals(contract);
    }
}
