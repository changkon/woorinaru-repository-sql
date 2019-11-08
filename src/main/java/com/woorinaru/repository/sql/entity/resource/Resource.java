package com.woorinaru.repository.sql.entity.resource;

import javax.persistence.*;

@Entity
@Table(name="RESOURCE")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    private int id;

    @Column(name="RESOURCE")
    @Lob
    private byte[] resource;

    @Column(name="DESCRIPTION")
    private String description;

    public Resource() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getResource() {
        return resource;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
