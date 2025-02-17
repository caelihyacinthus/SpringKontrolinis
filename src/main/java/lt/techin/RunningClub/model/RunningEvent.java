package lt.techin.RunningClub.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "running_events")
public class RunningEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private LocalDate calendarDate;
    private String location;
    private int maxParticipants;
    @OneToMany
    @JoinColumn(name = "running_event_id")
    private List<Registration> registrations;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(LocalDate calendarDate) {
        this.calendarDate = calendarDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }
}
