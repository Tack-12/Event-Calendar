package Event_Calendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EventListPanel extends JPanel {

    private final ArrayList<Event> events;
    private final JPanel displayPanel;
    private final JCheckBox filterDisplay;
    private final JButton addEventButton;
    private final JButton sortByNameAscButton;
    private final JButton sortByNameDescButton;
    private final JButton sortByDateButton;

    // Constants for UI layout
    private static final int DISPLAY_PANEL_LAYOUT_AXIS = BoxLayout.Y_AXIS;
    private static final String SORT_ASCENDING_LABEL = "Sort A → Z";
    private static final String SORT_DESCENDING_LABEL = "Sort Z → A";
    private static final String SORT_BY_DATE_LABEL = "Sort by Date";
    private static final String FILTER_LABEL = "Show Only Incomplete";
    private static final String ADD_EVENT_LABEL = "Add Event";

    public EventListPanel() {
        events = new ArrayList<>();
        this.setLayout(new BorderLayout());

        // Control panel with sorting, filtering, and adding events
        JPanel controlPanel = new JPanel();

        // Button to sort events alphabetically (A → Z)
        sortByNameAscButton = new JButton(SORT_ASCENDING_LABEL);
        sortByNameAscButton.addActionListener(e -> sortEvents(true));

        // Button to sort events in reverse order (Z → A)
        sortByNameDescButton = new JButton(SORT_DESCENDING_LABEL);
        sortByNameDescButton.addActionListener(e -> sortEvents(false));

        // Button to sort events by date
        sortByDateButton = new JButton(SORT_BY_DATE_LABEL);
        sortByDateButton.addActionListener(e -> sortByDate());

        // Checkbox to filter only incomplete events
        filterDisplay = new JCheckBox(FILTER_LABEL);
        filterDisplay.addActionListener(e -> updateDisplay());

        // Button to add a new event
        addEventButton = new JButton(ADD_EVENT_LABEL);
        addEventButton.addActionListener(e -> openAddEventModal());

        // Add controls to the panel
        controlPanel.add(sortByNameAscButton);
        controlPanel.add(sortByNameDescButton);
        controlPanel.add(sortByDateButton);
        controlPanel.add(filterDisplay);
        controlPanel.add(addEventButton);

        this.add(controlPanel, BorderLayout.NORTH);

        // Display panel to show event list
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, DISPLAY_PANEL_LAYOUT_AXIS));
        this.add(new JScrollPane(displayPanel), BorderLayout.CENTER);

        updateDisplay();
    }

    // Add event to the list and update display
    public void addEvent(Event event) {
        events.add(event);
        updateDisplay();
    }

    // Sort events alphabetically (ascending or descending)
    private void sortEvents(boolean ascending) {
        if (ascending) {
            events.sort(Comparator.comparing(Event::getName));
        } else {
            events.sort(Comparator.comparing(Event::getName).reversed());
        }
        updateDisplay();
    }

    // Sort events by date
    private void sortByDate() {
        events.sort(Comparator.comparing(Event::getDateTime));
        updateDisplay();
    }

    // Refresh the event list display
    private void updateDisplay() {
        displayPanel.removeAll();
        for (Event event : events) {
            // Show only incomplete events if filter is enabled
            if (filterDisplay.isSelected() && event instanceof Completable completableEvent && completableEvent.isComplete()) {
                continue;
            }
            displayPanel.add(new EventPanel(event));
        }
        displayPanel.revalidate();
        displayPanel.repaint();
    }

    // Open the modal to add a new event
    private void openAddEventModal() {
        new AddEventModal(this).setVisible(true);
    }
}
