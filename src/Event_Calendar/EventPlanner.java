package Event_Calendar;

import javax.swing.*;
import java.time.LocalDateTime;

public class EventPlanner {
    public static void main(String[] args) {
        final int panel_x = 900;
        final int panel_y = 900;

        // Create main application window
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize event panel and add default events
        EventListPanel eventListPanel = new EventListPanel();
        addDefaultEvents(eventListPanel);

        frame.add(eventListPanel);
        frame.setSize(panel_x, panel_y);
        frame.setVisible(true); // Display the frame
    }

    // Adds some default example events
    static void addDefaultEvents(EventListPanel eventListPanel) {
        eventListPanel.addEvent(new Deadline("EXAMPLE Project Deadline in 5 Days",
                LocalDateTime.now().plusDays(5)));

        eventListPanel.addEvent(new Meeting("EXAMPLE Meeting in an hour",
                LocalDateTime.now().plusHours(1),
                LocalDateTime.now().plusHours(2),
                "Room A"));

        eventListPanel.addEvent(new Meeting("EXAMPLE Class due an hour ago",
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1),
                "CS123"));
    }
}
