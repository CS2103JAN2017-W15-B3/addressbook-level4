package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's status in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status can take any values";
    public static final String STATUS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given status.
     *
     * @throws IllegalValueException
     *             if given status is invalid.
     */
    public Status(String status) throws IllegalValueException {
	assert status != null;
	String trimmedStatus = status.trim();
	if (!isValidStatus(trimmedStatus)) {
	    throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
	}
	this.value = trimmedStatus;
    }

    /**
     * Returns if a given string is a valid task status.
     */
    public static boolean isValidStatus(String test) {
	return test.matches(STATUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
	return value;
    }

    @Override
    public boolean equals(Object other) {
	return other == this // short circuit if same object
		|| (other instanceof Status // instanceof handles nulls
			&& this.value.equals(((Status) other).value)); // state
								       // check
    }

    @Override
    public int hashCode() {
	return value.hashCode();
    }

}
