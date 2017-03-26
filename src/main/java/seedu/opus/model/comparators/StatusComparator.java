package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

public class StatusComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask s1, ReadOnlyTask s2) {
        return s2.getStatus().value.compareTo(s1.getStatus().value);
    }
}
