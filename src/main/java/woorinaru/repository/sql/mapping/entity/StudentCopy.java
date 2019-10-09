package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.user.Student;

public class StudentCopy implements Copy<Student> {

    private UserCopy userCopy;

    public StudentCopy() {
        this(new UserCopy());
    }

    public StudentCopy(UserCopy userCopy) {
        this.userCopy = userCopy;
    }

    @Override
    public void copy(Student src, Student dest) {
        // User specific details
        userCopy.copy(src, dest);
    }
}
