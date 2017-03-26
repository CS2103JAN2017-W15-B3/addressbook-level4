package seedu.opus.logic.parser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Parses user date input.
 */
public class DateTimeParser {

    /**
     * Parses user date input into LocalDateTime object.
     *
     * @param dateString user input date string
     * @return an Optional that is either empty or contains a parsed LocalDateTime object
     */
    public static Optional<LocalDateTime> parse(String dateString) {

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(dateString);
        Date date = null;

        for (DateGroup group:groups) {
            date = group.getDates().get(0);
        }

        if (date == null) {
            return Optional.empty();
        }

        LocalDateTime output = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(output);
    }
}
