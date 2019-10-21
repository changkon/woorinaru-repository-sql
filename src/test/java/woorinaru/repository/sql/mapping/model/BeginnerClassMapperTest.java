package woorinaru.repository.sql.mapping.model;

import org.junit.jupiter.api.Test;
import woorinaru.core.model.management.administration.Team;
import woorinaru.core.model.user.StaffRole;
import woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;

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
        woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = new woorinaru.core.model.management.administration.BeginnerClass();
        beginnerClassModel.setId(1);

        woorinaru.core.model.management.administration.Resource resourceModel = new woorinaru.core.model.management.administration.Resource();
        resourceModel.setId(1);
        resourceModel.setDescription("test resource description");
        resourceModel.setResource("test resource".getBytes());

        beginnerClassModel.setResources(List.of(resourceModel));

        woorinaru.core.model.user.Staff staffModel = new woorinaru.core.model.user.Staff();
        staffModel.setId(1);
        staffModel.setName("Test Name");
        staffModel.setEmail("test@test.com");
        staffModel.setSignUpDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 10), LocalTime.of(14, 5, 0)));
        staffModel.setFavouriteResources(Collections.emptyList());
        staffModel.setTeam(woorinaru.core.model.management.administration.Team.PLANNING);
        staffModel.setStaffRole(woorinaru.core.model.user.StaffRole.TEACHER);
        beginnerClassModel.setStaff(List.of(staffModel));

        beginnerClassModel.setStudents(Collections.emptyList());

        woorinaru.core.model.management.administration.Event eventModel = new woorinaru.core.model.management.administration.Event();
        eventModel.setId(1);
        eventModel.setDescription("test event description");
        eventModel.setAddress("test address");
        beginnerClassModel.setEvent(eventModel);

        // WHEN
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        BeginnerClass beginnerClassEntity = mapper.mapToEntity(beginnerClassModel);

        // THEN
        assertThat(beginnerClassEntity.getId()).isEqualTo(1);
        assertThat(beginnerClassEntity.getResources()).hasSize(1);

        Resource resourceEntity = beginnerClassEntity.getResources().iterator().next();

        assertThat(resourceEntity.getId()).isEqualTo(1);
        assertThat(resourceEntity.getDescription()).isEqualTo("test resource description");
        assertThat(resourceEntity.getResource()).isNull();

        assertThat(beginnerClassEntity.getStaff()).hasSize(1);

        assertThat(beginnerClassEntity.getStudents()).isEmpty();

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
        staffEntity.setTeam(woorinaru.repository.sql.entity.management.administration.Team.PLANNING);
        staffEntity.setStaffRole(woorinaru.repository.sql.entity.user.StaffRole.TEACHER);
        beginnerClassEntity.setStaff(List.of(staffEntity));

        beginnerClassEntity.setStudents(Collections.emptyList());

        Event eventEntity = new Event();
        eventEntity.setId(1);
        eventEntity.setDescription("test event description");
        eventEntity.setAddress("test address");
        beginnerClassEntity.setEvent(eventEntity);

        // WHEN
        BeginnerClassMapper mapper = BeginnerClassMapper.MAPPER;
        woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = mapper.mapToModel(beginnerClassEntity);

        // THEN
        assertThat(beginnerClassModel.getId()).isEqualTo(1);
        assertThat(beginnerClassModel.getResources()).hasSize(1);

        woorinaru.core.model.management.administration.Resource resourceModel = beginnerClassModel.getResources().iterator().next();

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
