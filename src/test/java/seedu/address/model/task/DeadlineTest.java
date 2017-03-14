package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DeadlineTest {

    @Test
    public void isValidDeadline() {
        // invalid deadline
        assertFalse(Deadline.isValidDeadline("03/12/2016")); // inputs a date earlier than the current date

        // valid deadline
        assertTrue(Deadline.isValidDeadline("03/12/2217")); // inputs a date later than the current date
    }
}
