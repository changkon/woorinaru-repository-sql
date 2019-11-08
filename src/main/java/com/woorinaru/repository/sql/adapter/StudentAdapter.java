package com.woorinaru.repository.sql.adapter;

import com.woorinaru.core.model.management.administration.Resource;
import com.woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public class StudentAdapter extends Student {

    private com.woorinaru.repository.sql.entity.user.Student studentEntity;
    private EntityManager em;

    public StudentAdapter(com.woorinaru.repository.sql.entity.user.Student studentEntity, EntityManager em) {
        this.studentEntity = studentEntity;
        this.em = em;
    }

    @Override
    public int getId() {
        return this.studentEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.studentEntity.setId(id);
    }

    @Override
    public String getName() {
        return this.studentEntity.getName();
    }

    @Override
    public String getNationality() {
        return this.studentEntity.getNationality();
    }

    @Override
    public String getEmail() {
        return this.studentEntity.getEmail();
    }

    @Override
    public Collection<Resource> getFavouriteResources() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        this.studentEntity.setName(name);
    }

    @Override
    public void setNationality(String nationality) {
        this.studentEntity.setNationality(nationality);
    }

    @Override
    public void setEmail(String email) {
        this.studentEntity.setEmail(email);
    }

    @Override
    public void setFavouriteResources(Collection<Resource> favouriteResources) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public LocalDateTime getSignUpDateTime() {
        return this.studentEntity.getSignUpDateTime();
    }

    @Override
    public void setSignUpDateTime(LocalDateTime signupDateTime) {
        this.studentEntity.setSignUpDateTime(signupDateTime);
    }

    @Override
    public boolean addFavouriteResource(Resource resource) {
        if (this.studentEntity.getFavouriteResources() == null) {
            this.studentEntity.setFavouriteResources(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(com.woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.studentEntity.getFavouriteResources().add(resourceEntity);
    }

    @Override
    public boolean removeFavouriteResource(int resourceId) {
        if (this.studentEntity.getFavouriteResources() == null) {
            return false;
        }
        return this.studentEntity.getFavouriteResources().removeIf(resource -> resource.getId() == resourceId);
    }
}
