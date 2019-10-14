package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.management.administration.BeginnerClass;

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
}
