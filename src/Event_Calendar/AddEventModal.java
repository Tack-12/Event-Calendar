package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AddEventModal extends JDialog {
    public AddEventModal(EventListPanel eventListPanel) {
        setTitle("Add Event");
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));

        JTextField eventNameField = new JTextField();
        JComboBox<String> eventTypeDropdown = new JComboBox<>(new String[]{"Deadline", "Meeting"});
        JTextField dateTimeField = new JTextField("2025-02-15T10:00");
        JTextField locationField = new JTextField();

        add(new JLabel("Event Name:"));
        add(eventNameField);
        add(new JLabel("Event Type:"));
        add(eventTypeDropdown);
        add(new JLabel("Date-Time:"));
        add(dateTimeField);
        add(new JLabel("Location (if meeting):"));
        add(locationField);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(e -> {
            String name = eventNameField.getText();
            String type = (String) eventTypeDropdown.getSelectedItem();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeField.getText());

            if ("Deadline".equals(type)) {
                eventListPanel.addEvent(new Deadline(name, dateTime));
            } else {
                LocalDateTime endTime = dateTime.plusHours(1); // Example duration
                eventListPanel.addEvent(new Meeting(name, dateTime, endTime, locationField.getText()));
            }
            dispose();
        });

        add(addButton);
        setModal(true);
    }
}