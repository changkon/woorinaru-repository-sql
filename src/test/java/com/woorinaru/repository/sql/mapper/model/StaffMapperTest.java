package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.Team;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.StaffRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class StaffMapperTest {

    @Test
    public void testStaffModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.user.Staff staffModel = new com.woorinaru.core.model.user.Staff();
        staffModel.setId(1);
        staffModel.setName("Test Staff Name");
        staffModel.setEmail("test email");
        staffModel.setNationality("New Zealand");
        staffModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        staffModel.setFavouriteResources(Collections.emptyList());
        staffModel.setStaffRole(com.woorinaru.core.model.user.StaffRole.LEADER);
        staffModel.setTeam(com.woorinaru.core.model.management.administration.Team.PLANNING);

        // WHEN
        StaffMapper mapper = StaffMapper.MAPPER;
        Staff staffEntity = mapper.mapToEntity(staffModel);

        // THEN
        assertThat(staffEntity.getId()).isEqualTo(1);
        assertThat(staffEntity.getName()).isEqualTo("Test Staff Name");
        assertThat(staffEntity.getEmail()).isEqualTo("test email");
        assertThat(staffEntity.getNationality()).isEqualTo("New Zealand");
        assertThat(staffEntity.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(staffEntity.getFavouriteResources()).isNullOrEmpty();
        assertThat(staffEntity.getStaffRole()).isEqualTo(StaffRole.LEADER);
        assertThat(staffEntity.getTeam()).isEqualTo(Team.PLANNING);
    }

    @Test
    public void testStaffEntityToModel() {
        // GIVEN
        Staff staffEntity = new Staff();
        staffEntity.setId(1);
        staffEntity.setName("Test Staff Name");
        staffEntity.setEmail("test email");
        staffEntity.setNationality("New Zealand");
        staffEntity.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        staffEntity.setFavouriteResources(Collections.emptyList());
        staffEntity.setStaffRole(StaffRole.LEADER);
        staffEntity.setTeam(Team.PLANNING);

        // WHEN
        StaffMapper mapper = StaffMapper.MAPPER;
        com.woorinaru.core.model.user.Staff staffModel = mapper.mapToModel(staffEntity);

        // THEN
        assertThat(staffModel.getId()).isEqualTo(1);
        assertThat(staffModel.getName()).isEqualTo("Test Staff Name");
        assertThat(staffModel.getEmail()).isEqualTo("test email");
        assertThat(staffModel.getNationality()).isEqualTo("New Zealand");
        assertThat(staffModel.getSignUpDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(10, 1, 1)));
        assertThat(staffModel.getFavouriteResources()).isEmpty();
        assertThat(staffModel.getStaffRole()).isEqualTo(com.woorinaru.core.model.user.StaffRole.LEADER);
        assertThat(staffModel.getTeam()).isEqualTo(com.woorinaru.core.model.management.administration.Team.PLANNING);
    }

}
