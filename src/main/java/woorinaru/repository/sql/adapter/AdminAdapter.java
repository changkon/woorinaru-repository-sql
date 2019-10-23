package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Admin;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AdminAdapter extends Admin {

    private woorinaru.repository.sql.entity.user.Admin adminEntity;
    private EntityManager em;

    public AdminAdapter(woorinaru.repository.sql.entity.user.Admin adminEntity, EntityManager em) {
        this.adminEntity = adminEntity;
        this.em = em;
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
        throw new AdapterUnsupportedOperationException();
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
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public LocalDateTime getSignUpDateTime() {
        return this.adminEntity.getSignUpDateTime();
    }

    @Override
    public void setSignUpDateTime(LocalDateTime signupDateTime) {
        this.adminEntity.setSignUpDateTime(signUpDateTime);
    }

    @Override
    public boolean addFavouriteResource(Resource resource) {
        if (this.adminEntity.getFavouriteResources() == null) {
            this.adminEntity.setFavouriteResources(new ArrayList<>());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.adminEntity.getFavouriteResources().add(resourceEntity);
    }

    @Override
    public boolean removeFavouriteResource(int resourceId) {
        if (this.adminEntity.getFavouriteResources() == null) {
            return false;
        }
        return this.adminEntity.getFavouriteResources().removeIf(resource -> resource.getId() == resourceId);
    }
}
