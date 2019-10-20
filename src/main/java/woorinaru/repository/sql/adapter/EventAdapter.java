package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.WooriClass;
import woorinaru.core.model.user.Student;

import java.time.LocalDateTime;
import java.util.Collection;

public class EventAdapter extends Event {

    private woorinaru.repository.sql.entity.management.administration.Event eventEntity;

    public EventAdapter(woorinaru.repository.sql.entity.management.administration.Event eventEntity) {
        this.eventEntity = eventEntity;
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
    public void setStartDateTime(LocalDateTime startDateTime) {
        super.setStartDateTime(startDateTime);
    }

    @Override
    public void setEndDateTime(LocalDateTime endDateTime) {
        super.setEndDateTime(endDateTime);
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setWooriClasses(Collection<WooriClass> wooriClasses) {
        super.setWooriClasses(wooriClasses);
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return super.getStartDateTime();
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return super.getEndDateTime();
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Collection<WooriClass> getWooriClasses() {
        return super.getWooriClasses();
    }

    @Override
    public Collection<Student> getStudentReservations() {
        return super.getStudentReservations();
    }

    @Override
    public void setStudentReservations(Collection<Student> studentReservations) {
        super.setStudentReservations(studentReservations);
    }
}
