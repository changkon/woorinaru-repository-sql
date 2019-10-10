package woorinaru.repository.sql.entity.user;


import woorinaru.repository.sql.entity.resource.Resource;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="USER")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="USER_TYPE")
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

    // TODO ensure cascade works as intended. Needs tests
    @ManyToMany(fetch = FetchType.LAZY,  cascade=CascadeType.ALL)
    @JoinTable(name="USER_RESOURCE", joinColumns = @JoinColumn(name = "USER_ID"),
        inverseJoinColumns = @JoinColumn(name = "RESOURCE_ID"))
    protected List<Resource> favouriteResources;

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

    public List<Resource> getFavouriteResources() {
        return favouriteResources;
    }

    public void setFavouriteResources(List<Resource> favouriteResources) {
        this.favouriteResources = favouriteResources;
    }

    @Transient
    abstract public String getDiscriminatorValue();
}
