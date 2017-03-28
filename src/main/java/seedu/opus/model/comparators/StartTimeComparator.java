package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@author A0148081H
public class StartTimeComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask d1, ReadOnlyTask d2) {
        boolean bothHaveStart = d1.getStartTime().isPresent() && d2.getStartTime().isPresent();
        boolean oneHasStart = d1.getStartTime().isPresent() || d2.getStartTime().isPresent();
        if (bothHaveStart) {
            boolean d1IsBefore = d1.getStartTime().get().dateTime.isBefore(d2.getStartTime().get().dateTime);
            boolean d1IsAfter = d1.getStartTime().get().dateTime.isAfter(d2.getStartTime().get().dateTime);
            if (d1IsBefore) {
                return -1;
            } else if (d1IsAfter) {
                return 1;
            } else {
                return 0;
            }
        } else if (oneHasStart) {
            return -1;
        } else {
            return 1;
        }
    }
}
