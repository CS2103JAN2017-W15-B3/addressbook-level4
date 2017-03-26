package seedu.opus.logic.commands;

import seedu.opus.commons.core.EventsCenter;
import seedu.opus.commons.core.Messages;
import seedu.opus.commons.core.UnmodifiableObservableList;
import seedu.opus.commons.events.ui.JumpToListRequestEvent;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from the task manager.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

}
