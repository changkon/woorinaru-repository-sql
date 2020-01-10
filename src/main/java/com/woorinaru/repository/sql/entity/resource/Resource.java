package com.woorinaru.repository.sql.entity.resource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name="RESOURCE")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    private int id;

    @Column(name="LOCATION")
    private String location;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="CREATEDATETIME", columnDefinition="TIMESTAMP")
    private LocalDateTime createDateTime;

    @Column(name="UPDATEDATETIME", columnDefinition="TIMESTAMP")
    private LocalDateTime updateDateTime;

    public Resource() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }
}
