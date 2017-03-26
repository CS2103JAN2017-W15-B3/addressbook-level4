package seedu.opus.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.opus.commons.core.ComponentManager;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.logic.commands.Command;
import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.exceptions.CommandException;
import seedu.opus.logic.parser.Parser;
import seedu.opus.model.Model;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
