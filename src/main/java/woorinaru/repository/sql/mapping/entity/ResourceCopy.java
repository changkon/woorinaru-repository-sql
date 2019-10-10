package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.resource.Resource;

public class ResourceCopy implements Copy<Resource> {

    @Override
    public void copy(Resource src, Resource dest) {
        dest.setId(src.getId());
        dest.setResource(src.getResource());
        dest.setDescription(src.getDescription());
    }
}
