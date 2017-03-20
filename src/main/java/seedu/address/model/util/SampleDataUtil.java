package seedu.address.model.util;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        LocalDateTime currentDate = LocalDateTime.now();
        try {
            return new Task[] {
                new Task(new Name("Finalise CS2101 Report"), new Priority("hi"), new Status("incomplete"),
                    new Note("User Guide and Developer Guide"),
                    new DateTime(currentDate.minusDays(2)), new DateTime(currentDate.minusDays(1)),
                    new UniqueTagList("school")),
                new Task(new Name("Buy a new laptop"), new Priority("mid"), new Status("incomplete"),
                    new Note("Avoid HP. Because, reasons."),
                    new DateTime(currentDate.plusDays(2)), new DateTime(currentDate.plusDays(3)),
                    new UniqueTagList("personal")),
                new Task(new Name("Clean the toilet"), new Priority("low"), new Status("incomplete"),
                    new Note("Mom says she can smell it from a mile away."),
                    new DateTime(currentDate.plusDays(2)), new DateTime(currentDate.plusDays(3)),
                    new UniqueTagList("personal")),
                new Task(new Name("Prepare for job interviews"), new Priority("mid"), new Status("incomplete"),
                    new Note("Equip yourself to overcome the quarter-life crisis"),
                    new DateTime(currentDate.plusDays(4)), new DateTime(currentDate.plusDays(5)),
                    new UniqueTagList("work")),
                new Task(new Name("Do CS2103 Tutorial"), new Priority("low"), new Status("incomplete"),
                    new Note("You need it to pass the module, trust me."),
                    new DateTime(currentDate.plusDays(4)), new DateTime(currentDate.plusDays(5)),
                    new UniqueTagList("classmates")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleTaskManager = new TaskManager();
            for (Task sampleTask : getSampleTasks()) {
                sampleTaskManager.addTask(sampleTask);
            }
            return sampleTaskManager;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate lists", e);
        }
    }
}
