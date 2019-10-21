package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.WooriClass;
import woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class EventAdapter extends Event {

    private woorinaru.repository.sql.entity.management.administration.Event eventEntity;
    private EntityManager em;

    public EventAdapter(woorinaru.repository.sql.entity.management.administration.Event eventEntity, EntityManager em) {
        this.eventEntity = eventEntity;
        this.em = em;
    }

    @Override
    public int getId() {
        return this.eventEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.eventEntity.setId(id);
    }

    @Override
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.eventEntity.setStartDateTime(startDateTime);
    }

    @Override
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.eventEntity.setEndDateTime(endDateTime);
    }

    @Override
    public void setAddress(String address) {
        this.eventEntity.setAddress(address);
    }

    @Override
    public void setDescription(String description) {
        this.eventEntity.setDescription(description);
    }

    @Override
    public void setWooriClasses(Collection<WooriClass> wooriClasses) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return this.eventEntity.getStartDateTime();
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return this.eventEntity.getEndDateTime();
    }

    @Override
    public String getAddress() {
        return this.eventEntity.getAddress();
    }

    @Override
    public String getDescription() {
        return this.eventEntity.getDescription();
    }

    @Override
    public Collection<WooriClass> getWooriClasses() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public Collection<Student> getStudentReservations() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setStudentReservations(Collection<Student> studentReservations) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public boolean addWooriClass(WooriClass wooriClass) {
        if (this.eventEntity.getWooriClasses() == null) {
            this.eventEntity.setWooriClasses(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.management.administration.WooriClass wooriClassEntity = em.find(woorinaru.repository.sql.entity.management.administration.WooriClass.class, wooriClass.getId());
        return this.eventEntity.getWooriClasses().add(wooriClassEntity);
    }

    @Override
    public boolean addStudentReservation(Student student) {
        if (this.eventEntity.getStudentReservations() == null) {
            this.eventEntity.setStudentReservations(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());
        return this.eventEntity.getStudentReservations().add(studentEntity);
    }

    @Override
    public boolean removeWooriClass(int id) {
        if (this.eventEntity.getWooriClasses() == null) {
            return false;
        }
        return this.eventEntity.getWooriClasses().removeIf(wooriClass -> wooriClass.getId() == id);
    }

    @Override
    public boolean removeStudentReservation(int id) {
        if (this.eventEntity.getStudentReservations() == null) {
            return false;
        }
        return this.eventEntity.getStudentReservations().removeIf(student -> student.getId() == id);
    }
}
