package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

//@@author A0148081H
public class PriorityComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask p1, ReadOnlyTask p2) {
        int c;

        StatusComparator sc = new StatusComparator();
        c = sc.compare(p1, p2);

        if (c != 0) {
            return c;
        } else {
            boolean bothHavePriority = p1.getPriority().isPresent() && p2.getPriority().isPresent();
            boolean oneHasPriority = p1.getPriority().isPresent() || p2.getPriority().isPresent();
            if (bothHavePriority) {
                return p1.getPriority().get().getValue().compareTo(p2.getPriority().get().getValue());
            } else if (oneHasPriority) {
                if (p1.getPriority().isPresent()) {
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
