package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void isValidStatus() {
        // blank email
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // missing parts
        assertFalse(Status.isValidStatus("@webmail.com")); // missing local part
        assertFalse(Status.isValidStatus("peterjackwebmail.com")); // missing '@' symbol
        assertFalse(Status.isValidStatus("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Status.isValidStatus("-@webmail.com")); // invalid local part
        assertFalse(Status.isValidStatus("peterjack@-")); // invalid domain name
        assertFalse(Status.isValidStatus("peter jack@webmail.com")); // spaces in local part
        assertFalse(Status.isValidStatus("peterjack@web mail.com")); // spaces in domain name
        assertFalse(Status.isValidStatus("peterjack@@webmail.com")); // double '@' symbol
        assertFalse(Status.isValidStatus("peter@jack@webmail.com")); // '@' symbol in local part
        assertFalse(Status.isValidStatus("peterjack@webmail@com")); // '@' symbol in domain name

        // valid email
        assertTrue(Status.isValidStatus("PeterJack_1190@WEB.Mail.com"));
        assertTrue(Status.isValidStatus("a@b"));  // minimal
        assertTrue(Status.isValidStatus("test@localhost"));   // alphabets only
        assertTrue(Status.isValidStatus("123@145"));  // numeric local part and domain name
        assertTrue(Status.isValidStatus("a1@sg50.org"));  // mixture of alphanumeric and dot characters
        assertTrue(Status.isValidStatus("_user_@_do_main_.com_"));    // underscores
        assertTrue(Status.isValidStatus("peter_jack@a_very_long_domain_AVLD.com"));   // long domain name
        assertTrue(Status.isValidStatus("if.you.dream.it_you.can.do.it@youth_email.com"));    // long local part
    }
}
