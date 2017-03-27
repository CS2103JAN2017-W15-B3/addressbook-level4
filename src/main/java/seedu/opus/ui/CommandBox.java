package seedu.opus.ui;

import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.commons.core.Trie;
import seedu.opus.commons.events.ui.NewResultAvailableEvent;
import seedu.opus.commons.util.FxViewUtil;
import seedu.opus.logic.Logic;
import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String EMPTY_STRING = "";

    private final Logic logic;
    private final UserInputHistory history;
    private final AutocompleteTrie autocompleteTrie;
    private Iterator<String> suggestions;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        this.autocompleteTrie = new AutocompleteTrie();
        this.autocompleteTrie.init();
        this.suggestions = Collections.emptyIterator();
        addToPlaceholder(commandBoxPlaceholder);
        history = new UserInputHistory();
        registerCursorKeyEventFilter();
        listenForTab();
        focusCommandBox();
    }

    private void focusCommandBox() {
        commandTextField.requestFocus();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            history.saveUserInput(commandTextField.getText());
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText(EMPTY_STRING);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            logger.info("Invalid command: " + commandTextField.getText());
            commandTextField.setText(EMPTY_STRING);
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    private void listenForTab() {
        commandTextField.setOnKeyPressed(e -> {
            if (!e.getCode().equals(KeyCode.TAB)) {
                clearAutocompleteSuggestions();
                return;
            }

            // prevents the event from propagating up, resulting in shift out of focus
            e.consume();

            autocompleteUserInput();
        });
    }

    private void autocompleteUserInput() {
        String userInput = commandTextField.getText();
        if (userInput.isEmpty()) return;

        boolean hasMatch = autocompleteTrie.hasMatch(userInput);
        boolean outOfSuggestions = !suggestions.hasNext();

        if (hasMatch && outOfSuggestions) {
            suggestions = autocompleteTrie.autoComplete(userInput).iterator();
        }

        if (hasMatch) {
            setCommandLineInput(suggestions.next());
        } else {
            clearAutocompleteSuggestions();
        }
    }

    private void clearAutocompleteSuggestions() {
        suggestions = Collections.emptyIterator();
    }

    private void setCommandLineInput(String input) {
        commandTextField.setText(input);
        commandTextField.positionCaret(input.length());
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Catch cursor key inputs from user to browse previous user input history
     */
    private void registerCursorKeyEventFilter() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode key = event.getCode();
            if (!handleCursorKeyEvent(key)) {
                return;
            }
            event.consume();
        });
    }

    private boolean handleCursorKeyEvent(KeyCode key) {
        if (key.equals(KeyCode.UP)) {
            browseToPreviousCommand();
        } else if (key.equals(KeyCode.DOWN)) {
            browseToPrecedingCommand();
        } else {
            return false;
        }
        return true;
    }

    private void browseToPreviousCommand() {
        String input  = history.getPreviousUserInput().orElse(EMPTY_STRING);
        commandTextField.setText(input);
        commandTextField.end();
    }

    private void browseToPrecedingCommand() {
        String input  = history.getPrecedingUserInput().orElse(EMPTY_STRING);
        commandTextField.setText(input);
        commandTextField.end();
    }

     /** Custom Trie for autocomplete feature.
     *
     * @author xbili
     *
     */
    private static class AutocompleteTrie extends Trie {

        private static final String[] COMMANDS = { "add", "delete", "edit", "mark", "unmark", "schedule", "list",
            "help", "find", "undo", "redo", "clear" };

        private void init() {
            for (String command : COMMANDS) {
                this.insert(command);
            }
        }

        /**
         * @return true if prefix matches any commands.
         */
        private boolean hasMatch(String prefix) {
            return !autoComplete(prefix).isEmpty();
        }
    }
}
