package Event_Calendar;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventCalendar {
    private List<Event> events;

    public EventCalendar() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        Collections.sort(events);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void listEvents() {
        for (Event event : events) {
            System.out.println(event.getDetails());
        }
    }

    public static void main(String[] args) {
        EventCalendar calendar = new EventCalendar();

        Deadline projectDeadline = new Deadline("Project Submission", LocalDateTime.of(2025, 3, 1, 17, 0));
        Meeting teamMeeting = new Meeting("Team Sync", LocalDateTime.of(2025, 2, 15, 10, 0), LocalDateTime.of(2025, 2, 15, 11, 0), "Conference Room A");

        calendar.addEvent(projectDeadline);
        calendar.addEvent(teamMeeting);

        projectDeadline.complete();

        calendar.listEvents();
    }
}