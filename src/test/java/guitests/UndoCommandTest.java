package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.opus.logic.commands.UndoCommand;
import seedu.opus.testutil.TestTask;
import seedu.opus.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList = td.getTypicalTasks();
    private TestTask[] currentTasksList = td.getTypicalTasks();

    @Test
    public void undoPreviousAddCommandTestSuccess() {
        TestTask taskToAdd = td.submission;
        currentTasksList = TestUtil.addTasksToList(currentTasksList, taskToAdd);

        //adds a test using user command
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertTrue(taskListPanel.isListMatching(currentTasksList));
        assertUndoSuccess(expectedTasksList);

    }

    /**
     * Checks that the undo command properly restore to previous state
     * @param expectedTasksList
     */
    public void assertUndoSuccess(TestTask...expectedTasksList) {
        commandBox.runCommand(UndoCommand.COMMAND_WORD);

        //confirm that undo command restores TaskList to the previous state
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
    }
}
