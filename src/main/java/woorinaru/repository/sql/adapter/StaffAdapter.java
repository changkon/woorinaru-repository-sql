package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.management.administration.Team;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.StaffRole;

import java.time.LocalDateTime;
import java.util.Collection;

public class StaffAdapter extends Staff {

    private woorinaru.repository.sql.entity.user.Staff staffEntity;

    public StaffAdapter(woorinaru.repository.sql.entity.user.Staff staffEntity) {
        this.staffEntity = staffEntity;
    }

    @Override
    public StaffRole getStaffRole() {
        return super.getStaffRole();
    }

    @Override
    public void setStaffRole(StaffRole staffRole) {
        super.setStaffRole(staffRole);
    }

    @Override
    public void setTeam(Team team) {
        super.setTeam(team);
    }

    @Override
    public Team getTeam() {
        return super.getTeam();
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
