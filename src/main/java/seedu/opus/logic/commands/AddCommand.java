package seedu.opus.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME p/PRIORITY s/STATUS n/NOTES b/STARTTIME e/ENDTIME [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Finish assignment p/hi s/incomplete n/dead b/25/12/2017 23:59 e/30/12/2017 23:59 t/CS1234";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_INVALID_EVENT = "Please make sure to define an end time "
            + "if the start time is already set. The end time "
            + "should also be after the current time and the start time.";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<String> priority, Optional<String> status, Optional<String> note,
            Optional<String> startTime, Optional<String> endTime, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                priority.isPresent() ? new Priority(priority.get()) : null,
                status.isPresent() ? new Status(status.get()) : new Status(),
                note.isPresent() ? new Note(note.get()) : null,
                startTime.isPresent() ? new DateTime(startTime.get()) : null,
                endTime.isPresent() ? new DateTime(endTime.get()) : null,
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            if (!Task.isValidEvent(toAdd)) {
                throw new CommandException(MESSAGE_INVALID_EVENT);
            }
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

}
