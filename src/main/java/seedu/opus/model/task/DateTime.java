package seedu.opus.model.task;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.logic.parser.DateTimeParser;

public class DateTime {

    /*
     * Represents a Task's dateTime in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
     */

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Opus does not recognize the date time format. Please try again.";

    public final LocalDateTime dateTime;
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private Clock clock;

    /**
     * Validates given dateTime.
     *
     * @throws IllegalValueException if given dateTime is invalid.
     */
    public DateTime(LocalDateTime dateTime) throws IllegalValueException {
        assert dateTime != null;

        this.clock = Clock.systemDefaultZone();
        this.dateTime = dateTime;
    }

    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        this.clock = Clock.systemDefaultZone();
        this.dateTime =  DateTimeParser.parse(dateTime)
                .orElseThrow(() -> new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS));
    }

    /**
     * Checks if the DateTime is in the current week.
     *
     * @return true if the DateTime is in the current week
     */
    public boolean isCurrentWeek() {
        LocalDateTime now = LocalDateTime.now(clock);

        // 1 for Monday, 7 for Sunday
        int dayOfCurrentWeek = now.getDayOfWeek().getValue();

        // We take last Sunday and next Monday because isAfter and isBefore methods are not inclusive.
        LocalDate lastSundayDate = now.minusDays(dayOfCurrentWeek - 2).toLocalDate();
        LocalDate nextMondayDate = now.plusDays(8 - dayOfCurrentWeek).toLocalDate();

        LocalDateTime startOfWeek = LocalDateTime.of(lastSundayDate, LocalTime.MAX);
        LocalDateTime endOfWeek = LocalDateTime.of(nextMondayDate, LocalTime.MIDNIGHT);

        return dateTime.isAfter(startOfWeek) && dateTime.isBefore(endOfWeek);
    }

    /**
     * Returns true if a given date is a valid task dateTime.
     * @throws IllegalValueException
     */
    public static boolean isValidDateTime(String test) {
        return DateTimeParser.parse(test).isPresent();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public String toString() {
        return formatter.format(dateTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.dateTime.equals(((DateTime) other).dateTime)); // state check

    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
