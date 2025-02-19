package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private final Event event;
    private final JButton completeButton;
    private final JCheckBox completedCheckBox;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    // Constants for UI customization
    private static final Color OVERDUE_COLOR = Color.RED;
    private static final Color IMMINENT_COLOR = Color.YELLOW;
    private static final Color DISTANT_COLOR = Color.GREEN;
    private static final int FLOW_LAYOUT_ALIGNMENT = FlowLayout.LEFT;

    public EventPanel(Event event) {
        this.event = event;
        this.setLayout(new BorderLayout());

        // Display event details with formatted text
        String eventDetails = "<html>" + event.getDetails().replace("\n", "<br>") + "</html>";
        JLabel detailsLabel = new JLabel(eventDetails);
        this.add(detailsLabel, BorderLayout.CENTER);

        // Panel for button and checkbox
        JPanel bottomPanel = new JPanel(new FlowLayout(FLOW_LAYOUT_ALIGNMENT));

        // Checkbox to show event completion status
        completedCheckBox = new JCheckBox("Completed");
        completedCheckBox.setEnabled(false); // Prevent manual toggling

        // Button to mark event as completed
        completeButton = new JButton("Complete Event");

        if (event instanceof Completable completableEvent) {
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                completedCheckBox.setSelected(true); // Auto-check when completed
                completeButton.setEnabled(false); // Disable button after completion
                JOptionPane.showMessageDialog(this, "Event marked as complete!");
            });
            bottomPanel.add(completeButton);
            bottomPanel.add(completedCheckBox);
        } else {
            completeButton.setVisible(false);
            completedCheckBox.setVisible(false);
        }

        this.add(bottomPanel, BorderLayout.SOUTH);
        updateUrgency(); // Set urgency-based background color
    }

    private void updateUrgency() {
        Urgency urgency = Urgency.getUrgency(event.getDateTime());

        switch (urgency) {
            case OVERDUE:
                setBackground(OVERDUE_COLOR);
                break;
            case IMMINENT:
                setBackground(IMMINENT_COLOR);
                break;
            case DISTANT:
                setBackground(DISTANT_COLOR);
                break;
        }
    }
}
