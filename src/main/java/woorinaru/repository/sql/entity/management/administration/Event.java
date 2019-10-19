package woorinaru.repository.sql.entity.management.administration;

import woorinaru.repository.sql.entity.user.Student;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

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
}
