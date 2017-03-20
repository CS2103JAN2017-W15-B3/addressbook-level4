package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, taskWithoutPriority;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Do laundry")
                    .withNote("Twice as many detergent this time").withStatus("incomplete")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withTags("chores").build();
            benson = new TaskBuilder().withName("Wash the dishes")
                    .withNote("They're in the sink").withStatus("incomplete")
                    .withPriority("mid").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withTags("chores").build();
            carl = new TaskBuilder().withName("Do CS2103T post lecture quiz")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("On IVLE").build();
            daniel = new TaskBuilder().withName("Buy milk")
                    .withPriority("low").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Low fat").build();
            elle = new TaskBuilder().withName("Write reflections for CS2101")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Include more reflection rather than description").build();
            fiona = new TaskBuilder().withName("Meet Prof Joe for consultation")
                    .withPriority("mid").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("COM02-01").build();
            george = new TaskBuilder().withName("Pay school fees")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Pay it on myISIS").build();

            // Manually added
            hoon = new TaskBuilder().withName("Submit research proposal")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("To Prof Obama's pigeonhole").build();
            ida = new TaskBuilder().withName("Apply for scholarship")
                    .withPriority("low").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Apply via iDA website").build();
            taskWithoutPriority = new TaskBuilder().withName("Submit research proposal")
                    .withNullPriority().withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("To Prof Obama's pigeonhole").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager taskManager = new TaskManager();
        loadTaskManagerWithSampleData(taskManager);
        return taskManager;
    }
}
