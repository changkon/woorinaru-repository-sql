package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Term;
import woorinaru.core.model.user.Staff;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class TermAdapter extends Term {

    private woorinaru.repository.sql.entity.management.administration.Term termEntity;

    public TermAdapter(woorinaru.repository.sql.entity.management.administration.Term termEntity) {
        this.termEntity = termEntity;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public int getTerm() {
        return super.getTerm();
    }

    @Override
    public void setTerm(int term) {
        super.setTerm(term);
    }

    @Override
    public void setStaffMembers(Collection<Staff> staffMembers) {
        super.setStaffMembers(staffMembers);
    }

    @Override
    public Collection<Staff> getStaffMembers() {
        return super.getStaffMembers();
    }

    @Override
    public LocalDate getStartDate() {
        return super.getStartDate();
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        super.setStartDate(startDate);
    }

    @Override
    public LocalDate getEndDate() {
        return super.getEndDate();
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        super.setEndDate(endDate);
    }

    @Override
    public List<Event> getEvents() {
        return super.getEvents();
    }

    @Override
    public void setEvents(List<Event> events) {
        super.setEvents(events);
    }
}
