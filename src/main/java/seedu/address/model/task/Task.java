package seedu.address.model.task;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Status status;
    private Note note;
    private Deadline deadline;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority priority, Status status, Note note, Deadline deadline, UniqueTagList tags) {
        // Name should never be null because it is required for each task.
        // Status should never be null because every created task should be marked as incomplete.
        // Tags should never be null because zero tags is represented as an empty list.
        assert !CollectionUtil.isAnyNull(name, status, tags);

        this.name = name;
        this.priority = priority;
        this.status = status;
        this.note = note;
        this.deadline = deadline;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority().orElse(null), source.getStatus(),
                source.getNote().orElse(null), source.getDeadline().orElse(null), source.getTags());
    }

    public void setName(Name name) {
        assert name != null;
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setPriority(Priority priority) {
        assert priority != null;
        this.priority = priority;
    }

    @Override
    public Optional<Priority> getPriority() {
        return Optional.of(priority);
    }

    public void setStatus(Status status) {
        assert status != null;
        this.status = status;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    public void setNote(Note note) {
        assert note != null;
        this.note = note;
    }

    @Override
    public Optional<Note> getNote() {
        return Optional.of(note);
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return Optional.of(deadline);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        assert replacement != null;

        this.setName(replacement.getName());
        this.setPriority(replacement.getPriority().orElse(null));
        this.setStatus(replacement.getStatus());
        this.setNote(replacement.getNote().orElse(null));
        this.setDeadline(replacement.getDeadline().orElse(null));
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, status, note, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
