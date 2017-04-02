package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0148081H
public class EndTimeComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask d1, ReadOnlyTask d2) {
        int c;

        StatusComparator sc = new StatusComparator();
        c = sc.compare(d1, d2);

        if (c != 0) {
            return c;
        } else {
            boolean bothHaveEnd = d1.getEndTime().isPresent() && d2.getEndTime().isPresent();
            boolean oneHasEnd = d1.getEndTime().isPresent() || d2.getEndTime().isPresent();
            if (bothHaveEnd) {
                boolean d1IsBefore = d1.getEndTime().get().dateTime.isBefore(d2.getEndTime().get().dateTime);
                boolean d1IsAfter = d1.getEndTime().get().dateTime.isAfter(d2.getEndTime().get().dateTime);
                if (d1IsBefore) {
                    return -1;
                } else if (d1IsAfter) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (oneHasEnd) {
                if (d1.getEndTime().isPresent()) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
    }
}
