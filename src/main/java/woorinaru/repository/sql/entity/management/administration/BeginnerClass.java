package woorinaru.repository.sql.entity.management.administration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="BeginnerClass")
@DiscriminatorValue("B")
public class BeginnerClass extends WooriClass {
    @Override
    public String getDiscriminatorValue() {
        return "B";
    }
}
