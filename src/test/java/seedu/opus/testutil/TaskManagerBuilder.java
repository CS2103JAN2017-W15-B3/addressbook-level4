package seedu.opus.testutil;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.TaskManager;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.task.Task;
import seedu.opus.model.task.UniqueTaskList;

/**
 * A utility class to help with building Taskmanager objects.
 * Example usage: <br>
 *     {@code TaskManager tm = new TaskManagerBuilder().withTask("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManager;

    public TaskManagerBuilder(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManagerBuilder withTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        taskManager.addTask(task);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManager.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return taskManager;
    }
}
