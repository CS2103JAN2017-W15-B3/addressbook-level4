package seedu.address.commons.exceptions;

/**
 * Signals that the current undo/redo action is invalid
 *
 */
public class InvalidUndoException extends Exception {
    public InvalidUndoException(String message) {
        super(message);
    }
}
