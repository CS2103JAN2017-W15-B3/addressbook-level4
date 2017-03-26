package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.opus.logic.commands.RedoCommand;
import seedu.opus.logic.commands.UndoCommand;
import seedu.opus.testutil.TestTask;
import seedu.opus.testutil.TestUtil;

public class RedoCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList = td.getTypicalTasks();
    private TestTask[] originalTasksList = td.getTypicalTasks();

    @Test
    public void redoSuccess() {
        TestTask taskToAdd = td.submission;
        expectedTasksList = TestUtil.addTasksToList(expectedTasksList, taskToAdd);

        //adds a test using user command
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertTrue(taskListPanel.isListMatching(expectedTasksList));

        //Undo previous add command
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(taskListPanel.isListMatching(originalTasksList));

        assertRedoSuccess(expectedTasksList);
    }

    /**
     * Checks that the redo command properly rollback previous undo command
     * @param expectedTasksList
     */
    public void assertRedoSuccess(TestTask...expectedTasksList) {
        commandBox.runCommand(RedoCommand.COMMAND_WORD);

        //confirm that redo command restores changes made by previous undo command
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }
}
