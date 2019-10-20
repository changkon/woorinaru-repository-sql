package woorinaru.repository.sql.entity.management.administration;


import woorinaru.repository.sql.entity.user.Staff;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name="Term")
@Table(name="TERM")
public class Term {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="ID", updatable=false, nullable=false)
    private int id;

    @Column(name="TERM")
    private int term;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="TERM_STAFF", joinColumns=@JoinColumn(name="TERM_ID"), inverseJoinColumns = @JoinColumn(name="STAFF_ID"))
    private Collection<Staff> staffMembers;

    @Column(name="STARTDATE", columnDefinition="DATE")
    private LocalDate startDate;

    @Column(name="ENDDATE", columnDefinition="DATE")
    private LocalDate endDate;

    @OneToMany(fetch=FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="TERM_ID")
    private List<Event> events;

    public Term() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Collection<Staff> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(Collection<Staff> staffMembers) {
        this.staffMembers = staffMembers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}