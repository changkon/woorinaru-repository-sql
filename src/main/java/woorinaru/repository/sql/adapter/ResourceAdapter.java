package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Resource;

public class ResourceAdapter extends Resource {

    private woorinaru.repository.sql.entity.resource.Resource resourceEntity;

    public ResourceAdapter(woorinaru.repository.sql.entity.resource.Resource resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @Override
    public int getId() {
        return this.resourceEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.resourceEntity.setId(id);
    }

    @Override
    public byte[] getResource() {
        return this.resourceEntity.getResource();
    }

    @Override
    public String getDescription() {
        return this.resourceEntity.getDescription();
    }

    @Override
    public void setResource(byte[] resource) {
        this.resourceEntity.setResource(resource);
    }

    @Override
    public void setDescription(String description) {
        this.resourceEntity.setDescription(description);
    }
}
