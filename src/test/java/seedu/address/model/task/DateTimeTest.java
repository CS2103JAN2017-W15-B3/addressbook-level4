package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid dateTime
        assertFalse(DateTime.isValidDateTime("fasdf")); // inputs an invalid date string

        // valid dateTime
        assertTrue(DateTime.isValidDateTime("03/12/2217 12:00")); // inputs a valid date string
    }
}
