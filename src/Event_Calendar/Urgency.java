package Event_Calendar;

import java.time.Duration;
import java.time.LocalDateTime;

enum Urgency {
    DISTANT, IMMINENT, OVERDUE;
    static Duration thresholdOfImminence = Duration.ofDays(1);

    public static void setThresholdOfImminence(Duration d) {
        thresholdOfImminence = d;
    }

    public static Urgency getUrgency(LocalDateTime time) {
        Duration duration = Duration.between(LocalDateTime.now(), time);
        if (duration.isNegative()) return OVERDUE;
        if (duration.compareTo(thresholdOfImminence) <= 0) return IMMINENT;
        return DISTANT;
    }
}