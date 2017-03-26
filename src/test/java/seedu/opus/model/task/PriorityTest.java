package seedu.opus.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.task.Priority.Level;

public class PriorityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidPriority() {
        // invalid priorities
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only

        // valid priorities
        assertTrue(Priority.isValidPriority("hi")); // high priority
        assertTrue(Priority.isValidPriority("mid")); // medium priority
        assertTrue(Priority.isValidPriority("low")); // low priority
    }

    @Test (expected = IllegalValueException.class)
    public void initialisePriorityWithInvalidArgs() throws IllegalValueException {
        @SuppressWarnings("unused")
        Priority invalidPriority = new Priority("HIGH");
    }

    @Test
    public void parseValidUserInputString() throws IllegalValueException {
        assertEquals(Priority.parseUserInputString("hi"), Priority.Level.HIGH);
        assertEquals(Priority.parseUserInputString("mid"), Priority.Level.MEDIUM);
        assertEquals(Priority.parseUserInputString("low"), Priority.Level.LOW);
    }

    @Test (expected = IllegalValueException.class)
    public void parseInvalidUserInputString() throws IllegalValueException {
        Priority.parseUserInputString("1a$");
    }

    @Test
    public void toUserInputString() {
        assertEquals(Level.HIGH.toString(), Priority.PRIORITY_HIGH);
        assertEquals(Level.MEDIUM.toString(), Priority.PRIORITY_MEDIUM);
        assertEquals(Level.LOW.toString(), Priority.PRIORITY_LOW);
    }

    @Test
    public void parseValidXmlString() throws IllegalValueException {
        assertEquals(Priority.valueOf("HIGH"), Priority.Level.HIGH);
        assertEquals(Priority.valueOf("MEDIUM"), Priority.Level.MEDIUM);
        assertEquals(Priority.valueOf("LOW"), Priority.Level.LOW);
    }

    @Test (expected = IllegalValueException.class)
    public void pareseInvalidXmlString() throws IllegalValueException {
        Priority.valueOf("NON");
    }
}
