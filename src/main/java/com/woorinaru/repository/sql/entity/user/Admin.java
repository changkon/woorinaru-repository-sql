package com.woorinaru.repository.sql.entity.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Admin")
@DiscriminatorValue("A")
public class Admin extends User {

    @Override
    public String getDiscriminatorValue() {
        return "A";
    }
}
