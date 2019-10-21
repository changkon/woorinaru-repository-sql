package woorinaru.repository.sql.entity.management.administration;

import woorinaru.repository.sql.entity.user.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity(name="Event")
@Table(name="EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    private int id;

    @Column(name="STARTDATETIME", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDateTime;

    @Column(name="ENDDATETIME", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDateTime;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="event")
    private Collection<WooriClass> wooriClasses;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="EVENT_STUDENT_RESERVATION", joinColumns=@JoinColumn(name="EVENT_ID"), inverseJoinColumns = @JoinColumn(name="STUDENT_ID"))
    private Collection<Student> studentReservations;

    public Event() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<WooriClass> getWooriClasses() {
        return wooriClasses;
    }

    public void setWooriClasses(Collection<WooriClass> wooriClasses) {
        this.wooriClasses = wooriClasses;
    }

    public Collection<Student> getStudentReservations() {
        return studentReservations;
    }

    public void setStudentReservations(Collection<Student> studentReservations) {
        this.studentReservations = studentReservations;
    }

    public boolean addWooriClass(WooriClass wooriClass) {
        if (wooriClasses == null) {
            this.wooriClasses = Collections.emptyList();
        }
        return this.wooriClasses.add(wooriClass);
    }

    public boolean addStudentReservation(Student student) {
        if (studentReservations == null) {
            this.studentReservations = Collections.emptyList();
        }

        return this.studentReservations.add(student);
    }

    public boolean removeWooriClass(int id) {
        if (wooriClasses == null) {
            return false;
        }
        return this.wooriClasses.removeIf(wClass -> wClass.getId() == id);
    }

    public boolean removeStudentReservation(int id) {
        if (studentReservations == null) {
            return false;
        }
        return this.studentReservations.removeIf(student -> student.getId() == id);
    }
}
