package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class EventPanel extends JPanel {
    private final Event event;
    private final JButton completeButton;
    private final JCheckBox completedCheckBox;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    public EventPanel(Event event) {
        this.event = event;
        this.setLayout(new BorderLayout());

        // Display event details
        String eventDetails = "<html>" + event.getDetails().replace("\n", "<br>") + "</html>";
        JLabel detailsLabel = new JLabel(eventDetails);
        this.add(detailsLabel, BorderLayout.CENTER);

        // Panel for button and checkbox
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        completedCheckBox = new JCheckBox("Completed");
        completedCheckBox.setEnabled(false); // User can't manually toggle, only updates when event is completed

        completeButton = new JButton("Complete Event");

        if (event instanceof Completable completableEvent) {
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                completedCheckBox.setSelected(true); // Mark the checkbox when completed
                completeButton.setEnabled(false); // Disable the button after completion
                JOptionPane.showMessageDialog(this, "Event marked as complete!");
            });
            bottomPanel.add(completeButton);
            bottomPanel.add(completedCheckBox);
        } else {
            completeButton.setVisible(false);
            completedCheckBox.setVisible(false);
        }

        this.add(bottomPanel, BorderLayout.SOUTH);
        updateUrgency();
    }

    private void updateUrgency() {
        Urgency urgency = Urgency.getUrgency(event.getDateTime());

        switch (urgency) {
            case OVERDUE:
                setBackground(Color.RED);
                break;
            case IMMINENT:
                setBackground(Color.YELLOW);
                break;
            case DISTANT:
                setBackground(Color.GREEN);
                break;
        }
    }
}
