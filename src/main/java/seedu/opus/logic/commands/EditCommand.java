package seedu.opus.logic.commands;

import java.util.List;
import java.util.Optional;

import seedu.opus.commons.core.Messages;
import seedu.opus.commons.util.CollectionUtil;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NAME] [p/PRIORITY] [n/NOTE] [s/STATUS] [b/STARTTIME] [e/ENDTIME] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 p/mid s/complete";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";
    public static final String MESSAGE_INVALID_EVENT = "Please make sure to define an end time "
            + "if the start time is already set. The end time "
            + "should also be after the current time and the start time.";

    private final int filteredTaskListIndex;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param filteredTaskListIndex the index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        assert filteredTaskListIndex > 0;
        assert editTaskDescriptor != null;

        // converts filteredTaskListIndex from one-based to zero-based.
        this.filteredTaskListIndex = filteredTaskListIndex - 1;

        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            if (!Task.isValidEvent(editedTask)) {
                throw new CommandException(MESSAGE_INVALID_EVENT);
            }
            model.updateTask(filteredTaskListIndex, editedTask);
        } catch (UniqueTaskList.DuplicateTaskException dte) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElseGet(taskToEdit::getName);
        Priority updatedPriority = editTaskDescriptor
                .getPriority()
                .map(Optional::of)
                .orElseGet(taskToEdit::getPriority)
                .orElse(null);
        Status updatedStatus = editTaskDescriptor.getStatus().orElseGet(taskToEdit::getStatus);
        Note updatedNote = editTaskDescriptor.getNote()
                .map(Optional::of)
                .orElseGet(taskToEdit::getNote)
                .orElse(null);
        DateTime updatedStartTime = editTaskDescriptor
                .getStartTime()
                .map(Optional::of)
                .orElseGet(taskToEdit::getStartTime)
                .orElse(null);
        DateTime updatedEndTime = editTaskDescriptor
                .getEndTime()
                .map(Optional::of)
                .orElseGet(taskToEdit::getEndTime)
                .orElse(null);
        UniqueTagList updatedTags = editTaskDescriptor.getTags().orElseGet(taskToEdit::getTags);

        return new Task(updatedName, updatedPriority, updatedStatus, updatedNote,
                updatedStartTime, updatedEndTime, updatedTags);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private Optional<Name> name = Optional.empty();
        private Optional<Priority> priority = Optional.empty();
        private Optional<Status> status = Optional.empty();
        private Optional<Note> note = Optional.empty();
        private Optional<DateTime> startTime = Optional.empty();
        private Optional<DateTime> endTime = Optional.empty();
        private Optional<UniqueTagList> tags = Optional.empty();

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.getName();
            this.priority = toCopy.getPriority();
            this.status = toCopy.getStatus();
            this.note = toCopy.getNote();
            this.startTime = toCopy.getStartTime();
            this.endTime = toCopy.getEndTime();
            this.tags = toCopy.getTags();
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyPresent(this.name, this.priority, this.status,
                    this.note, this.startTime, this.endTime, this.tags);
        }

        public void setName(Optional<Name> name) {
            assert name != null;
            this.name = name;
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setPriority(Optional<Priority> priority) {
            assert priority != null;
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return priority;
        }

        public void setStatus(Optional<Status> status) {
            assert status != null;
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return status;
        }

        public void setNote(Optional<Note> note) {
            assert note != null;
            this.note = note;
        }

        public Optional<Note> getNote() {
            return note;
        }

        public void setStartTime(Optional<DateTime> startTime) {
            assert startTime != null;
            this.startTime = startTime;
        }

        public Optional<DateTime> getStartTime() {
            return startTime;
        }

        public void setEndTime(Optional<DateTime> endTime) {
            assert endTime != null;
            this.endTime = endTime;
        }

        public Optional<DateTime> getEndTime() {
            return endTime;
        }

        public void setTags(Optional<UniqueTagList> tags) {
            assert tags != null;
            this.tags = tags;
        }

        public Optional<UniqueTagList> getTags() {
            return tags;
        }
    }
}
