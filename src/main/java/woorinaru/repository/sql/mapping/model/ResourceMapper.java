package woorinaru.repository.sql.mapping.model;

import woorinaru.repository.sql.entity.resource.Resource;

public class ResourceMapper implements ModelMapper<woorinaru.core.model.management.administration.Resource, Resource> {

    @Override
    public Resource mapToEntity(woorinaru.core.model.management.administration.Resource model) {
        Resource resourceEntity = new Resource();
        resourceEntity.setId(model.getId());
        resourceEntity.setResource(model.getResource());
        return resourceEntity;
    }

    @Override
    public woorinaru.core.model.management.administration.Resource mapToModel(Resource entity) {
        woorinaru.core.model.management.administration.Resource resourceModel = new woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(entity.getId());
        resourceModel.setResource(entity.getResource());
        return resourceModel;
    }
}
