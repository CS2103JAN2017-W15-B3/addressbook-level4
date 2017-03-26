package seedu.opus.logic.commands;

import java.util.Optional;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.task.Status;

public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s as complete";

    private final int filteredTaskListIndex;

    public MarkCommand(int filteredTaskListIndex) {
        this.filteredTaskListIndex = filteredTaskListIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Status status;
        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            status = new Status("complete");
        } catch (IllegalValueException e) {
            throw new CommandException("Invalid status created.");
        }

        editTaskDescriptor.setStatus(Optional.of(status));

        EditCommand editCommand = new EditCommand(filteredTaskListIndex, editTaskDescriptor);

        editCommand.setData(model);
        return editCommand.execute();
    }

}
