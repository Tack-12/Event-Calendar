package Event_Calendar;

import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventListPanel eventListPanel = new EventListPanel();
        addDefaultEvents(eventListPanel);

        frame.add(eventListPanel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    // Adds default events to the planner
    static void addDefaultEvents(EventListPanel eventListPanel) {
        eventListPanel.addEvent(new Deadline("Project Deadline", LocalDateTime.now().plusDays(5)));
        eventListPanel.addEvent(new Meeting("Team Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Room A"));
    }
}