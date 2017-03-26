package seedu.opus.model;

import java.util.Stack;

import seedu.opus.commons.exceptions.InvalidUndoException;

public class History {
    private Stack<TaskManager> undoStack;
    private Stack<TaskManager> redoStack;

    public static final String MESSAGE_INVALID_REDO = "No available action to redo!";
    public static final String MESSAGE_INVALID_UNDO = "No available action to undo!";

    public History() {
        undoStack = new Stack<TaskManager>();
        redoStack = new Stack<TaskManager>();
    }

    /**
     * Backup source taskManager to Undo History and clear any previous undo data
     * @param source
     */
    public void backupCurrentState(TaskManager source) {
        assert source != null;
        TaskManager backup = new TaskManager(source);
        this.undoStack.push(backup);
        this.redoStack.clear();
    }

    /**
     * Retrieve previous TaskManager state and pushes a copy of current state into the Redo History
     * @param currentState
     * @return previous TaskManager state
     * @throws InvalidUndoException
     */
    public TaskManager getPreviousState(TaskManager currentState) throws InvalidUndoException {
        if (this.undoStack.isEmpty()) {
            throw new InvalidUndoException(MESSAGE_INVALID_UNDO);
        }
        TaskManager prevState = this.undoStack.pop();
        this.redoStack.push(new TaskManager(currentState));
        return prevState;
    }

    /**
     * Retrieve preceding TaskManager state and pushes a copy of current state into the Undo History
     * @param currentState
     * @return preceding TaskManager state
     * @throws InvalidUndoException
     */
    public TaskManager getPrecedingState(TaskManager currentState) throws InvalidUndoException {
        if (this.redoStack.isEmpty()) {
            throw new InvalidUndoException(MESSAGE_INVALID_REDO);
        }
        TaskManager precedingState = this.redoStack.pop();
        this.undoStack.push(new TaskManager(currentState));
        return precedingState;
    }
}
