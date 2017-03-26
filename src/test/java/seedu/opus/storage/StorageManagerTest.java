package seedu.opus.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.opus.commons.events.model.TaskManagerChangedEvent;
import seedu.opus.commons.events.storage.DataSavingExceptionEvent;
import seedu.opus.model.ReadOnlyTaskManager;
import seedu.opus.model.TaskManager;
import seedu.opus.model.UserPrefs;
import seedu.opus.testutil.EventsCollector;
import seedu.opus.testutil.TypicalTestTasks;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void taskManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        TaskManager original = new TypicalTestTasks().getTypicalTaskManager();
        storageManager.saveTaskManager(original);
        ReadOnlyTaskManager retrieved = storageManager.readTaskManager().get();
        assertEquals(original, new TaskManager(retrieved));
    }

    @Test
    public void getTaskManagerFilePath() {
        assertNotNull(storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleTaskManagerChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskManagerStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskManagerChangedEvent(new TaskManagerChangedEvent(new TaskManager()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskManagerStorageExceptionThrowingStub extends XmlTaskManagerStorage {

        public XmlTaskManagerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
