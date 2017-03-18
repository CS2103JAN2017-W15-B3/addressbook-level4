package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Note;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String priority;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String note;
    @XmlElement(required = true)
    private String dateTime;

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
        priority = source.getPriority()
                .map(Priority::getValue)
                .map(Priority.Type::name)
                .orElse(Priority.Type.NONE.name());
        status = source.getStatus().value;
        note = source.getNote().map(Note::toString).orElse("");
        dateTime = source.getDateTime().map(DateTime::toString).orElse("");
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
        final Priority priority = new Priority(Priority.parseXmlString(this.priority));
        final Status status = new Status(this.status);
        final Note note = new Note(this.note);
        final DateTime dateTime = new DateTime(this.dateTime);
        final UniqueTagList tags = new UniqueTagList(taskTags);
        return new Task(name, priority, status, note, dateTime, tags);
    }
}
