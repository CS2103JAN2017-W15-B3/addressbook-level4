package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;

import java.util.regex.Matcher;

import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.logic.commands.SortCommand;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        final String[] keywords = matcher.group("keywords").split("\\s+");
        final String keywordSet = keywords[0];
        return new SortCommand(keywordSet);
    }

}
