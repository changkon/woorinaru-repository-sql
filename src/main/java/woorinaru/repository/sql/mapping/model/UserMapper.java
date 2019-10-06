package woorinaru.repository.sql.mapping.model;


import woorinaru.core.model.user.User;
import woorinaru.repository.sql.entity.resource.Resource;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class UserMapper {

    private ResourceMapper resourceMapper;

    public UserMapper() {
        resourceMapper = new ResourceMapper();
    }

    public BiFunction<User, woorinaru.repository.sql.entity.user.User, woorinaru.repository.sql.entity.user.User> mapUserModelToEntity() {
        return (model, entity) -> {
            entity.setNationality(model.getNationality());
            entity.setName(model.getName());
            List<Resource> resourceEntities = model.getFavouriteResources().stream()
                .map(resourceMapper::mapToEntity)
                .collect(Collectors.toList());
            entity.setFavouriteResources(resourceEntities);
            entity.setId(model.getId());
            entity.setEmail(model.getEmail());
            return entity;
        };
    }

    public BiFunction<User, woorinaru.repository.sql.entity.user.User, User> mapEntityToUserModel() {
        return (model, entity) -> {
            model.setEmail(entity.getEmail());
            model.setId(entity.getId());
            model.setName(entity.getName());
            model.setNationality(entity.getNationality());
            List<woorinaru.core.model.management.administration.Resource> resourceModels = entity.getFavouriteResources().stream()
                .map(resourceMapper::mapToModel)
                .collect(Collectors.toList());
            model.setFavouriteResources(resourceModels);
            return model;
        };
    }
}
