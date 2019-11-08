package com.woorinaru.repository.sql.entity.management.administration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="TutoringClass")
@DiscriminatorValue("T")
public class TutoringClass extends WooriClass {
    @Override
    public Grade getGrade() {
        return Grade.TUTORING;
    }
}
