package seedu.opus.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("buy milk*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("buy milk")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("buy 2 pens")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("Remember to buy birthday card and cake for Andy")); // long task names
        assertTrue(Name.isValidName("TERM TEST")); // all capital letters
    }
}
