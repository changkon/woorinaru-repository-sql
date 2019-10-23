package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.OutingClass;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class OutingClassAdapter extends OutingClass {

    private woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity;
    private EntityManager em;

    public OutingClassAdapter(woorinaru.repository.sql.entity.management.administration.OutingClass outingClassEntity, EntityManager em) {
        this.outingClassEntity = outingClassEntity;
        this.em = em;
    }

    @Override
    public Grade getGrade() {
        return super.getGrade();
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
        this.outingClassEntity.setEvent(eventEntity);
    }

    @Override
    public int getId() {
        return this.outingClassEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.outingClassEntity.setId(id);
    }

    @Override
    public boolean addResource(Resource resource) {
        if (this.outingClassEntity.getResources() == null) {
            this.outingClassEntity.setResources(new ArrayList<>());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.outingClassEntity.addResource(resourceEntity);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.outingClassEntity.getStaff() == null) {
            this.outingClassEntity.setStaff(new ArrayList<>());
        }
        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.outingClassEntity.addStaff(staffEntity);
    }

    @Override
    public boolean addStudent(Student student) {
        if (this.outingClassEntity.getStudents() == null) {
            this.outingClassEntity.setStudents(new ArrayList<>());
        }
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());
        return this.outingClassEntity.addStudent(studentEntity);
    }

    @Override
    public boolean removeResource(int id) {
        if (this.outingClassEntity.getResources() == null) {
            return false;
        }
        return this.outingClassEntity.getResources().removeIf(resource -> resource.getId() == id);
    }

    @Override
    public boolean removeStaff(int id) {
        if (this.outingClassEntity.getStaff() == null) {
            return false;
        }
        return this.outingClassEntity.getStaff().removeIf(staff -> staff.getId() == id);
    }

    @Override
    public boolean removeStudent(int id) {
        if (this.outingClassEntity.getStudents() == null) {
            return false;
        }
        return this.outingClassEntity.getStudents().removeIf(student -> student.getId() == id);
    }
}
