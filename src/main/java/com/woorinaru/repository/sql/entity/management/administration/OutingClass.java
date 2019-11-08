package com.woorinaru.repository.sql.entity.management.administration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="OutingClass")
@DiscriminatorValue("O")
public class OutingClass extends WooriClass {
    @Override
    public Grade getGrade() {
        return Grade.OUTING;
    }
}
