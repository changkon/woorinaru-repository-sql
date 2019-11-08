package com.woorinaru.repository.sql.adapter;


import com.woorinaru.core.model.management.administration.Event;
import com.woorinaru.core.model.management.administration.Grade;
import com.woorinaru.core.model.management.administration.Resource;
import com.woorinaru.core.model.management.administration.TutoringClass;
import com.woorinaru.core.model.user.Staff;
import com.woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;

public class TutoringClassAdapter extends TutoringClass {

    private com.woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity;
    private EntityManager em;

    public TutoringClassAdapter(com.woorinaru.repository.sql.entity.management.administration.TutoringClass tutoringClassEntity, EntityManager em) {
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
        com.woorinaru.repository.sql.entity.management.administration.Event eventEntity = em.find(com.woorinaru.repository.sql.entity.management.administration.Event.class, event.getId());
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
            this.tutoringClassEntity.setResources(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(com.woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.tutoringClassEntity.addResource(resourceEntity);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.tutoringClassEntity.getStaff() == null) {
            this.tutoringClassEntity.setStaff(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(com.woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.tutoringClassEntity.addStaff(staffEntity);
    }

    @Override
    public boolean addStudent(Student student) {
        if (this.tutoringClassEntity.getStudents() == null) {
            this.tutoringClassEntity.setStudents(new ArrayList<>());
        }
        com.woorinaru.repository.sql.entity.user.Student studentEntity = em.find(com.woorinaru.repository.sql.entity.user.Student.class, student.getId());
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
