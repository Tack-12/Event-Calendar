package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EventListPanel extends JPanel {
    private final ArrayList<Event> events;
    private final JPanel displayPanel;
    private final JComboBox<String> sortDropDown;
    private final JCheckBox filterDisplay;
    private final JButton addEventButton;
    private final JComboBox<String> eventTypeFilter;

    public EventListPanel() {
        events = new ArrayList<>();
        this.setLayout(new BorderLayout());

        // Control Panel
        JPanel controlPanel = new JPanel();
        sortDropDown = new JComboBox<>(new String[]{"Sort by Name", "Sort by Date"});
        sortDropDown.addActionListener(e -> sortEvents());

        filterDisplay = new JCheckBox("Show Only Incomplete");
        filterDisplay.addActionListener(e -> updateDisplay());

        addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> openAddEventModal());

        // Drop-down filter for event type
        eventTypeFilter = new JComboBox<>(new String[]{"Show All", "Show Only Meetings", "Show Only Deadlines"});
        eventTypeFilter.addActionListener(e -> updateDisplay());

        controlPanel.add(sortDropDown);
        controlPanel.add(filterDisplay);
        controlPanel.add(eventTypeFilter);
        controlPanel.add(addEventButton);

        this.add(controlPanel, BorderLayout.NORTH);

        // Display Panel
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        this.add(new JScrollPane(displayPanel), BorderLayout.CENTER);

        updateDisplay();
    }

    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    private void sortEvents() {
        String selected = (String) sortDropDown.getSelectedItem();
        if (selected != null) {
            switch (selected) {
                case "Sort by Name" -> events.sort(Comparator.comparing(Event::getName));
                case "Sort by Date" -> events.sort(Comparator.comparing(Event::getDateTime));
            }
            updateDisplay();
        }
    }

    private void updateDisplay() {
        displayPanel.removeAll();
        String selectedFilter = (String) eventTypeFilter.getSelectedItem();

        for (Event event : events) {
            if (filterDisplay.isSelected() && event instanceof Completable completableEvent && completableEvent.isComplete()) {
                continue;
            }

            // Apply event type filtering
            if ("Show Only Meetings".equals(selectedFilter) && !(event instanceof Meeting)) {
                continue;
            }
            if ("Show Only Deadlines".equals(selectedFilter) && !(event instanceof Deadline)) {
                continue;
            }

            displayPanel.add(new EventPanel(event));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    private void openAddEventModal() {
        new AddEventModal(this).setVisible(true);
    }
}
