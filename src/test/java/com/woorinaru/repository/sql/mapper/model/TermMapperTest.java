package com.woorinaru.repository.sql.mapper.model;

import com.woorinaru.repository.sql.entity.management.administration.Event;
import com.woorinaru.repository.sql.entity.management.administration.Term;
import com.woorinaru.repository.sql.entity.user.Staff;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TermMapperTest {

    @Test
    public void testTermEntityToModel() {
        // GIVEN
        Term termEntity = new Term();
        termEntity.setId(1);
        termEntity.setTerm(1);
        termEntity.setStartDate(LocalDate.of(2019, 1, 10));
        termEntity.setEndDate(LocalDate.of(2019, 4, 10));

        Event eventEntity = new Event();
        eventEntity.setId(1);
        eventEntity.setDescription("test description 1");

        Staff staffEntity1 = new Staff();
        staffEntity1.setId(1);
        staffEntity1.setName("Test Staff 1");

        Staff staffEntity2 = new Staff();
        staffEntity2.setId(2);
        staffEntity2.setName("Test Staff 2");

        termEntity.setEvents(List.of(eventEntity));
        termEntity.setStaffMembers(List.of(staffEntity1, staffEntity2));

        // WHEN
        TermMapper mapper = TermMapper.MAPPER;
        com.woorinaru.core.model.management.administration.Term termModel = mapper.mapToModel(termEntity);

        // THEN
        assertThat(termModel.getId()).isEqualTo(1);
        assertThat(termModel.getTerm()).isEqualTo(1);
        assertThat(termModel.getStartDate()).isEqualTo(LocalDate.of(2019, 1, 10));
        assertThat(termModel.getEndDate()).isEqualTo(LocalDate.of(2019, 4, 10));

        assertThat(termModel.getEvents()).hasSize(1);
        com.woorinaru.core.model.management.administration.Event eventModel = termModel.getEvents().iterator().next();
        assertThat(eventModel.getId()).isEqualTo(1);
        assertThat(eventModel.getDescription()).isNull();

        Iterator<com.woorinaru.core.model.user.Staff> iter = termModel.getStaffMembers().iterator();

        com.woorinaru.core.model.user.Staff staffModel1 = iter.next();
        com.woorinaru.core.model.user.Staff staffModel2 = iter.next();

        assertThat(staffModel1.getId()).isEqualTo(1);
        assertThat(staffModel1.getName()).isNull();

        assertThat(staffModel2.getId()).isEqualTo(2);
        assertThat(staffModel2.getName()).isNull();
    }

    @Test
    public void testTermModelToEntity() {
        // GIVEN
        com.woorinaru.core.model.management.administration.Term termModel = new com.woorinaru.core.model.management.administration.Term();
        termModel.setId(1);
        termModel.setTerm(1);
        termModel.setStartDate(LocalDate.of(2019, 1, 10));
        termModel.setEndDate(LocalDate.of(2019, 4, 10));

        com.woorinaru.core.model.management.administration.Event eventModel = new com.woorinaru.core.model.management.administration.Event();
        eventModel.setId(1);
        eventModel.setDescription("test description 1");

        com.woorinaru.core.model.user.Staff staffModel1 = new com.woorinaru.core.model.user.Staff();
        staffModel1.setId(1);
        staffModel1.setName("Test Staff 1");

        com.woorinaru.core.model.user.Staff staffModel2 = new com.woorinaru.core.model.user.Staff();
        staffModel2.setId(2);
        staffModel2.setName("Test Staff 2");

        termModel.setEvents(List.of(eventModel));
        termModel.setStaffMembers(List.of(staffModel1, staffModel2));

        // WHEN
        TermMapper mapper = TermMapper.MAPPER;
        Term termEntity = mapper.mapToEntity(termModel);

        // THEN
        assertThat(termEntity.getId()).isEqualTo(1);
        assertThat(termEntity.getTerm()).isEqualTo(1);
        assertThat(termEntity.getStartDate()).isEqualTo(LocalDate.of(2019, 1, 10));
        assertThat(termEntity.getEndDate()).isEqualTo(LocalDate.of(2019, 4, 10));

        assertThat(termEntity.getEvents()).isNullOrEmpty();
        assertThat(termEntity.getStaffMembers()).isNullOrEmpty();
    }
}
