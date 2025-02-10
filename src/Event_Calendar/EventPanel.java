package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventPanel extends JPanel {
    private final Event event;
    private final JButton completeButton;

    public EventPanel(Event event) {
        this.event = event;
        this.setLayout(new BorderLayout());

        String eventDetails = "<html>" + event.getDetails().replace("\n", "<br>") + "</html>";
        JLabel detailsLabel = new JLabel(eventDetails);
        this.add(detailsLabel, BorderLayout.CENTER);

        completeButton = new JButton("Complete Event");
        if (event instanceof Completable completableEvent) {
            completeButton.addActionListener(e -> {
                completableEvent.complete();
                JOptionPane.showMessageDialog(this, "Event marked as complete!");
                updatePanel();
            });
            this.add(completeButton, BorderLayout.SOUTH);
        } else {
            completeButton.setVisible(false);
        }
        updateUrgency();
    }

    private void updatePanel() {
        completeButton.setEnabled(false);
    }

    private void updateUrgency() {
        LocalDateTime now = LocalDateTime.now();
        if (event.getDateTime().isBefore(now)) {
            setBackground(Color.RED);
        } else if (event.getDateTime().isBefore(now.plusDays(1))) {
            setBackground(Color.YELLOW);
        } else {
            setBackground(Color.GREEN);
        }
    }
}