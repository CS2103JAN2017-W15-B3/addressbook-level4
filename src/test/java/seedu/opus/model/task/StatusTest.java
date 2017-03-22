package seedu.opus.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.opus.model.task.Status;

public class StatusTest {

    @Test
    public void isValidStatus() {
        // valid status
        assertTrue(Status.isValidStatus("Incomplete"));
        assertTrue(Status.isValidStatus("done"));
        assertTrue(Status.isValidStatus("completed"));
        assertTrue(Status.isValidStatus("finished"));
        assertTrue(Status.isValidStatus("1/2 done")); // alphanumeric
        assertTrue(Status.isValidStatus("pending"));
    }
}
