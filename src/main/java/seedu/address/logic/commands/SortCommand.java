package seedu.address.logic.commands;


/**
 * Lists all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all tasks according to deadline";


    @Override
    public CommandResult execute() {
        model.sortFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
