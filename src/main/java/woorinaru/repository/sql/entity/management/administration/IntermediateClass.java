package woorinaru.repository.sql.entity.management.administration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="IntermediateClass")
@DiscriminatorValue("I")
public class IntermediateClass extends WooriClass {
    @Override
    public String getDiscriminatorValue() {
        return "I";
    }
}
