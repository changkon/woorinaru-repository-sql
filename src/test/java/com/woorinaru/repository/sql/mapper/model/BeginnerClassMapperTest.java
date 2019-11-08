package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import com.woorinaru.repository.sql.entity.management.administration.Event;
import com.woorinaru.repository.sql.entity.management.administration.Team;
import com.woorinaru.repository.sql.entity.resource.Resource;
import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.StaffRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BeginnerClassMapperTest {

    @Test
    public void testBeginnerClassModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = new com.woorinaru.core.model.management.administration.BeginnerClass();
        beginnerClassModel.setId(1);

        com.woorinaru.core.model.management.administration.Resource resourceModel = new com.woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(1);
        resourceModel.setDescription("test resource description");
        resourceModel.setResource("test resource".getBytes());

        beginnerClassModel.setResources(List.of(resourceModel));

        com.woorinaru.core.model.user.Staff staffModel = new com.woorinaru.core.model.user.Staff();
        staffModel.setId(1);
        staffModel.setName("Test Name");
        staffModel.setEmail("test@test.com");
        staffModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(14, 5, 0)));
        staffModel.setFavouriteResources(Collections.emptyList());
        staffModel.setTeam(com.woorinaru.core.model.management.administration.Team.PLANNING);
        staffModel.setStaffRole(com.woorinaru.core.model.user.StaffRole.TEACHER);
        beginnerClassModel.setStaff(List.of(staffModel));

        beginnerClassModel.setStudents(Collections.emptyList());

        com.woorinaru.core.model.management.administration.Event eventModel = new com.woorinaru.core.model.management.administration.Event();
        eventModel.setId(1);
        eventModel.setDescription("test event description");
        eventModel.setAddress("test address");
        beginnerClassModel.setEvent(eventModel);

        // WHEN
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        BeginnerClass beginnerClassEntity = mapper.mapToEntity(beginnerClassModel);

        // THEN
        assertThat(beginnerClassEntity.getId()).isEqualTo(1);
        Assertions.assertThat(beginnerClassEntity.getResources()).isNullOrEmpty();

//        Resource resourceEntity = beginnerClassEntity.getResources().iterator().next();
//
//        assertThat(resourceEntity.getId()).isEqualTo(1);
//        assertThat(resourceEntity.getDescription()).isEqualTo("test resource description");
//        assertThat(resourceEntity.getResource()).isNull();

        assertThat(beginnerClassEntity.getStaff()).isNullOrEmpty();

        assertThat(beginnerClassEntity.getStudents()).isNullOrEmpty();

        assertThat(beginnerClassEntity.getEvent().getId()).isEqualTo(1);
        assertThat(beginnerClassEntity.getEvent().getDescription()).isNull();
        assertThat(beginnerClassEntity.getEvent().getAddress()).isNull();
        assertThat(beginnerClassEntity.getEvent().getStartDateTime()).isNull();
        assertThat(beginnerClassEntity.getEvent().getEndDateTime()).isNull();
        assertThat(beginnerClassEntity.getEvent().getStudentReservations()).isNullOrEmpty();
        assertThat(beginnerClassEntity.getEvent().getWooriClasses()).isNullOrEmpty();
    }

    @Test
    public void testBeginnerClassEntityToModel() {
        // GIVEN
        BeginnerClass beginnerClassEntity = new BeginnerClass();
        beginnerClassEntity.setId(1);

        Resource resourceEntity = new Resource();
        resourceEntity.setId(1);
        resourceEntity.setDescription("test resource description");
        resourceEntity.setResource("test resource".getBytes());

        beginnerClassEntity.setResources(List.of(resourceEntity));

        Staff staffEntity = new Staff();
        staffEntity.setId(1);
        staffEntity.setName("Test Name");
        staffEntity.setEmail("test@test.com");
        staffEntity.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(14, 5, 0)));
        staffEntity.setFavouriteResources(Collections.emptyList());
        staffEntity.setTeam(Team.PLANNING);
        staffEntity.setStaffRole(StaffRole.TEACHER);
        beginnerClassEntity.setStaff(List.of(staffEntity));

        beginnerClassEntity.setStudents(Collections.emptyList());

        Event eventEntity = new Event();
        eventEntity.setId(1);
        eventEntity.setDescription("test event description");
        eventEntity.setAddress("test address");
        beginnerClassEntity.setEvent(eventEntity);

        // WHEN
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        com.woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = mapper.mapToModel(beginnerClassEntity);

        // THEN
        assertThat(beginnerClassModel.getId()).isEqualTo(1);
        assertThat(beginnerClassModel.getResources()).hasSize(1);

        com.woorinaru.core.model.management.administration.Resource resourceModel = beginnerClassModel.getResources().iterator().next();

        assertThat(resourceModel.getId()).isEqualTo(1);
        assertThat(resourceModel.getDescription()).isEqualTo("test resource description");
        assertThat(resourceModel.getResource()).isNull();

        assertThat(beginnerClassModel.getStaff()).hasSize(1);

        assertThat(beginnerClassModel.getStudents()).isEmpty();

        assertThat(beginnerClassModel.getEvent().getId()).isEqualTo(1);
        assertThat(beginnerClassModel.getEvent().getDescription()).isNull();
        assertThat(beginnerClassModel.getEvent().getAddress()).isNull();
        assertThat(beginnerClassModel.getEvent().getStartDateTime()).isNull();
        assertThat(beginnerClassModel.getEvent().getEndDateTime()).isNull();
        assertThat(beginnerClassModel.getEvent().getStudentReservations()).isNullOrEmpty();
        assertThat(beginnerClassModel.getEvent().getWooriClasses()).isNullOrEmpty();
    }

}
