package Event_Calendar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventCalendar {
    private List<Event> events; // List to store events

    public EventCalendar() {
        this.events = new ArrayList<>(); // Initialize event list
    }

    public void addEvent(Event event) {
        events.add(event); // Add new event
        Collections.sort(events); // Keep events sorted
    }

    public void removeEvent(Event event) {
        events.remove(event); // Remove an event
    }

    public void listEvents() {
        for (Event event : events) {
            System.out.println(event.getDetails()); // Print event details
        }
    }

    public static void main(String[] args) {
        EventCalendar calendar = new EventCalendar(); // Create event calendar

        // Create a deadline event
        Deadline projectDeadline = new Deadline("Project Submission",
                LocalDateTime.of(2025, 3, 1, 17, 0));

        // Create a meeting event
        Meeting teamMeeting = new Meeting("Team Sync",
                LocalDateTime.of(2025, 2, 15, 10, 0),
                LocalDateTime.of(2025, 2, 15, 11, 0),
                "Conference Room A");

        calendar.addEvent(projectDeadline); // Add deadline
        calendar.addEvent(teamMeeting); // Add meeting

        projectDeadline.complete(); // Mark deadline as completed

        calendar.listEvents(); // Display all events
    }
}
