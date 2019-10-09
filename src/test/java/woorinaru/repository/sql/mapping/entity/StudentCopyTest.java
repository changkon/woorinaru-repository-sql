package woorinaru.repository.sql.mapping.entity;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.user.Student;

import static org.mockito.Mockito.*;

public class StudentCopyTest {
    @Test
    public void testUserCopyIsCalled() {
        // GIVEN
        Student copyStudent = new Student();
        Student destStudent = new Student();

        UserCopy userCopy = mock(UserCopy.class);
        StudentCopy studentCopy = new StudentCopy(userCopy);

        // WHEN
        studentCopy.copy(copyStudent, destStudent);

        // THEN
        verify(userCopy, times(1)).copy(copyStudent, destStudent);
    }
}
