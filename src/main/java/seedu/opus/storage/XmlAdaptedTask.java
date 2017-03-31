package seedu.opus.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.tag.Tag;
import seedu.opus.model.tag.UniqueTagList;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Name;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;
import seedu.opus.model.task.Status;
import seedu.opus.model.task.Task;

//@@author A0148087W
/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String id;
    @XmlElement(required = false)
    private String priority;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = false)
    private String note;
    @XmlElement(required = false)
    private String startTime;
    @XmlElement(required = false)
    private String endTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(ReadOnlyTask source) {
        name = source.getName().fullName;
        id = source.getId();
        priority = source.getPriority().isPresent() ? source.getPriority().get().getValue().name() : null;
        status = source.getStatus().toString();
        note = source.getNote().isPresent() ? source.getNote().get().toString() : null;
        startTime = source.getStartTime().isPresent() ? source.getStartTime().get().toString() : null;
        endTime = source.getEndTime().isPresent() ? source.getEndTime().get().toString() : null;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted task object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final String id = this.id;
        Priority priority = (this.priority != null) ? new Priority(Priority.valueOf(this.priority)) : null;
        final Status status = new Status(this.status);
        final Note note = (this.note != null) ? new Note(this.note) : null;
        final DateTime startTime = (this.startTime != null) ? new DateTime(this.startTime) : null;
        final DateTime endTime = (this.endTime != null) ? new DateTime(this.endTime) : null;
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, id, priority, status, note, startTime, endTime, tags);
    }
}
