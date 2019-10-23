package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.management.administration.Team;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.StaffRole;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class StaffAdapter extends Staff {

    private woorinaru.repository.sql.entity.user.Staff staffEntity;
    private EntityManager em;

    public StaffAdapter(woorinaru.repository.sql.entity.user.Staff staffEntity, EntityManager em) {
        this.staffEntity = staffEntity;
        this.em = em;
    }

    @Override
    public StaffRole getStaffRole() {
        woorinaru.repository.sql.entity.user.StaffRole staffRoleEntity = staffEntity.getStaffRole();
        switch(staffRoleEntity) {
            case LEADER:
                return StaffRole.LEADER;
            case SUB_LEADER:
                return StaffRole.SUB_LEADER;
            case TEACHER:
                return StaffRole.TEACHER;
            case VICE_LEADER:
                return StaffRole.VICE_LEADER;
        }
        return null;
    }

    @Override
    public void setStaffRole(StaffRole staffRole) {
        switch (staffRole) {
            case LEADER:
                this.staffEntity.setStaffRole(woorinaru.repository.sql.entity.user.StaffRole.LEADER);
            case SUB_LEADER:
                this.staffEntity.setStaffRole(woorinaru.repository.sql.entity.user.StaffRole.SUB_LEADER);
            case TEACHER:
                this.staffEntity.setStaffRole(woorinaru.repository.sql.entity.user.StaffRole.TEACHER);
            case VICE_LEADER:
                this.staffEntity.setStaffRole(woorinaru.repository.sql.entity.user.StaffRole.VICE_LEADER);
        }
    }

    @Override
    public void setTeam(Team team) {
        switch(team) {
            case DESIGN:
                this.staffEntity.setTeam(woorinaru.repository.sql.entity.management.administration.Team.DESIGN);
            case EDUCATION:
                this.staffEntity.setTeam(woorinaru.repository.sql.entity.management.administration.Team.EDUCATION);
            case MEDIA:
                this.staffEntity.setTeam(woorinaru.repository.sql.entity.management.administration.Team.MEDIA);
            case PLANNING:
                this.staffEntity.setTeam(woorinaru.repository.sql.entity.management.administration.Team.PLANNING);
        }
    }

    @Override
    public Team getTeam() {
        woorinaru.repository.sql.entity.management.administration.Team teamEntity = staffEntity.getTeam();
        switch(teamEntity) {
            case DESIGN:
                return Team.DESIGN;
            case EDUCATION:
                return Team.EDUCATION;
            case MEDIA:
                return Team.MEDIA;
            case PLANNING:
                return Team.PLANNING;
        }
        return null;
    }

    @Override
    public int getId() {
        return this.staffEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.staffEntity.setId(id);
    }

    @Override
    public String getName() {
        return this.staffEntity.getName();
    }

    @Override
    public String getNationality() {
        return this.staffEntity.getNationality();
    }

    @Override
    public String getEmail() {
        return this.staffEntity.getEmail();
    }

    @Override
    public Collection<Resource> getFavouriteResources() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setName(String name) {
        this.staffEntity.setName(name);
    }

    @Override
    public void setNationality(String nationality) {
        this.staffEntity.setNationality(nationality);
    }

    @Override
    public void setEmail(String email) {
        this.staffEntity.setEmail(email);
    }

    @Override
    public void setFavouriteResources(Collection<Resource> favouriteResources) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public LocalDateTime getSignUpDateTime() {
        return this.staffEntity.getSignUpDateTime();
    }

    @Override
    public void setSignUpDateTime(LocalDateTime signupDateTime) {
        this.staffEntity.setSignUpDateTime(signupDateTime);
    }

    @Override
    public boolean addFavouriteResource(Resource resource) {
        if (this.staffEntity.getFavouriteResources() == null) {
            this.staffEntity.setFavouriteResources(new ArrayList<>());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.staffEntity.getFavouriteResources().add(resourceEntity);
    }

    @Override
    public boolean removeFavouriteResource(int resourceId) {
        if (this.staffEntity.getFavouriteResources() == null) {
            return false;
        }
        return this.staffEntity.getFavouriteResources().removeIf(resource -> resource.getId() == resourceId);
    }
}
