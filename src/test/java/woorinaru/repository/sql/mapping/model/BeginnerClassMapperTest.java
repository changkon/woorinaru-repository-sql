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
        // TODO
    }

}
