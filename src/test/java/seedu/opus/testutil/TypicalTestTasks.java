package seedu.opus.testutil;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.TaskManager;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask laundry, quiz, payment, reflection, dishes, consultation,
                        grocery, submission, application, taskWithoutDeadline,
                        taskWithoutNote, taskWithoutPriority;

    public TypicalTestTasks() {
        try {
            laundry = new TaskBuilder().withName("Do laundry")
                    .withNote("Twice as many detergent this time").withStatus("incomplete")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withTags("chores").build();
            dishes = new TaskBuilder().withName("Wash the dishes")
                    .withNote("They're in the sink").withStatus("incomplete")
                    .withPriority("mid").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withTags("chores").build();
            quiz = new TaskBuilder().withName("Do CS2103T post lecture quiz")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("On IVLE").build();
            grocery = new TaskBuilder().withName("Buy milk")
                    .withPriority("low").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Low fat").build();
            reflection = new TaskBuilder().withName("Write reflections for CS2101")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Include more reflection rather than description").build();
            consultation = new TaskBuilder().withName("Meet Prof Joe for consultation")
                    .withPriority("mid").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("COM02-01").build();
            payment = new TaskBuilder().withName("Pay school fees")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Pay it on myISIS").build();

            // Manually added
            submission = new TaskBuilder().withName("Submit research proposal")
                    .withPriority("hi").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("To Prof Obama's pigeonhole").build();
            application = new TaskBuilder().withName("Apply for scholarship")
                    .withPriority("low").withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("Apply via iDA website").build();
            taskWithoutPriority = new TaskBuilder().withName("Submit research proposal")
                    .withNullPriority().withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00")
                    .withStatus("incomplete").withNote("To Prof Obama's pigeonhole").build();
            taskWithoutNote = new TaskBuilder().withName("No note").withPriority("low").withStatus("incomplete")
                    .withStartTime("12/12/2017 12:00").withEndTime("12/12/2017 13:00").withNullNote().build();
            taskWithoutDeadline = new TaskBuilder().withName("No deadlines").withPriority("hi")
                    .withNullStartTime().withNullEndTime().withStatus("incomplete").withNote("no deadlines").build();
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
        return new TestTask[]{laundry, quiz, payment, reflection, dishes, consultation, grocery};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager taskManager = new TaskManager();
        loadTaskManagerWithSampleData(taskManager);
        return taskManager;
    }
}
