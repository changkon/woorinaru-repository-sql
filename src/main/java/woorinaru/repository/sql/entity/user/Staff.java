package woorinaru.repository.sql.entity.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Staff")
@DiscriminatorValue("T")
public class Staff extends User {

    // TODO add enums for Team and Staff Role

    @Override
    public String getDiscriminatorValue() {
        return "T";
    }
}
