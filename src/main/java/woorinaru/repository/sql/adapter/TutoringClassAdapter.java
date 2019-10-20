package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.management.administration.TutoringClass;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;

import java.util.Collection;

public class TutoringClassAdapter extends TutoringClass {

    private woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity;

    public TutoringClassAdapter(woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity) {
        this.tutoringClassEntity = tutoringClassEntity;
    }

    @Override
    public Grade getGrade() {
        return super.getGrade();
    }

    @Override
    public Collection<Resource> getResources() {
        return super.getResources();
    }

    @Override
    public Collection<Staff> getStaff() {
        return super.getStaff();
    }

    @Override
    public Collection<Student> getStudents() {
        return super.getStudents();
    }

    @Override
    public void setResources(Collection<Resource> resources) {
        super.setResources(resources);
    }

    @Override
    public void setStaff(Collection<Staff> staff) {
        super.setStaff(staff);
    }

    @Override
    public void setStudents(Collection<Student> students) {
        super.setStudents(students);
    }

    @Override
    public Event getEvent() {
        return super.getEvent();
    }

    @Override
    public void setEvent(Event event) {
        super.setEvent(event);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }
}
