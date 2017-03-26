package seedu.opus.logic.commands;

import seedu.opus.commons.exceptions.InvalidUndoException;
import seedu.opus.logic.commands.exceptions.CommandException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo to previous state!";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            assert model != null;
            model.resetToPrecedingState();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (InvalidUndoException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
