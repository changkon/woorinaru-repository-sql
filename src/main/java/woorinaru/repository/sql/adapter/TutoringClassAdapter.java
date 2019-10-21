package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.management.administration.TutoringClass;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

public class TutoringClassAdapter extends TutoringClass {

    private woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity;
    private EntityManager em;

    public TutoringClassAdapter(woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity, EntityManager em) {
        this.tutoringClassEntity = tutoringClassEntity;
        this.em = em;
    }

    @Override
    public Grade getGrade() {
        return this.getGrade();
    }

    @Override
    public Collection<Resource> getResources() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public Collection<Staff> getStaff() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public Collection<Student> getStudents() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setResources(Collection<Resource> resources) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setStaff(Collection<Staff> staff) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setStudents(Collection<Student> students) {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public Event getEvent() {
        throw new AdapterUnsupportedOperationException();
    }

    @Override
    public void setEvent(Event event) {
        woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());
        this.tutoringClassEntity.setEvent(eventEntity);
    }

    @Override
    public int getId() {
        return this.tutoringClassEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.tutoringClassEntity.setId(id);
    }

    @Override
    public boolean addResource(Resource resource) {
        if (this.tutoringClassEntity.getResources() == null) {
            this.tutoringClassEntity.setResources(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.tutoringClassEntity.addResource(resourceEntity);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.tutoringClassEntity.getStaff() == null) {
            this.tutoringClassEntity.setStaff(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.tutoringClassEntity.addStaff(staffEntity);
    }

    @Override
    public boolean addStudent(Student student) {
        if (this.tutoringClassEntity.getStudents() == null) {
            this.tutoringClassEntity.setStudents(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());
        return this.tutoringClassEntity.addStudent(studentEntity);
    }

    @Override
    public boolean removeResource(int id) {
        if (this.tutoringClassEntity.getResources() == null) {
            return false;
        }
        return this.tutoringClassEntity.getResources().removeIf(resource -> resource.getId() == id);
    }

    @Override
    public boolean removeStaff(int id) {
        if (this.tutoringClassEntity.getStaff() == null) {
            return false;
        }
        return this.tutoringClassEntity.getStaff().removeIf(staff -> staff.getId() == id);
    }

    @Override
    public boolean removeStudent(int id) {
        if (this.tutoringClassEntity.getStudents() == null) {
            return false;
        }
        return this.tutoringClassEntity.getStudents().removeIf(student -> student.getId() == id);
    }
}
