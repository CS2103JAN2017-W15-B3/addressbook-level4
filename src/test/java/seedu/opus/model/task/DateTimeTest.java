package seedu.opus.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.Test;

import seedu.opus.commons.exceptions.IllegalValueException;

public class DateTimeTest {

    @Test
    public void isValidDateTime() {
        // invalid dateTime
        assertFalse(DateTime.isValidDateTime("fasdf")); // inputs an invalid date string

        // valid dateTime
        assertTrue(DateTime.isValidDateTime("03/12/2217 12:00")); // inputs a valid date string
    }

    @Test
    public void isInCurrentWeek() {
        // Stub the time now to be 10:30AM on 2017-03-22, Wednesday.
        LocalDateTime stubbedNow = LocalDateTime.of(LocalDate.of(2017, 3, 22), LocalTime.of(10, 30));
        Clock fixedClock = Clock.fixed(stubbedNow.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());

        try {
            // Today should be in current week
            DateTime today = new DateTime(stubbedNow);
            today.setClock(fixedClock);
            assertTrue(today.isCurrentWeek());

            // This Sunday should be in current week
            LocalDateTime stubbedSunday = LocalDateTime.of(LocalDate.of(2017, 3, 26), LocalTime.MAX);
            DateTime sunday = new DateTime(stubbedSunday);
            sunday.setClock(fixedClock);
            assertTrue(sunday.isCurrentWeek());

            // Next Monday should not be in current week
            LocalDateTime stubbedNextMonday = LocalDateTime.of(LocalDate.of(2017, 3, 27), LocalTime.MIDNIGHT);
            DateTime nextMonday = new DateTime(stubbedNextMonday);
            nextMonday.setClock(fixedClock);
            assertFalse(nextMonday.isCurrentWeek());

            // This Monday should be in current week
            LocalDateTime stubbedMonday = LocalDateTime.of(LocalDate.of(2017, 3, 20), LocalTime.MIDNIGHT);
            DateTime monday = new DateTime(stubbedMonday);
            monday.setClock(fixedClock);
            assertFalse(monday.isCurrentWeek());

            // Last Sunday should not be in current week
            LocalDateTime stubbedLastSunday = LocalDateTime.of(LocalDate.of(2017, 3, 19), LocalTime.MAX);
            DateTime lastSunday = new DateTime(stubbedLastSunday);
            lastSunday.setClock(fixedClock);
            assertFalse(lastSunday.isCurrentWeek());
        } catch (IllegalValueException e) {
            fail("Exception should not be thrown.");
        }
    }
}
