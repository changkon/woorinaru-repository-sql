package woorinaru.repository.sql.mapping.entity;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.management.administration.Team;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.StaffRole;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class StaffCopyTest {
    @Test
    public void testStaffIsCopied() {
        // GIVEN
        // GIVEN
        Staff copyStaff = new Staff();
        Staff destStaff = new Staff();

        copyStaff.setTeam(Team.PLANNING);
        copyStaff.setStaffRole(StaffRole.TEACHER);

        UserCopy userCopy = mock(UserCopy.class);
        StaffCopy staffCopy = new StaffCopy(userCopy);

        // WHEN
        staffCopy.copy(copyStaff, destStaff);

        // THEN
        verify(userCopy, times(1)).copy(copyStaff, destStaff);
        assertThat(destStaff.getTeam()).isEqualTo(Team.PLANNING);
        assertThat(destStaff.getStaffRole()).isEqualTo(StaffRole.TEACHER);
    }
}
