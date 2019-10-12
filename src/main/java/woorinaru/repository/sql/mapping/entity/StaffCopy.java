package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.user.Staff;

public class StaffCopy implements Copy<Staff> {
    private UserCopy userCopy;

    public StaffCopy() {
        this(new UserCopy());
    }

    public StaffCopy(UserCopy userCopy) {
        this.userCopy = userCopy;
    }

    @Override
    public void copy(Staff src, Staff dest) {
        this.userCopy.copy(src, dest);
        // Staff specific mapping
        dest.setStaffRole(src.getStaffRole());
        dest.setTeam(src.getTeam());
    }
}
