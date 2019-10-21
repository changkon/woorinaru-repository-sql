package woorinaru.repository.sql.mapping.model;

import org.junit.jupiter.api.Test;
import woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import woorinaru.repository.sql.entity.management.administration.Event;
import woorinaru.repository.sql.entity.user.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EventMapperTest {

    @Test
    public void testEventEntityToModel() {
        // GIVEN
        Event eventEntity = new Event();
        eventEntity.setId(1);
        eventEntity.setAddress("test event entity address");
        eventEntity.setDescription("test event entity description");
        eventEntity.setStartDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        eventEntity.setEndDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        BeginnerClass beginnerClassEntity = new BeginnerClass();
        beginnerClassEntity.setId(1);

        eventEntity.setWooriClasses(List.of(beginnerClassEntity));

        Student studentEntity1 = new Student();
        studentEntity1.setId(1);
        studentEntity1.setName("Henry Walker");

        Student studentEntity2 = new Student();
        studentEntity2.setId(2);
        studentEntity2.setName("Michael Foster");

        eventEntity.setStudentReservations(List.of(studentEntity1, studentEntity2));

        // WHEN
        EventMapper mapper = EventMapper.MAPPER;
        woorinaru.core.model.management.administration.Event eventModel = mapper.mapToModel(eventEntity);

        // THEN
        assertThat(eventModel.getId()).isEqualTo(1);
        assertThat(eventModel.getAddress()).isEqualTo("test event entity address");
        assertThat(eventModel.getDescription()).isEqualTo("test event entity description");
        assertThat(eventModel.getStartDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        assertThat(eventModel.getEndDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        assertThat(eventModel.getWooriClasses()).hasSize(1);
        assertThat(eventModel.getWooriClasses().iterator().next()).isInstanceOf(woorinaru.core.model.management.administration.BeginnerClass.class);

        assertThat(eventModel.getStudentReservations()).hasSize(2);

        Iterator<woorinaru.core.model.user.Student> iter = eventModel.getStudentReservations().iterator();

        woorinaru.core.model.user.Student studentModel1 = iter.next();
        assertThat(studentModel1.getId()).isEqualTo(1);
        assertThat(studentModel1.getName()).isNull();

        woorinaru.core.model.user.Student studentModel2 = iter.next();
        assertThat(studentModel2.getId()).isEqualTo(2);
        assertThat(studentModel2.getName()).isNull();
    }

    @Test
    public void testEventModelToEntity() {
        // GIVEN
        woorinaru.core.model.management.administration.Event eventModel = new woorinaru.core.model.management.administration.Event();
        eventModel.setId(1);
        eventModel.setAddress("test event entity address");
        eventModel.setDescription("test event entity description");
        eventModel.setStartDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        eventModel.setEndDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = new woorinaru.core.model.management.administration.BeginnerClass();
        beginnerClassModel.setId(1);

        eventModel.setWooriClasses(List.of(beginnerClassModel));

        woorinaru.core.model.user.Student studentModel1 = new woorinaru.core.model.user.Student();
        studentModel1.setId(1);
        studentModel1.setName("Henry Walker");

        woorinaru.core.model.user.Student studentModel2 = new woorinaru.core.model.user.Student();
        studentModel2.setId(2);
        studentModel2.setName("Michael Foster");

        eventModel.setStudentReservations(List.of(studentModel1, studentModel2));

        // WHEN
        EventMapper mapper = EventMapper.MAPPER;
        Event eventEntity = mapper.mapToEntity(eventModel);

        // THEN
        assertThat(eventEntity.getId()).isEqualTo(1);
        assertThat(eventEntity.getAddress()).isEqualTo("test event entity address");
        assertThat(eventEntity.getDescription()).isEqualTo("test event entity description");
        assertThat(eventEntity.getStartDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        assertThat(eventEntity.getEndDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        assertThat(eventEntity.getWooriClasses()).hasSize(1);
        assertThat(eventEntity.getWooriClasses().iterator().next()).isInstanceOf(BeginnerClass.class);

        assertThat(eventEntity.getStudentReservations()).hasSize(2);

        Iterator<Student> iter = eventEntity.getStudentReservations().iterator();

        Student studentEntity1 = iter.next();
        assertThat(studentEntity1.getId()).isEqualTo(1);
        assertThat(studentEntity1.getName()).isNull();

        Student studentEntity2 = iter.next();
        assertThat(studentEntity2.getId()).isEqualTo(2);
        assertThat(studentEntity2.getName()).isNull();
    }

}
