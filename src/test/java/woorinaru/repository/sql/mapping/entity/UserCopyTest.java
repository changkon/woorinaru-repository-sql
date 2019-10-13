package woorinaru.repository.sql.mapping.entity;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCopyTest {

    private UserCopy copy = new UserCopy();

    @Test
    public void testFullyPopulatedUserCopy() {
        // GIVEN
        // Create user
        User copyUser = new MockUser();
        copyUser.setId(1);
        copyUser.setNationality("South Korea");
        copyUser.setEmail("test@random.com");
        copyUser.setName("No Name");
        copyUser.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 7, 8), LocalTime.of(10, 10, 10)));

        Resource resourceContainer = new Resource();
        resourceContainer.setId(1);
        String resourceContent = new String("content");
        resourceContainer.setResource(resourceContent.getBytes());
        copyUser.setFavouriteResources(List.of(resourceContainer));

        // WHEN
        User destUser = new MockUser();
        copy.copy(copyUser, destUser);

        // THEN
        assertThat(destUser.getId()).isEqualTo(1);
        assertThat(destUser.getNationality()).isEqualTo("South Korea");
        assertThat(destUser.getEmail()).isEqualTo("test@random.com");
        assertThat(destUser.getName()).isEqualTo("No Name");
        assertThat(destUser.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 7, 8), LocalTime.of(10, 10, 10)));

        assertThat(destUser.getFavouriteResources()).isNotNull();
        assertThat(destUser.getFavouriteResources()).hasSize(1);
        Resource destResourceContainer = destUser.getFavouriteResources().iterator().next();
        assertThat(destResourceContainer.getId()).isEqualTo(1);
        assertThat(destResourceContainer.getResource()).isEqualTo(resourceContent.getBytes());
    }

    // For now, include a Mock User. Doesn't need a full mock library
    private static class MockUser extends User {
        @Override
        public String getDiscriminatorValue() {
            return "M";
        }
    }

}
