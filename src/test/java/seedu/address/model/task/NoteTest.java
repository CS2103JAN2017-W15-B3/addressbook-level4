package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteTest {

    @Test
    public void isValidNote() {
        // invalid note
        assertFalse(Note.isValidNote("")); // empty string
        assertFalse(Note.isValidNote(" ")); // spaces only

        // valid note
        assertTrue(Note.isValidNote("Chapter 2 to 4"));
        assertTrue(Note.isValidNote("-")); // one character
        assertTrue(Note.isValidNote("2 dozens of eggs, 1 packet of milk, 1 loaf of bread, cheese")); // long note
        assertTrue(Note.isValidNote("2 dozens of eggs, 1 packet of milk, 1 loaf of bread, cheese")); // long note
        assertTrue(Note.isValidNote("Individually, review the sample user guides (on IVLE) using the "
                                    + "User Guide checklist in CA 3: User Guide and Developer Guide. In "
        		                    + "your project team, discuss your findings with each other. Be ready "
        		                    + "to share your team�s discussion with the class.")); // very long note
    }
}
