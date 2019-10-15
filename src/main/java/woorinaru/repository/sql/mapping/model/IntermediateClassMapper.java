package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.management.administration.IntermediateClass;

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
}
