package seedu.opus.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.opus.commons.core.ComponentManager;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.commons.core.UnmodifiableObservableList;
import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.commons.exceptions.InvalidUndoException;
import seedu.opus.commons.util.CollectionUtil;
import seedu.opus.commons.util.StringUtil;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;
import seedu.opus.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Represents the in-memory model of the task manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private FilteredList<ReadOnlyTask> filteredTasks;

    private History history;

    /**
     * Initializes a ModelManager with the given taskManager and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        assert !CollectionUtil.isAnyNull(taskManager, userPrefs);

        logger.fine("Initializing with task manager: " + taskManager + " and user prefs " + userPrefs);

        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        history = new History();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        history.backupCurrentState(this.taskManager);
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        history.backupCurrentState(this.taskManager);
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        history.backupCurrentState(this.taskManager);
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedTask != null;

        history.backupCurrentState(this.taskManager);
        int taskManagerIndex = filteredTasks.getSourceIndex(filteredTaskListIndex);
        taskManager.updateTask(taskManagerIndex, editedTask);
        indicateTaskManagerChanged();
    }

    //@@author A0148087W
    @Override
    public void resetToPreviousState() throws InvalidUndoException {
        this.taskManager.resetData(this.history.getPreviousState(this.taskManager));
        indicateTaskManagerChanged();
    }

    @Override
    public void resetToPrecedingState() throws InvalidUndoException {
        this.taskManager.resetData(this.history.getPrecedingState(this.taskManager));
        indicateTaskManagerChanged();
    }
    //@@author

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    //@@author A0148081H
    @Override
    public void sortList(String keyword) {
        filteredTasks = new FilteredList<>(this.taskManager.getSortedList(keyword));
    }
    //@@author

    public int getTaskIndex(ReadOnlyTask task) {
        return filteredTasks.indexOf(task);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(
                new NameQualifier(keywords), new NoteQualifier(keywords), new TagQualifier(keywords)
                ));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
    }

    //@@author A0126345J
    private class PredicateExpression implements Expression {

        private final List<Qualifier> qualifiers;


        PredicateExpression(Qualifier... qualifiers) {
            this.qualifiers = Arrays.asList(qualifiers);
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            boolean result = false;
            for (Qualifier qualifier: qualifiers) {
                result = result | qualifier.run(task);
            }
            return result;
        }

    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

    }

    private class NoteQualifier implements Qualifier {
        private Set<String> noteKeyWords;

        NoteQualifier(Set<String> noteKeyWords) {
            this.noteKeyWords = noteKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String note = task.getNote().isPresent() ? task.getNote().get().value : "";
            return noteKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(note, keyword))
                    .findAny()
                    .isPresent();
        }

    }

    private class TagQualifier implements Qualifier {
        private Set<String> tagKeyWords;

        TagQualifier(Set<String> tagKeyWords) {
            this.tagKeyWords = tagKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return tagKeyWords.stream()
                    .filter(keyword -> {
                        try {
                            return task.getTags().contains(new Tag(keyword));
                        } catch (IllegalValueException e) {
                            e.printStackTrace();
                        }
                        return false;
                    })
                    .findAny()
                    .isPresent();
        }

    }

}
