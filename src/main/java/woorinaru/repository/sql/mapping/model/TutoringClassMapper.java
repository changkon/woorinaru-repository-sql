package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.management.administration.TutoringClass;

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
}
