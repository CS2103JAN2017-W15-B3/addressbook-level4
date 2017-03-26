package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.opus.commons.core.Messages;
import seedu.opus.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find asdf"); // no results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find milk", td.grocery);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findmilk");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);

        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
