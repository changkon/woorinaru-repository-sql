package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.management.administration.TutoringClass;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class TutoringClassMapper implements ModelMapper<woorinaru.core.model.management.administration.TutoringClass, TutoringClass> {

    private WooriClassMapper wooriClassMapper;

    public TutoringClassMapper() {
        this(new WooriClassMapper());
    }

    public TutoringClassMapper(WooriClassMapper wooriClassMapper) {
        this.wooriClassMapper = wooriClassMapper;
    }

    @Override
    public TutoringClass mapToEntity(woorinaru.core.model.management.administration.TutoringClass model) {
        TutoringClass tutoringClassEntity = new TutoringClass();
        wooriClassMapper.mapWooriClassModelToEntity().apply(model, tutoringClassEntity);
        return tutoringClassEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.TutoringClass mapToModel(TutoringClass entity) {
        woorinaru.core.model.management.administration.TutoringClass tutoringClassModel = new woorinaru.core.model.management.administration.TutoringClass();
        wooriClassMapper.mapEntityToWooriClassModel().apply(tutoringClassModel, entity);
        return tutoringClassModel;
    }

    @Override
    public Class<TutoringClass> getEntityClass() {
        return TutoringClass.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.TutoringClass> getModelClass() {
        return woorinaru.core.model.management.administration.TutoringClass.class;
    }

    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.TutoringClass.class)
            .all()
            .includePartialFieldNames("event")
            .build();
        return mapperContract.equals(contract);
    }
}
