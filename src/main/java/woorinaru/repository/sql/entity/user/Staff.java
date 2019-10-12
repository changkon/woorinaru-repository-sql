package woorinaru.repository.sql.entity.user;

import woorinaru.repository.sql.entity.management.administration.Team;

import javax.persistence.*;

@Entity(name="Staff")
@DiscriminatorValue("T")
public class Staff extends User {

    @Enumerated(EnumType.STRING)
    @Column(name="STAFF_ROLE")
    private StaffRole staffRole;

    @Enumerated(EnumType.STRING)
    @Column(name="TEAM")
    private Team team;

    @Override
    public String getDiscriminatorValue() {
        return "T";
    }

    public StaffRole getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(StaffRole staffRole) {
        this.staffRole = staffRole;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
