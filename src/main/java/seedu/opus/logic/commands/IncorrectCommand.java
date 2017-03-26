package seedu.opus.logic.commands;

import seedu.opus.logic.commands.exceptions.CommandException;

/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(feedbackToUser);
    }

}

