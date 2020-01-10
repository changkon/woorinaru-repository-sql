package com.woorinaru.repository.sql.entity.user;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name="Student")
@DiscriminatorValue("S")
public class Student extends User {

    @Column(name="GUEST")
    private boolean isGuest;

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    @Override
    public String getDiscriminatorValue() {
        return "S";
    }
}
