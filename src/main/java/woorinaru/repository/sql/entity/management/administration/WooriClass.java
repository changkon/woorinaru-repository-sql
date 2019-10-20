package woorinaru.repository.sql.entity.management.administration;

import woorinaru.repository.sql.entity.resource.Resource;
import woorinaru.repository.sql.entity.user.Staff;
import woorinaru.repository.sql.entity.user.Student;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="WOORICLASS")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CLASS_TYPE")
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

    @Transient
    abstract public Grade getGrade();
}
