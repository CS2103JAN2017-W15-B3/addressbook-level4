package seedu.opus.logic.parser;

import static seedu.opus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.opus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.opus.logic.commands.IncorrectCommand;
import seedu.opus.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STATUS, PREFIX_NOTE,
                        PREFIX_STARTTIME, PREFIX_ENDTIME, PREFIX_TAG);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = ParserUtil.splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(ParserUtil::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            editTaskDescriptor.setName(ParserUtil.parseName(preambleFields.get(1)));
            editTaskDescriptor.setPriority(ParserUtil.parsePriority(argsTokenizer.getValue(PREFIX_PRIORITY)));
            editTaskDescriptor.setStatus(ParserUtil.parseStatus(argsTokenizer.getValue(PREFIX_STATUS)));
            editTaskDescriptor.setNote(ParserUtil.parseNote(argsTokenizer.getValue(PREFIX_NOTE)));
            editTaskDescriptor.setStartTime(ParserUtil.parseDateTime(argsTokenizer.getValue(PREFIX_STARTTIME)));
            editTaskDescriptor.setEndTime(ParserUtil.parseDateTime(argsTokenizer.getValue(PREFIX_ENDTIME)));
            editTaskDescriptor.setTags(parseTagsForEdit(ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
