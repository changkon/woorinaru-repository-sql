package com.woorinaru.repository.sql.entity.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Student")
@DiscriminatorValue("S")
public class Student extends User {

    @Override
    public String getDiscriminatorValue() {
        return "S";
    }
}
