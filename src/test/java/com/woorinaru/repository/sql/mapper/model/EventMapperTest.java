package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.BeginnerClass;
import com.woorinaru.repository.sql.entity.management.administration.Event;
import com.woorinaru.repository.sql.entity.user.Student;
import org.junit.jupiter.api.Test;

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
        com.woorinaru.core.model.management.administration.Event eventModel = mapper.mapToModel(eventEntity);

        // THEN
        assertThat(eventModel.getId()).isEqualTo(1);
        assertThat(eventModel.getAddress()).isEqualTo("test event entity address");
        assertThat(eventModel.getDescription()).isEqualTo("test event entity description");
        assertThat(eventModel.getStartDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        assertThat(eventModel.getEndDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        assertThat(eventModel.getWooriClasses()).hasSize(1);
        assertThat(eventModel.getWooriClasses().iterator().next()).isInstanceOf(com.woorinaru.core.model.management.administration.BeginnerClass.class);

        assertThat(eventModel.getStudentReservations()).hasSize(2);

        Iterator<com.woorinaru.core.model.user.Student> iter = eventModel.getStudentReservations().iterator();

        com.woorinaru.core.model.user.Student studentModel1 = iter.next();
        assertThat(studentModel1.getId()).isEqualTo(1);
        assertThat(studentModel1.getName()).isNull();

        com.woorinaru.core.model.user.Student studentModel2 = iter.next();
        assertThat(studentModel2.getId()).isEqualTo(2);
        assertThat(studentModel2.getName()).isNull();
    }

    @Test
    public void testEventModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.management.administration.Event eventModel = new com.woorinaru.core.model.management.administration.Event();
        eventModel.setId(1);
        eventModel.setAddress("test event entity address");
        eventModel.setDescription("test event entity description");
        eventModel.setStartDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(14, 0, 0)));
        eventModel.setEndDateTime(LocalDateTime.of(LocalDate.of(2019, 5, 1), LocalTime.of(20, 0, 0)));

        com.woorinaru.core.model.management.administration.BeginnerClass beginnerClassModel = new com.woorinaru.core.model.management.administration.BeginnerClass();
        beginnerClassModel.setId(1);

        eventModel.setWooriClasses(List.of(beginnerClassModel));

        com.woorinaru.core.model.user.Student studentModel1 = new com.woorinaru.core.model.user.Student();
        studentModel1.setId(1);
        studentModel1.setName("Henry Walker");

        com.woorinaru.core.model.user.Student studentModel2 = new com.woorinaru.core.model.user.Student();
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

        assertThat(eventEntity.getWooriClasses()).isNullOrEmpty();
        assertThat(eventEntity.getStudentReservations()).isNullOrEmpty();
    }

}
