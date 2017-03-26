package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

public class StartTimeComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask d1, ReadOnlyTask d2) {
        boolean hasStartAndEnd = d1.getStartTime().isPresent() && d2.getStartTime().isPresent();
        boolean hasStartOrEnd = d1.getStartTime().isPresent() || d2.getStartTime().isPresent();
        if (hasStartAndEnd) {
            if (d1.getStartTime().get().dateTime.isBefore(d2.getStartTime().get().dateTime)) {
                return -1;
            } else if (d1.getStartTime().get().dateTime.isAfter(d2.getStartTime().get().dateTime)) {
                return 1;
            } else {
                return 0;
            }
        } else if (hasStartOrEnd) {
            return -1;
        } else {
            return 1;
        }
    }
}
