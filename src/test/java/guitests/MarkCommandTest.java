package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.testutil.TaskBuilder;
import seedu.opus.testutil.TestTask;

public class MarkCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void markTestSuccess() throws Exception {
        int taskManagerIndex = 1;

        TestTask taskToMark = expectedTasksList[taskManagerIndex - 1];
        TestTask markedTask = new TaskBuilder(taskToMark).withStatus("complete")
                .withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00").build();

        assertMarkSuccess(taskManagerIndex, taskManagerIndex, markedTask);
    }

    /**
     * Checks whether the marked task has the correct updated details.
     *
     * @param filteredTaskListIndex index of task to edit in filtered list.
     * @param taskManagerIndex index of task to edit in the task manager.
     *      Must refer to the same task as {@code filteredTaskListIndex}
     * @param editedTask the expected task after editing the task's details.
     */
    private void assertMarkSuccess(int filteredTaskListIndex, int taskManagerIndex,
            TestTask markedTask) {
        commandBox.runCommand("mark " + filteredTaskListIndex);

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(markedTask.getName().fullName);
        assertMatching(markedTask, editedCard);

        // confirm the list now contains all previous tasks plus the task with updated details
        expectedTasksList[taskManagerIndex - 1] = markedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, markedTask));
    }

}
