package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.opus.logic.commands.AddCommand;
import seedu.opus.logic.commands.ClearCommand;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.DeleteCommand;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.ExitCommand;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.HelpCommand;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.logic.commands.ListCommand;
import seedu.opus.logic.commands.MarkCommand;
import seedu.opus.logic.commands.RedoCommand;
import seedu.opus.logic.commands.SelectCommand;
import seedu.opus.logic.commands.SortCommand;
import seedu.opus.logic.commands.UndoCommand;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
