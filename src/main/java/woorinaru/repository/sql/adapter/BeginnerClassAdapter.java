package woorinaru.repository.sql.adapter;

import woorinaru.core.model.management.administration.BeginnerClass;
import woorinaru.core.model.management.administration.Event;
import woorinaru.core.model.management.administration.Grade;
import woorinaru.core.model.management.administration.Resource;
import woorinaru.core.model.user.Staff;
import woorinaru.core.model.user.Student;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

public class BeginnerClassAdapter extends BeginnerClass {

    private woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity;
    private EntityManager em;

    public BeginnerClassAdapter(woorinaru.repository.sql.entity.management.administration.BeginnerClass beginnerClassEntity, EntityManager em) {
        this.beginnerClassEntity = beginnerClassEntity;
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
        this.beginnerClassEntity.setEvent(eventEntity);
    }

    @Override
    public int getId() {
        return this.beginnerClassEntity.getId();
    }

    @Override
    public void setId(int id) {
        this.beginnerClassEntity.setId(id);
    }

    @Override
    public boolean addResource(Resource resource) {
        if (this.beginnerClassEntity.getResources() == null) {
            this.beginnerClassEntity.setResources(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.resource.Resource resourceEntity = em.find(woorinaru.repository.sql.entity.resource.Resource.class, resource.getId());
        return this.beginnerClassEntity.addResource(resourceEntity);
    }

    @Override
    public boolean addStaff(Staff staff) {
        if (this.beginnerClassEntity.getStaff() == null) {
            this.beginnerClassEntity.setStaff(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Staff staffEntity = em.find(woorinaru.repository.sql.entity.user.Staff.class, staff.getId());
        return this.beginnerClassEntity.addStaff(staffEntity);
    }

    @Override
    public boolean addStudent(Student student) {
        if (this.beginnerClassEntity.getStudents() == null) {
            this.beginnerClassEntity.setStudents(Collections.emptyList());
        }
        woorinaru.repository.sql.entity.user.Student studentEntity = em.find(woorinaru.repository.sql.entity.user.Student.class, student.getId());
        return this.beginnerClassEntity.addStudent(studentEntity);
    }

    @Override
    public boolean removeResource(int id) {
        if (this.beginnerClassEntity.getResources() == null) {
            return false;
        }
        return this.beginnerClassEntity.getResources().removeIf(resource -> resource.getId() == id);
    }

    @Override
    public boolean removeStaff(int id) {
        if (this.beginnerClassEntity.getStaff() == null) {
            return false;
        }
        return this.beginnerClassEntity.getStaff().removeIf(staff -> staff.getId() == id);
    }

    @Override
    public boolean removeStudent(int id) {
        if (this.beginnerClassEntity.getStudents() == null) {
            return false;
        }
        return this.beginnerClassEntity.getStudents().removeIf(student -> student.getId() == id);
    }
}
