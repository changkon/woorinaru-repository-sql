package com.woorinaru.repository.sql.entity.management.administration;

import com.woorinaru.repository.sql.entity.user.Staff;
import com.woorinaru.repository.sql.entity.user.Student;
import com.woorinaru.repository.sql.entity.resource.Resource;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="WOORICLASS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CLASS_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
public abstract class WooriClass {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    protected int id;

    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(name="WOORICLASS_RESOURCE", joinColumns = @JoinColumn(name="WOORICLASS_ID"), inverseJoinColumns=@JoinColumn(name="RESOURCE_ID"))
    protected Collection<Resource> resources;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="WOORICLASS_STAFF", joinColumns=@JoinColumn(name="WOORICLASS_ID"), inverseJoinColumns = @JoinColumn(name="STAFF_ID"))
    protected Collection<Staff> staff;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="WOORICLASS_STUDENT", joinColumns=@JoinColumn(name="WOORICLASS_ID"), inverseJoinColumns = @JoinColumn(name="STUDENT_ID"))
    protected Collection<Student> students;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EVENT_ID")
    protected Event event;

    @Column(name="CREATEDATETIME", columnDefinition="TIMESTAMP")
    protected LocalDateTime createDateTime;

    @Column(name="UPDATEDATETIME", columnDefinition="TIMESTAMP")
    protected LocalDateTime updateDateTime;

    public WooriClass() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Collection<Resource> getResources() {
        return resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }

    public Collection<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Collection<Staff> staff) {
        this.staff = staff;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean addResource(Resource resource) {
        if (resources == null) {
            resources = new ArrayList<>();
        }
        return resources.add(resource);
    }

    public boolean addStaff(Staff staff) {
        if (this.staff == null) {
            this.staff = new ArrayList<>();
        }
        return this.staff.add(staff);
    }

    public boolean addStudent(Student student) {
        if (students == null) {
            this.students = new ArrayList<>();
        }
        return this.students.add(student);
    }

    public boolean removeResource(int id) {
        if (resources == null) {
            return false;
        }
        return resources.removeIf(resource -> resource.getId() == id);
    }

    public boolean removeStaff(int id) {
        if (staff == null) {
            return false;
        }
        return this.staff.removeIf(staff -> staff.getId() == id);
    }

    public boolean removeStudent(int id) {
        if (students == null) {
            return false;
        }
        return this.students.removeIf(student -> student.getId() == id);
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @PrePersist
    protected void onCreate() {
        this.createDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDateTime = LocalDateTime.now(ZoneOffset.UTC);
    }

    @Transient
    abstract public Grade getGrade();
}
