package seedu.opus.model;

import java.util.Set;

import seedu.opus.commons.core.UnmodifiableObservableList;
import seedu.opus.commons.exceptions.InvalidUndoException;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;
import seedu.opus.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Sorts the filtered task list to show all tasks */
    void sortList(String keyword);

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /**
     * Reset current data to the previous state to undo changes
     * @throws InvalidUndoException
     */
    void resetToPreviousState() throws InvalidUndoException;

    /**
     * Reset current data to preceding state to rollback changes due to previous undo operation
     * @throws InvalidUndoException
     */
    void resetToPrecedingState() throws InvalidUndoException;

}
