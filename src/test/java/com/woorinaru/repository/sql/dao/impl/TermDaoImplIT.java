package com.woorinaru.repository.sql.dao.impl;

import com.woorinaru.core.dao.spi.EventDao;
import com.woorinaru.core.dao.spi.TermDao;
import com.woorinaru.core.model.management.administration.Event;
import com.woorinaru.core.model.management.administration.Term;
import com.woorinaru.repository.sql.dao.helper.DatabaseContainerRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.woorinaru.repository.sql.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DatabaseContainerRule.class)
public class TermDaoImplIT extends AbstractContainerDatabaseIT {

    // TODO complete with more tests

    @Test
    @DisplayName("Test unidirectional one to many relationship between term and events")
    public void testTermAndEventRelationship() {
        // GIVEN
        // Create events
        Event eventModel1 = new Event();
        eventModel1.setStartDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 1), LocalTime.of(12, 0, 0)));
        eventModel1.setEndDateTime(LocalDateTime.of(LocalDate.of(2019, 1, 1), LocalTime.of(16, 0, 0)));
        eventModel1.setDescription("Event 1");
        eventModel1.setAddress("서울특별시 마포구 와우산로 35길 7 지하1층");

        Event eventModel2 = new Event();
        eventModel2.setStartDateTime(LocalDateTime.of(LocalDate.of(2019, 8, 1), LocalTime.of(12, 0, 0)));
        eventModel2.setEndDateTime(LocalDateTime.of(LocalDate.of(2019, 8, 1), LocalTime.of(16, 0, 0)));
        eventModel2.setDescription("Event 2");
        eventModel2.setAddress("서울특별시 마포구 와우산로 35길 7 지하1층");

        EntityManager em = EntityManagerFactoryUtil.getEntityManager();

        EventDao eventDao = new EventDaoImpl(em);
        executeInTransaction().accept(em, () -> eventDao.create(eventModel1));
        executeInTransaction().accept(em, () -> eventDao.create(eventModel2));

        eventModel1.setId(1);
        eventModel2.setId(2);

        Term termModel = new Term();
        termModel.setTerm(1);
        termModel.setStartDate(LocalDate.of(2019, 10, 10));
        termModel.setEndDate(LocalDate.of(2019, 12, 1));
        List<Event> list = new ArrayList<>();
        list.add(eventModel1);
        list.add(eventModel2);
        termModel.setEvents(list);

        // WHEN
        TermDao termDao = new TermDaoImpl(em);
        executeInTransaction().accept(em, () -> termDao.create(termModel));

        executeInTransaction().accept(em, () -> {
           List<Event> events = eventDao.getAll();
           assertThat(events).hasSize(2);
           List<Event> termEvents = termDao.get(1).getEvents();
           assertThat(termEvents).hasSize(2);
        });

        termModel.setId(1);
        termModel.getEvents().removeIf(event -> event.getId() == 1);
        executeInTransaction().accept(em, () -> termDao.modify(termModel));

        // THEN
        executeInTransaction().accept(em, () -> {
            List<Event> termEvents = termDao.get(1).getEvents();
            assertThat(termEvents).hasSize(1);
            List<Event> events = eventDao.getAll();
            assertThat(events).hasSize(1);
        });

        em.close();
    }
}
