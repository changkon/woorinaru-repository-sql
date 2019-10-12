package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.user.Admin;

public class AdminCopy implements Copy<Admin> {

    private UserCopy userCopy;

    public AdminCopy() {
        this(new UserCopy());
    }

    public AdminCopy(UserCopy userCopy) {
        this.userCopy = userCopy;
    }

    @Override
    public void copy(Admin src, Admin dest) {
        this.userCopy.copy(src, dest);
    }
}
