package seedu.opus.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.Note;
import seedu.opus.model.task.Priority;
import seedu.opus.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    private static final String STYLE_PRIORITY_HIGH = "priority-high";
    private static final String STYLE_PRIORITY_MID = "priority-mid";
    private static final String STYLE_PRIORITY_LOW = "priority-low";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label note;
    @FXML
    private Label status;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex, boolean showIndex) {
        super(FXML);

        if (showIndex) id.setText(displayedIndex + ". ");

        name.setText(task.getName().fullName);
        setPriorityView(task);
        note.setText(task.getNote().map(Note::toString).orElse(""));
        status.setText(task.getStatus().value);
        startTime.setText(task.getStartTime().map(DateTime::toString).map(s -> "Start: " + s).orElse(""));
        endTime.setText(task.getEndTime().map(DateTime::toString).map(s -> "End: " + s).orElse(""));
        initTags(task);
    }

    private void setPriorityView(ReadOnlyTask task) {
        String text = task.getPriority()
                .map(Priority::toString)
                .map(String::toUpperCase)
                .orElse("");

        Optional<String> styleClass = task.getPriority().flatMap(TaskCard::getPriorityStyleClass);
        if (styleClass.isPresent()) {
            priority.getStyleClass().clear();
            priority.getStyleClass().add(styleClass.get());
        }

        priority.setText(text);
    }

    private static Optional<String> getPriorityStyleClass(Priority priority) {
        String styleClass;

        switch(priority.getValue()) {
        case HIGH:
            styleClass = STYLE_PRIORITY_HIGH;
            break;
        case MEDIUM:
            styleClass = STYLE_PRIORITY_MID;
            break;
        case LOW:
            styleClass = STYLE_PRIORITY_LOW;
            break;
        default:
            return Optional.empty();
        }

        return Optional.ofNullable(styleClass);
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

}
