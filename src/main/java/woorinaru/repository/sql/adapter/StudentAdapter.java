package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Student;

import java.time.LocalDateTime;
import java.util.Collection;

public class StudentAdapter extends Student {

    private woorinaru.repository.sql.entity.user.Student studentEntity;

    public StudentAdapter(woorinaru.repository.sql.entity.user.Student studentEntity) {
        this.studentEntity = studentEntity;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getNationality() {
        return super.getNationality();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public Collection<Resource> getFavouriteResources() {
        return super.getFavouriteResources();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setNationality(String nationality) {
        super.setNationality(nationality);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setFavouriteResources(Collection<Resource> favouriteResources) {
        super.setFavouriteResources(favouriteResources);
    }

    @Override
    public LocalDateTime getSignUpDateTime() {
        return super.getSignUpDateTime();
    }

    @Override
    public void setSignUpDateTime(LocalDateTime signupDateTime) {
        super.setSignUpDateTime(signupDateTime);
    }
}
