package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.NoSuchElementException;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.AddCommand;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_NOTE,
                        PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY),
                    argsTokenizer.getValue(PREFIX_STATUS),
                    argsTokenizer.getValue(PREFIX_NOTE),
                    argsTokenizer.getValue(PREFIX_STARTTIME),
                    argsTokenizer.getValue(PREFIX_ENDTIME),
                    ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
