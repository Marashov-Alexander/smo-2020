package alexander.marashov.smo.components.timeline;

import alexander.marashov.smo.components.generators.EventGenerator;
import alexander.marashov.smo.components.generators.Request;

import java.util.function.Supplier;

public final class Event implements Comparable<Event> {

    public enum EventType {
        CONSUMER_FREE_EVENT,
        SUPPLIER_EVENT,
    }

    public final EventType type;
    public final EventGenerator initiator;
    public final double timestamp;
    public final Supplier<Request> actions;

    public Event(
            final EventType type,
            final EventGenerator initiator,
            final double timestamp,
            final Supplier<Request> actions
    ) {
        this.type = type;
        this.initiator = initiator;
        this.timestamp = timestamp;
        this.actions = actions;
    }

    @Override
    public int compareTo(final Event event) {
        return Double.compare(this.timestamp, event.timestamp);
    }

    @Override
    public String toString() {
        return "Event(type = " + type + ", initiator = " + initiator + ", timestamp = " + timestamp + ")";
    }
}
