package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

public class EndTimeComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask d1, ReadOnlyTask d2) {
        boolean hasStartAndEnd = d1.getEndTime().isPresent() && d2.getEndTime().isPresent();
        boolean hasStartOrEnd = d1.getEndTime().isPresent() || d2.getEndTime().isPresent();
        if (hasStartAndEnd) {
            if (d1.getEndTime().get().dateTime.isBefore(d2.getEndTime().get().dateTime)) {
                return -1;
            } else if (d1.getEndTime().get().dateTime.isAfter(d2.getEndTime().get().dateTime)) {
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
