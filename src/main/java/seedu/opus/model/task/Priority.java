package seedu.opus.model.task;

import seedu.opus.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the task manager. Guarantees: immutable; is
 * valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static enum Level {

        HIGH,
        MEDIUM,
        LOW;

        @Override
        public String toString() {
            switch(this) {
            case HIGH:
                return PRIORITY_HIGH;
            case MEDIUM:
                return PRIORITY_MEDIUM;
            case LOW:
                return PRIORITY_LOW;
            default:
                throw new AssertionError();
            }
        }

    }

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority can only take specific string "
            + "values hi, mid, low, none.";
    public static final String PRIORITY_VALIDATION_REGEX = "[^\\s].*";
    public static final String PRIORITY_HIGH = "hi";
    public static final String PRIORITY_MEDIUM = "mid";
    public static final String PRIORITY_LOW = "low";

    private final Level value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException
     *             if given priority is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        assert priority != null;
        String trimmedPriority = priority.trim();
        if (!isValidPriority(trimmedPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = parseUserInputString(trimmedPriority);
    }

    public Priority(Priority.Level priority) {
        assert priority != null;
        this.value = priority;
    }

    /**
     * Initialise a Priority with Type value of Type.NONE
     */
    public Priority() {
        this.value = null;
    }

    /**
     * parse a string priority into Priority.Level
     *
     * @param priority
     * @return Priority.Level value
     * @throws IllegalValueException
     */
    public static Priority.Level parseUserInputString(String priority) throws IllegalValueException {
        assert priority != null;
        switch (priority.toLowerCase()) {
        case PRIORITY_HIGH:
            return Level.HIGH;
        case PRIORITY_MEDIUM:
            return Level.MEDIUM;
        case PRIORITY_LOW:
            return Level.LOW;
        default:
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }

    public static Priority.Level parseXmlString(String priority) throws IllegalValueException {
        assert priority != null;
        try {
            return Level.valueOf(priority);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid input.
     */
    public static boolean isValidPriority(String test) {
        switch (test.toLowerCase()) {
        case PRIORITY_HIGH:
            return true;
        case PRIORITY_MEDIUM:
            return true;
        case PRIORITY_LOW:
            return true;
        default:
            return false;
        }
    }

    /**
     * @return the current value of the priority
     */
    public Level getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                        && this.value.equals(((Priority) other).value)); // state
                                                                         // check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
