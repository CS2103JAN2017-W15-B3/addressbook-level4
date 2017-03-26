package seedu.opus.model.comparators;

import java.util.Comparator;

import seedu.opus.model.task.ReadOnlyTask;

public class TaskComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
        int c;

        StatusComparator sc = new StatusComparator();
        c = sc.compare(t1, t2);

        if (c != 0) {
            return c;
        } else {
            EndTimeComparator etc = new EndTimeComparator();
            c = etc.compare(t1, t2);
        }

        if (c != 0) {
            return c;
        } else {
            StartTimeComparator stc = new StartTimeComparator();
            c = stc.compare(t1, t2);
        }

        if (c != 0) {
            return c;
        } else {
            PriorityComparator pc = new PriorityComparator();
            c = pc.compare(t1, t2);
        }

        return c;
    }
}
