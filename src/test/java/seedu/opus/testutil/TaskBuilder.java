package seedu.opus.testutil;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.Status;

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

    public TaskBuilder withId(String id) throws IllegalValueException {
        this.task.setId(id);
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

    public TaskBuilder withNullNote() {
        this.task.setNote(null);
        return this;
    }

    public TaskBuilder withNullStartTime() {
        this.task.setStartTime(null);
        return this;
    }

    public TaskBuilder withNullEndTime() {
        this.task.setEndTime(null);
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}
