package woorinaru.repository.sql.adapter;

import org.mapstruct.factory.Mappers;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Admin;
import woorinaru.repository.sql.mapping.model.PartialResourceMapper;
import woorinaru.repository.sql.mapping.model.ResourceMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

public class AdminAdapter extends Admin {

    private woorinaru.repository.sql.entity.user.Admin adminEntity;

    public AdminAdapter(woorinaru.repository.sql.entity.user.Admin adminEntity) {
        this.adminEntity = adminEntity;
    }

    @Override
    public int getId() {
        return this.adminEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.adminEntity.setId(id);
    }

    @Override
    public String getName() {
        return this.adminEntity.getName();
    }

    @Override
    public String getNationality() {
        return this.adminEntity.getNationality();
    }

    @Override
    public String getEmail() {
        return this.adminEntity.getEmail();
    }

    @Override
    public Collection<Resource> getFavouriteResources() {
        PartialResourceMapper mapper = Mappers.getMapper(PartialResourceMapper.class);
        return this.adminEntity.getFavouriteResources()
            .stream()
            .map(mapper::mapToModel)
            .collect(Collectors.toList());
    }

    @Override
    public void setName(String name) {
        this.adminEntity.setNationality(name);
    }

    @Override
    public void setNationality(String nationality) {
        this.adminEntity.setNationality(nationality);
    }

    @Override
    public void setEmail(String email) {
        this.adminEntity.setEmail(email);
    }

    @Override
    public void setFavouriteResources(Collection<Resource> favouriteResources) {
        ResourceMapper mapper = Mappers.getMapper(ResourceMapper.class);
        Collection<woorinaru.repository.sql.entity.resource.Resource> resourceEntities = favouriteResources.stream()
            .map(mapper::mapToEntity)
            .collect(Collectors.toList());
        this.adminEntity.setFavouriteResources(resourceEntities);
    }

    @Override
    public LocalDateTime getSignUpDateTime() {
        return this.adminEntity.getSignUpDateTime();
    }

    @Override
    public void setSignUpDateTime(LocalDateTime signupDateTime) {
        this.adminEntity.setSignUpDateTime(signUpDateTime);
    }
}
