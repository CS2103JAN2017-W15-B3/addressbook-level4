package seedu.opus.logic.commands;

import seedu.opus.model.TaskManager;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
