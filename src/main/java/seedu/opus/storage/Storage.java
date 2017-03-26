package seedu.opus.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.events.storage.DataSavingExceptionEvent;
import seedu.opus.commons.exceptions.DataConversionException;
import seedu.opus.model.ReadOnlyTaskManager;
import seedu.opus.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Task Manager to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
}
