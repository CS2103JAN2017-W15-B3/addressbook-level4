package seedu.opus.model.task;

import java.util.Optional;

import seedu.opus.model.tag.UniqueTagList;

//@@author A0126345J
/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Optional<Priority> getPriority();
    Status getStatus();
    Optional<Note> getNote();
    Optional<DateTime> getStartTime();
    Optional<DateTime> getEndTime();
    String getId();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getStatus().equals(this.getStatus())
                && other.getNote().equals(this.getNote())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the task as text, showing all task's details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();

        builder.append(getName());

        if (getPriority().isPresent()) {
            builder.append(" Priority: ").append(getPriority().get().toString());
        }

        builder.append(" Status: ");
        builder.append(getStatus());

        if (getNote().isPresent()) {
            builder.append(" Note: ").append(getNote().get().toString());
        }

        if (getStartTime().isPresent()) {
            builder.append(" Start Time: ").append(getStartTime().get().toString());
        }

        if (getEndTime().isPresent()) {
            builder.append(" End Time: ").append(getEndTime().get().toString());
        }

        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
