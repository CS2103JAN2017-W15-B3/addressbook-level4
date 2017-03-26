package seedu.opus.testutil;

import java.util.ArrayList;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import seedu.opus.commons.core.EventsCenter;
import seedu.opus.commons.events.BaseEvent;

/**
 * A class that collects events raised by other classes.
 */
public class EventsCollector {
    private List<BaseEvent> events = new ArrayList<BaseEvent>();

    public EventsCollector() {
        EventsCenter.getInstance().registerHandler(this);
    }

    /**
     * Collects any event raised by any class
     */
    @Subscribe
    public void collectEvent(BaseEvent event) {
        events.add(event);
    }

    /**
     * Removes collected events from the collected list
     */
    public void reset() {
        events.clear();
    }

    /**
     * Returns the event at the specified index
     */
    public BaseEvent get(int index) {
        return events.get(index);
    }
}
