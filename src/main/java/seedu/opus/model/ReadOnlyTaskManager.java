package seedu.opus.model;


import javafx.collections.ObservableList;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of a task manager
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the sorted tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<ReadOnlyTask> getSortedList(String keyword);

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
