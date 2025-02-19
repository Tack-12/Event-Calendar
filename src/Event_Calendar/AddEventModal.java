package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddEventModal extends JDialog {

    // Constants for UI dimensions
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    private static final int GRID_ROWS = 6;
    private static final int GRID_COLUMNS = 2;
    private static final int GRID_SPACING = 5;
    private static final int DEFAULT_MEETING_DURATION_HOURS = 1;
    private static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm";
    private static final String ERROR_MESSAGE = "Invalid date format! Please use MM-dd-yyyy HH:mm";

    public AddEventModal(EventListPanel eventListPanel) {
        setTitle("Add Event");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);  // Set window size using constants
        setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS, GRID_SPACING, GRID_SPACING)); // Grid layout for spacing

        // Input fields for event details
        JTextField eventNameField = new JTextField();
        JComboBox<String> eventTypeDropdown = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        JTextField dateTimeField = new JTextField();
        JTextField locationField = new JTextField();

        // Hint label for date-time format
        JLabel dateHintLabel = new JLabel("(Format: " + DATE_TIME_FORMAT + ")");
        dateHintLabel.setForeground(Color.BLUE);

        // Add form labels and fields
        add(new JLabel("Event Name:"));
        add(eventNameField);
        add(new JLabel("Event Type:"));
        add(eventTypeDropdown);
        add(new JLabel("Date-Time:"));
        add(dateTimeField);
        add(dateHintLabel); // Display hint for date format
        add(new JLabel()); // Empty label for spacing
        add(new JLabel("Location (if meeting):"));
        add(locationField);

        // Button to add event
        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> {
            String name = eventNameField.getText();
            String type = (String) eventTypeDropdown.getSelectedItem();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeField.getText(), formatter);

                // Create event based on selected type
                if ("Deadline".equals(type)) {
                    eventListPanel.addEvent(new Deadline(name, dateTime));
                } else {
                    LocalDateTime endTime = dateTime.plusHours(DEFAULT_MEETING_DURATION_HOURS); // Use constant for duration
                    eventListPanel.addEvent(new Meeting(name, dateTime, endTime, locationField.getText()));
                }
                dispose(); // Close modal after adding event
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(addButton);
        setModal(true);
    }
}
