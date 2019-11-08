package com.woorinaru.repository.sql.entity.management.administration;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="BeginnerClass")
@DiscriminatorValue("B")
public class BeginnerClass extends WooriClass {
    @Override
    public Grade getGrade() {
        return Grade.BEGINNER;
    }
}
