package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteTest {

    @Test
    public void isValidNote() {
        // invalid notes
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid notes
        assertTrue(Note.isValidNote("Idle task"));
        assertTrue(Note.isValidNote("-")); // one character
        assertTrue(Note.isValidNote("This is just a test to see if note can handle a long note")); // long note
    }
}
