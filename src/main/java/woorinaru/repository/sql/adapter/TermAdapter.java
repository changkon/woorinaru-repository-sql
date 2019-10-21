package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Term;
import woorinaru.core.model.user.Staff;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TermAdapter extends Term {

    private woorinaru.repository.sql.entity.management.administration.Term termEntity;
    private EntityManager em;

    public TermAdapter(woorinaru.repository.sql.entity.management.administration.Term termEntity, EntityManager em) {
        this.termEntity = termEntity;
        this.em = em;
    }

    @Override
    public int getId() {
        return this.termEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.termEntity.setId(id);
    }

    @Override
    public int getTerm() {
        return this.termEntity.getTerm();
    }

    @Override
    public void setTerm(int term) {
        this.termEntity.setTerm(term);
    }

    @Override
    public void setStaffMembers(Collection<Staff> staffMembers) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public Collection<Staff> getStaffMembers() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public LocalDate getStartDate() {
        return this.termEntity.getStartDate();
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.termEntity.setStartDate(startDate);
    }

    @Override
    public LocalDate getEndDate() {
        return this.termEntity.getEndDate();
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        this.termEntity.setEndDate(endDate);
    }

    @Override
    public List<Event> getEvents() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setEvents(List<Event> events) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public boolean addEvent(Event event) {
        if (this.termEntity.getEvents() == null) {
            this.termEntity.setEvents(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());
        return this.termEntity.addEvent(eventEntity);
    }

    @Override
    public boolean removeEvent(int id) {
        if (this.termEntity.getEvents() == null) {
            return false;
        }
        return this.termEntity.getEvents().removeIf(event -> event.getId() == id);
    }
}
