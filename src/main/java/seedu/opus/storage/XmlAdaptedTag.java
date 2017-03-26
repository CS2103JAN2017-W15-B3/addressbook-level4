package seedu.opus.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.opus.commons.exceptions.IllegalValueException;
import seedu.opus.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    @XmlValue
    public String tagName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName);
    }

}
