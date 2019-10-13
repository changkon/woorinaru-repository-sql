package woorinaru.repository.sql.mapping.model;

import org.junit.jupiter.api.Test;
import woorinaru.core.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    @Test
    public void testUserModelIsMappedToEntity() {
        // GIVEN
        UserMapper mapper = new UserMapper();
        User userModel = new MockUserModel();
        userModel.setId(1);
        userModel.setName("Mock model name");
        userModel.setNationality("South Korea");
        userModel.setEmail("mock@test.com");
        userModel.setFavouriteResources(Collections.emptyList());
        userModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 15), LocalTime.of(10, 45, 0)));

        // WHEN
        woorinaru.repository.sql.entity.user.User userEntity = new MockUserEntity();
        mapper.mapUserModelToEntity().apply(userModel, userEntity);

        // THEN
        assertThat(userEntity.getId()).isEqualTo(1);
        assertThat(userEntity.getName()).isEqualTo("Mock model name");
        assertThat(userEntity.getNationality()).isEqualTo("South Korea");
        assertThat(userEntity.getEmail()).isEqualTo("mock@test.com");
        assertThat(userEntity.getFavouriteResources()).isEmpty();
        assertThat(userEntity.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 15), LocalTime.of(10, 45, 0)));
    }

    @Test
    public void testUserEntityIsMappedToModel() {
        // GIVEN
        UserMapper mapper = new UserMapper();
        woorinaru.repository.sql.entity.user.User userEntity = new MockUserEntity();

        userEntity.setId(1);
        userEntity.setName("Mock model name");
        userEntity.setNationality("South Korea");
        userEntity.setEmail("mock@test.com");
        userEntity.setFavouriteResources(Collections.emptyList());
        userEntity.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 15), LocalTime.of(10, 45, 0)));

        // WHEN
        User userModel = new MockUserModel();
        mapper.mapEntityToUserModel().apply(userModel, userEntity);

        // THEN
        assertThat(userModel.getId()).isEqualTo(1);
        assertThat(userModel.getName()).isEqualTo("Mock model name");
        assertThat(userModel.getNationality()).isEqualTo("South Korea");
        assertThat(userModel.getEmail()).isEqualTo("mock@test.com");
        assertThat(userModel.getFavouriteResources()).isEmpty();
        assertThat(userModel.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 15), LocalTime.of(10, 45, 0)));

    }

    private class MockUserModel extends User {

    }

    private class MockUserEntity extends woorinaru.repository.sql.entity.user.User {

        @Override
        public String getDiscriminatorValue() {
            return "M";
        }
    }

}
