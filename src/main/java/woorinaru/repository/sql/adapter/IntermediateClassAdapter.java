package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.IntermediateClass;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

public class IntermediateClassAdapter extends IntermediateClass {

    private woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity;
    private EntityManager em;

    public IntermediateClassAdapter(woorinaru.repository.sql.entity.management.administration.IntermediateClass intermediateClassEntity, EntityManager em) {
        this.intermediateClassEntity = intermediateClassEntity;
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
        this.intermediateClassEntity.setEvent(eventEntity);
    }

    @Override
    public int getId() {
        return this.intermediateClassEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.intermediateClassEntity.setId(id);
    }

    @Override
    public boolean addResource(Resource resource) {
        if (this.intermediateClassEntity.getResources() == null) {
            this.intermediateClassEntity.setResources(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.intermediateClassEntity.addResource(resourceEntity);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.intermediateClassEntity.getStaff() == null) {
            this.intermediateClassEntity.setStaff(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.intermediateClassEntity.addStaff(staffEntity);
    }

    @Override
    public boolean addStudent(Student student) {
        if (this.intermediateClassEntity.getStudents() == null) {
            this.intermediateClassEntity.setStudents(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());
        return this.intermediateClassEntity.addStudent(studentEntity);
    }

    @Override
    public boolean removeResource(int id) {
        if (this.intermediateClassEntity.getResources() == null) {
            return false;
        }
        return this.intermediateClassEntity.getResources().removeIf(resource -> resource.getId() == id);
    }

    @Override
    public boolean removeStaff(int id) {
        if (this.intermediateClassEntity.getStaff() == null) {
            return false;
        }
        return this.intermediateClassEntity.getStaff().removeIf(staff -> staff.getId() == id);
    }

    @Override
    public boolean removeStudent(int id) {
        if (this.intermediateClassEntity.getStudents() == null) {
            return false;
        }
        return this.intermediateClassEntity.getStudents().removeIf(student -> student.getId() == id);
    }
}
