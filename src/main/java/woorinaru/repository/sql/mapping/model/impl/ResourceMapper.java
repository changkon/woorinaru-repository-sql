package woorinaru.repository.sql.mapping.model.impl;

import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.mapping.contract.ModelContract;
import woorinaru.repository.sql.mapping.contract.ModelContractBuilder;
import woorinaru.repository.sql.mapping.model.ModelMapper;

public class ResourceMapper implements ModelMapper<woorinaru.core.model.management.administration.Resource, Resource> {

    @Override
    public Resource mapToEntity(woorinaru.core.model.management.administration.Resource model) {
        Resource resourceEntity = new Resource();
        resourceEntity.setId(model.getId());
        resourceEntity.setResource(model.getResource());
        resourceEntity.setDescription(model.getDescription());
        return resourceEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.Resource mapToModel(Resource entity) {
        woorinaru.core.model.management.administration.Resource resourceModel = new woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(entity.getId());
        resourceModel.setResource(entity.getResource());
        resourceModel.setDescription(entity.getDescription());
        return resourceModel;
    }

    @Override
    public Class<Resource> getEntityClass() {
        return Resource.class;
    }

    @Override
    public Class<woorinaru.core.model.management.administration.Resource> getModelClass() {
        return woorinaru.core.model.management.administration.Resource.class;
    }


    @Override
    public boolean isSatisfiedBy(ModelContract contract) {
        ModelContract mapperContract = new ModelContractBuilder(woorinaru.core.model.management.administration.Resource.class)
            .all()
            .build();
        return mapperContract.equals(contract);
    }
}
