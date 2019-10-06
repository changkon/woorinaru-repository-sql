package woorinaru.repository.sql.entity.resource;

import javax.persistence.*;

@Entity
@Table(name="RESOURCE")
public class Resource {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID", updatable=false, nullable=false)
    private int id;

    @Column(name="RESOURCE")
    private byte[] resource;

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
}
