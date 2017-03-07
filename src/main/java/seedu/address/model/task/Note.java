package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task note in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidNote(String)}
 */
public class Note {

    public static final String MESSAGE_NOTE_CONSTRAINTS =
            "Task note can take any values";

    /*
     * The first character of the note must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NOTE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Note(String note) throws IllegalValueException {
        assert note != null;
        if (!isValidNote(note)) {
            throw new IllegalValueException(MESSAGE_NOTE_CONSTRAINTS);
        }
        this.value = note;
    }

    /**
     * Returns true if a given string is a valid task note.
     */
    public static boolean isValidNote(String test) {
        return test.matches(NOTE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Note // instanceof handles nulls
                && this.value.equals(((Note) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
