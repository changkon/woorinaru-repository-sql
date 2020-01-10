package com.woorinaru.repository.sql.entity.user;


import com.woorinaru.repository.sql.entity.resource.Resource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="USER")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    protected int id;

    @Column(name="NAME")
    protected String name;

    @Column(name="NATIONALITY")
    protected String nationality;

    @Column(name="EMAIL")
    protected String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="USER_RESOURCE",
        joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID")
    )
    protected Collection<Resource> favouriteResources;

    @Column(name="SIGNUPDATETIME", columnDefinition="TIMESTAMP")
    protected LocalDateTime signUpDateTime;

    @Column(name="CREATEDATETIME", columnDefinition="TIMESTAMP")
    protected LocalDateTime createDateTime;

    @Column(name="UPDATEDATETIME", columnDefinition="TIMESTAMP")
    protected LocalDateTime updateDateTime;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Resource> getFavouriteResources() {
        return favouriteResources;
    }

    public void setFavouriteResources(Collection<Resource> favouriteResources) {
        this.favouriteResources = favouriteResources;
    }

    public LocalDateTime getSignUpDateTime() {
        return signUpDateTime;
    }

    public void setSignUpDateTime(LocalDateTime signUpDateTime) {
        this.signUpDateTime = signUpDateTime;
    }

    public boolean addFavouriteResource(Resource resource) {
        if (favouriteResources == null) {
            this.favouriteResources = new ArrayList<>();
        }
        return this.favouriteResources.add(resource);
    }

    public boolean removeFavouriteResource(int resourceId) {
        if (favouriteResources == null) {
            return false;
        }
        return this.favouriteResources.removeIf(resource -> resource.getId() == resourceId);
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @PrePersist
    protected void onCreate() {
        this.createDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Transient
    abstract public String getDiscriminatorValue();
}
