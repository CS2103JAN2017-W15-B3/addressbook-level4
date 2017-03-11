package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withNote("123, Jurong West Ave 6, #08-111").withStatus("alice@gmail.com")
                    .withPriority("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withNote("311, Clementi Ave 2, #02-25")
                    .withStatus("johnd@gmail.com").withPriority("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("95352563")
                    .withStatus("heinz@yahoo.com").withNote("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("87652533")
                    .withStatus("cornelia@google.com").withNote("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("9482224")
                    .withStatus("werner@gmail.com").withNote("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("9482427")
                    .withStatus("lydia@gmail.com").withNote("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("9482442")
                    .withStatus("anna@google.com").withNote("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("8482424")
                    .withStatus("stefan@mail.com").withNote("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("8482131")
                    .withStatus("hans@google.com").withNote("chicago ave").build();
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
