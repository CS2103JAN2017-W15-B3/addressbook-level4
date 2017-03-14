package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

public class Deadline {

    /*
     * Represents a Task's deadline in the task manager.
     * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
     */

    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Task deadline should be after the current time";

    public final Date deadline;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Validates given deadline.
     *
     * @throws IllegalValueException if given deadline is invalid.
     */
    public Deadline(Date deadline) throws IllegalValueException {
        assert deadline != null;
        if (!isValidDeadline(deadline)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.deadline = deadline;
    }

    public Deadline(String deadline) throws IllegalValueException {
        assert deadline != null;
        Date deadlineObj = null;
        try {
            deadlineObj = formatter.parse(deadline);
            if (!isValidDeadline(deadlineObj)) {
                throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        this.deadline = deadlineObj;
    }

    /**
     * Returns true if a given date is a valid task deadline.
     */
    public static boolean isValidDeadline(Date test) {
        return test.after(new Date());
    }

    public static boolean isValidDeadline(String test) {
        try {
            return formatter.parse(test).after(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return formatter.format(deadline);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.deadline.equals(((Deadline) other).deadline)); // state check

    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }
}
