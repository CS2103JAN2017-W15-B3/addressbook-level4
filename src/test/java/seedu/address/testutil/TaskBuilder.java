package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Status;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(TestTask taskToCopy) {
        this.task = new TestTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        task.setTags(new UniqueTagList());
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withNote(String note) throws IllegalValueException {
        this.task.setNote(new Note(note));
        return this;
    }

    public TaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }

    public TaskBuilder withStatus(String status) throws IllegalValueException {
        this.task.setStatus(new Status(status));
        return this;
    }

    public TaskBuilder withStartTime(String startTime) throws IllegalValueException {
        this.task.setStartTime(new DateTime(startTime));
        return this;
    }

    public TaskBuilder withEndTime(String endTime) throws IllegalValueException {
        this.task.setEndTime(new DateTime(endTime));
        return this;
    }

    public TaskBuilder withNullPriority() {
        this.task.setPriority(null);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
