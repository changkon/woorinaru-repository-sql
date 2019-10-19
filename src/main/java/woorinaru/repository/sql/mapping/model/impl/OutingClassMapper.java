package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.OutingClass;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class OutingClassMapper implements ModelMapper<woorinaru.core.model.management.administration.OutingClass, OutingClass> {

    private WooriClassMapper wooriClassMapper;

    public OutingClassMapper() {
        this(new WooriClassMapper());
    }

    public OutingClassMapper(WooriClassMapper wooriClassMapper) {
        this.wooriClassMapper = wooriClassMapper;
    }

    @Override
    public OutingClass mapToEntity(woorinaru.core.model.management.administration.OutingClass model) {
        OutingClass outingClassEntity = new OutingClass();
        wooriClassMapper.mapWooriClassModelToEntity().apply(model, outingClassEntity);
        return outingClassEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.OutingClass mapToModel(OutingClass entity) {
        woorinaru.core.model.management.administration.OutingClass outingClassModel = new woorinaru.core.model.management.administration.OutingClass();
        wooriClassMapper.mapEntityToWooriClassModel().apply(outingClassModel, entity);
        return outingClassModel;
    }

    @Override
    public Class<OutingClass> getEntityClass() {
        return OutingClass.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.OutingClass> getModelClass() {
        return woorinaru.core.model.management.administration.OutingClass.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.OutingClass.class)
            .all()
            .includePartialFieldNames("event")
            .build();
        return mapperContract.equals(contract);
    }
}
