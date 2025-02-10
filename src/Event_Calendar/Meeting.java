package Event_Calendar;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meeting extends Event implements Completable {
    private LocalDateTime endDateTime;
    private String location;
    private boolean complete;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");


    public Meeting(String name, LocalDateTime start, LocalDateTime end, String location) {
        super(name, start);
        this.endDateTime = end;
        this.location = location;
        this.complete = false;
    }

    @Override
    public void complete() {
        this.complete = true;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Duration getDuration() {
        return Duration.between(getDateTime(), endDateTime);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getDetails() {
        return String.format("Meeting: %s from %s to %s at %s (Complete: %b)",
                getName(), getDateTime().format(FORMATTER), getEndDateTime().format(FORMATTER), location, complete);
    }
}
