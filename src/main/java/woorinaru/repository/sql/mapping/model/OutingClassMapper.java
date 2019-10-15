package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.management.administration.OutingClass;

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
}
