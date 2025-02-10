package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddEventModal extends JDialog {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    public AddEventModal(EventListPanel eventListPanel) {
        setTitle("Add Event");
        setSize(350, 220);
        setLayout(new GridLayout(6, 2));

        JTextField eventNameField = new JTextField();
        JComboBox<String> eventTypeDropdown = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        JTextField dateTimeField = new JTextField();
        JTextField locationField = new JTextField();

        // Highlighting date format instructions
        JLabel dateTimeLabel = new JLabel("Date-Time (MM-DD-YYYY HH:MM):");
        dateTimeLabel.setForeground(Color.BLUE); // Make the label more noticeable
        dateTimeField.setToolTipText("Enter the date-time in format: MM-DD-YYYY HH:MM (e.g., 02-20-2025 18:30)");

        add(new JLabel("Event Name:"));
        add(eventNameField);
        add(new JLabel("Event Type:"));
        add(eventTypeDropdown);
        add(dateTimeLabel);
        add(dateTimeField);
        add(new JLabel("Location (if meeting):"));
        add(locationField);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> {
            String name = eventNameField.getText();
            String type = (String) eventTypeDropdown.getSelectedItem();
            String dateTimeText = dateTimeField.getText();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, FORMATTER);

                if ("Deadline".equals(type)) {
                    eventListPanel.addEvent(new Deadline(name, dateTime));
                } else {
                    LocalDateTime endTime = dateTime.plusHours(1); // Example duration
                    eventListPanel.addEvent(new Meeting(name, dateTime, endTime, locationField.getText()));
                }
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Please use MM-DD-YYYY HH:MM", "Error", JOptionPane.ERROR_MESSAGE);
                dateTimeField.setBackground(Color.PINK); // Highlight incorrect input
            }
        });

        add(addButton);
        setModal(true);
    }
}
