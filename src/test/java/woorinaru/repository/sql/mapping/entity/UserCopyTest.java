package woorinaru.repository.sql.mapping.entity;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.User;

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

        assertThat(destUser.getFavouriteResources()).isNotNull();
        assertThat(destUser.getFavouriteResources()).hasSize(1);
        Resource destResourceContainer = destUser.getFavouriteResources().get(0);
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
