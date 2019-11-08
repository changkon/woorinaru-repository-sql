package com.woorinaru.repository.sql.adapter;


import com.woorinaru.core.model.management.administration.Event;
import com.woorinaru.core.model.management.administration.Term;
import com.woorinaru.core.model.user.Staff;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TermAdapter extends Term {

    private com.woorinaru.repository.sql.entity.management.administration.Term termEntity;
    private EntityManager em;

    public TermAdapter(com.woorinaru.repository.sql.entity.management.administration.Term termEntity, EntityManager em) {
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
            this.termEntity.setEvents(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());
        return this.termEntity.addEvent(eventEntity);
    }

    @Override
    public boolean removeEvent(int id) {
        if (this.termEntity.getEvents() == null) {
            return false;
        }
        return this.termEntity.getEvents().removeIf(event -> event.getId() == id);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.termEntity.getStaffMembers() == null) {
            this.termEntity.setStaffMembers(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(com.woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.termEntity.addStaff(staffEntity);
    }

    @Override
    public boolean removeStaff(int id) {
        if (this.termEntity.getStaffMembers() == null) {
            return false;
        }
        return this.termEntity.getStaffMembers().removeIf(staff -> staff.getId() == id);
    }
}
