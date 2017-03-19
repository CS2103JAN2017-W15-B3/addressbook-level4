package seedu.address.model.task;

import java.util.Comparator;

public class DeadlineComparator implements Comparator<ReadOnlyTask> {
    public int compare(ReadOnlyTask p, ReadOnlyTask q) {
        if (p.getDeadline().get().deadline.before(q.getDeadline().get().deadline)) {
            return -1;
        } else if (p.getDeadline().get().deadline.after(q.getDeadline().get().deadline)) {
            return 1;
        } else {
            return 0;
        }        
    }
}
